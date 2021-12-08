import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPec, Pec } from '../pec.model';
import { PecService } from '../service/pec.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISoldeCi } from 'app/entities/solde-ci/solde-ci.model';
import { SoldeCiService } from 'app/entities/solde-ci/service/solde-ci.service';
import { ISoldeApa } from 'app/entities/solde-apa/solde-apa.model';
import { SoldeApaService } from 'app/entities/solde-apa/service/solde-apa.service';
import { ISoldePch } from 'app/entities/solde-pch/solde-pch.model';
import { SoldePchService } from 'app/entities/solde-pch/service/solde-pch.service';
import { ISoldePchE } from 'app/entities/solde-pch-e/solde-pch-e.model';
import { SoldePchEService } from 'app/entities/solde-pch-e/service/solde-pch-e.service';
import { TypeProduit } from 'app/entities/enumerations/type-produit.model';

@Component({
  selector: 'jhi-pec-update',
  templateUrl: './pec-update.component.html',
})
export class PecUpdateComponent implements OnInit {
  isSaving = false;
  typeProduitValues = Object.keys(TypeProduit);

  soldeCisCollection: ISoldeCi[] = [];
  soldeApasCollection: ISoldeApa[] = [];
  soldePchesCollection: ISoldePch[] = [];
  soldePchESCollection: ISoldePchE[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    idProduit: [null, [Validators.required]],
    produit: [null, [Validators.required]],
    isPlus: [null, [Validators.required]],
    dateCreated: [null, [Validators.required]],
    isUpdated: [null, [Validators.required]],
    dateModified: [],
    isActif: [null, [Validators.required]],
    pecDetails: [null, [Validators.required]],
    soldeCi: [],
    soldeApa: [],
    soldePch: [],
    soldePchE: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pecService: PecService,
    protected soldeCiService: SoldeCiService,
    protected soldeApaService: SoldeApaService,
    protected soldePchService: SoldePchService,
    protected soldePchEService: SoldePchEService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pec }) => {
      if (pec.id === undefined) {
        const today = dayjs().startOf('day');
        pec.dateCreated = today;
        pec.dateModified = today;
      }

      this.updateForm(pec);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('bcaApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pec = this.createFromForm();
    if (pec.id !== undefined) {
      this.subscribeToSaveResponse(this.pecService.update(pec));
    } else {
      this.subscribeToSaveResponse(this.pecService.create(pec));
    }
  }

  trackSoldeCiById(index: number, item: ISoldeCi): number {
    return item.id!;
  }

  trackSoldeApaById(index: number, item: ISoldeApa): number {
    return item.id!;
  }

  trackSoldePchById(index: number, item: ISoldePch): number {
    return item.id!;
  }

  trackSoldePchEById(index: number, item: ISoldePchE): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPec>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pec: IPec): void {
    this.editForm.patchValue({
      id: pec.id,
      idProduit: pec.idProduit,
      produit: pec.produit,
      isPlus: pec.isPlus,
      dateCreated: pec.dateCreated ? pec.dateCreated.format(DATE_TIME_FORMAT) : null,
      isUpdated: pec.isUpdated,
      dateModified: pec.dateModified ? pec.dateModified.format(DATE_TIME_FORMAT) : null,
      isActif: pec.isActif,
      pecDetails: pec.pecDetails,
      soldeCi: pec.soldeCi,
      soldeApa: pec.soldeApa,
      soldePch: pec.soldePch,
      soldePchE: pec.soldePchE,
    });

    this.soldeCisCollection = this.soldeCiService.addSoldeCiToCollectionIfMissing(this.soldeCisCollection, pec.soldeCi);
    this.soldeApasCollection = this.soldeApaService.addSoldeApaToCollectionIfMissing(this.soldeApasCollection, pec.soldeApa);
    this.soldePchesCollection = this.soldePchService.addSoldePchToCollectionIfMissing(this.soldePchesCollection, pec.soldePch);
    this.soldePchESCollection = this.soldePchEService.addSoldePchEToCollectionIfMissing(this.soldePchESCollection, pec.soldePchE);
  }

  protected loadRelationshipsOptions(): void {
    this.soldeCiService
      .query({ filter: 'pec-is-null' })
      .pipe(map((res: HttpResponse<ISoldeCi[]>) => res.body ?? []))
      .pipe(
        map((soldeCis: ISoldeCi[]) => this.soldeCiService.addSoldeCiToCollectionIfMissing(soldeCis, this.editForm.get('soldeCi')!.value))
      )
      .subscribe((soldeCis: ISoldeCi[]) => (this.soldeCisCollection = soldeCis));

    this.soldeApaService
      .query({ filter: 'pec-is-null' })
      .pipe(map((res: HttpResponse<ISoldeApa[]>) => res.body ?? []))
      .pipe(
        map((soldeApas: ISoldeApa[]) =>
          this.soldeApaService.addSoldeApaToCollectionIfMissing(soldeApas, this.editForm.get('soldeApa')!.value)
        )
      )
      .subscribe((soldeApas: ISoldeApa[]) => (this.soldeApasCollection = soldeApas));

    this.soldePchService
      .query({ filter: 'pec-is-null' })
      .pipe(map((res: HttpResponse<ISoldePch[]>) => res.body ?? []))
      .pipe(
        map((soldePches: ISoldePch[]) =>
          this.soldePchService.addSoldePchToCollectionIfMissing(soldePches, this.editForm.get('soldePch')!.value)
        )
      )
      .subscribe((soldePches: ISoldePch[]) => (this.soldePchesCollection = soldePches));

    this.soldePchEService
      .query({ filter: 'pec-is-null' })
      .pipe(map((res: HttpResponse<ISoldePchE[]>) => res.body ?? []))
      .pipe(
        map((soldePchES: ISoldePchE[]) =>
          this.soldePchEService.addSoldePchEToCollectionIfMissing(soldePchES, this.editForm.get('soldePchE')!.value)
        )
      )
      .subscribe((soldePchES: ISoldePchE[]) => (this.soldePchESCollection = soldePchES));
  }

  protected createFromForm(): IPec {
    return {
      ...new Pec(),
      id: this.editForm.get(['id'])!.value,
      idProduit: this.editForm.get(['idProduit'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      isPlus: this.editForm.get(['isPlus'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? dayjs(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isUpdated: this.editForm.get(['isUpdated'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value
        ? dayjs(this.editForm.get(['dateModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isActif: this.editForm.get(['isActif'])!.value,
      pecDetails: this.editForm.get(['pecDetails'])!.value,
      soldeCi: this.editForm.get(['soldeCi'])!.value,
      soldeApa: this.editForm.get(['soldeApa'])!.value,
      soldePch: this.editForm.get(['soldePch'])!.value,
      soldePchE: this.editForm.get(['soldePchE'])!.value,
    };
  }
}
