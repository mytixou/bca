import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldeApa, SoldeApa } from '../solde-apa.model';
import { SoldeApaService } from '../service/solde-apa.service';
import { IDroitsStrategieApa } from 'app/entities/droits-strategie-apa/droits-strategie-apa.model';
import { DroitsStrategieApaService } from 'app/entities/droits-strategie-apa/service/droits-strategie-apa.service';

@Component({
  selector: 'jhi-solde-apa-update',
  templateUrl: './solde-apa-update.component.html',
})
export class SoldeApaUpdateComponent implements OnInit {
  isSaving = false;

  droitsStrategieApasSharedCollection: IDroitsStrategieApa[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    isDernier: [null, [Validators.required]],
    annee: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    consoMontantApaCotisations: [null, [Validators.required]],
    consoMontantApaSalaire: [null, [Validators.required]],
    soldeMontantApa: [null, [Validators.required]],
    consoHeureApa: [null, [Validators.required]],
    soldeHeureApa: [null, [Validators.required]],
    droitsStrategieApa: [],
  });

  constructor(
    protected soldeApaService: SoldeApaService,
    protected droitsStrategieApaService: DroitsStrategieApaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeApa }) => {
      if (soldeApa.id === undefined) {
        const today = dayjs().startOf('day');
        soldeApa.date = today;
      }

      this.updateForm(soldeApa);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldeApa = this.createFromForm();
    if (soldeApa.id !== undefined) {
      this.subscribeToSaveResponse(this.soldeApaService.update(soldeApa));
    } else {
      this.subscribeToSaveResponse(this.soldeApaService.create(soldeApa));
    }
  }

  trackDroitsStrategieApaById(index: number, item: IDroitsStrategieApa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldeApa>>): void {
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

  protected updateForm(soldeApa: ISoldeApa): void {
    this.editForm.patchValue({
      id: soldeApa.id,
      date: soldeApa.date ? soldeApa.date.format(DATE_TIME_FORMAT) : null,
      isActif: soldeApa.isActif,
      isDernier: soldeApa.isDernier,
      annee: soldeApa.annee,
      mois: soldeApa.mois,
      consoMontantApaCotisations: soldeApa.consoMontantApaCotisations,
      consoMontantApaSalaire: soldeApa.consoMontantApaSalaire,
      soldeMontantApa: soldeApa.soldeMontantApa,
      consoHeureApa: soldeApa.consoHeureApa,
      soldeHeureApa: soldeApa.soldeHeureApa,
      droitsStrategieApa: soldeApa.droitsStrategieApa,
    });

    this.droitsStrategieApasSharedCollection = this.droitsStrategieApaService.addDroitsStrategieApaToCollectionIfMissing(
      this.droitsStrategieApasSharedCollection,
      soldeApa.droitsStrategieApa
    );
  }

  protected loadRelationshipsOptions(): void {
    this.droitsStrategieApaService
      .query()
      .pipe(map((res: HttpResponse<IDroitsStrategieApa[]>) => res.body ?? []))
      .pipe(
        map((droitsStrategieApas: IDroitsStrategieApa[]) =>
          this.droitsStrategieApaService.addDroitsStrategieApaToCollectionIfMissing(
            droitsStrategieApas,
            this.editForm.get('droitsStrategieApa')!.value
          )
        )
      )
      .subscribe((droitsStrategieApas: IDroitsStrategieApa[]) => (this.droitsStrategieApasSharedCollection = droitsStrategieApas));
  }

  protected createFromForm(): ISoldeApa {
    return {
      ...new SoldeApa(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      isActif: this.editForm.get(['isActif'])!.value,
      isDernier: this.editForm.get(['isDernier'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      consoMontantApaCotisations: this.editForm.get(['consoMontantApaCotisations'])!.value,
      consoMontantApaSalaire: this.editForm.get(['consoMontantApaSalaire'])!.value,
      soldeMontantApa: this.editForm.get(['soldeMontantApa'])!.value,
      consoHeureApa: this.editForm.get(['consoHeureApa'])!.value,
      soldeHeureApa: this.editForm.get(['soldeHeureApa'])!.value,
      droitsStrategieApa: this.editForm.get(['droitsStrategieApa'])!.value,
    };
  }
}
