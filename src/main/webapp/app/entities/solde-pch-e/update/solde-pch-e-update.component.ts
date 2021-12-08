import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldePchE, SoldePchE } from '../solde-pch-e.model';
import { SoldePchEService } from '../service/solde-pch-e.service';
import { IDroitsStrategiePchE } from 'app/entities/droits-strategie-pch-e/droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from 'app/entities/droits-strategie-pch-e/service/droits-strategie-pch-e.service';

@Component({
  selector: 'jhi-solde-pch-e-update',
  templateUrl: './solde-pch-e-update.component.html',
})
export class SoldePchEUpdateComponent implements OnInit {
  isSaving = false;

  droitsStrategiePchESSharedCollection: IDroitsStrategiePchE[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    isDernier: [null, [Validators.required]],
    annee: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    consoMontantPchECotisations: [null, [Validators.required]],
    consoMontantPchESalaire: [null, [Validators.required]],
    soldeMontantPchE: [null, [Validators.required]],
    consoHeurePchE: [null, [Validators.required]],
    soldeHeurePchE: [null, [Validators.required]],
    droitsStrategiePchE: [],
  });

  constructor(
    protected soldePchEService: SoldePchEService,
    protected droitsStrategiePchEService: DroitsStrategiePchEService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldePchE }) => {
      if (soldePchE.id === undefined) {
        const today = dayjs().startOf('day');
        soldePchE.date = today;
      }

      this.updateForm(soldePchE);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldePchE = this.createFromForm();
    if (soldePchE.id !== undefined) {
      this.subscribeToSaveResponse(this.soldePchEService.update(soldePchE));
    } else {
      this.subscribeToSaveResponse(this.soldePchEService.create(soldePchE));
    }
  }

  trackDroitsStrategiePchEById(index: number, item: IDroitsStrategiePchE): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldePchE>>): void {
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

  protected updateForm(soldePchE: ISoldePchE): void {
    this.editForm.patchValue({
      id: soldePchE.id,
      date: soldePchE.date ? soldePchE.date.format(DATE_TIME_FORMAT) : null,
      isActif: soldePchE.isActif,
      isDernier: soldePchE.isDernier,
      annee: soldePchE.annee,
      mois: soldePchE.mois,
      consoMontantPchECotisations: soldePchE.consoMontantPchECotisations,
      consoMontantPchESalaire: soldePchE.consoMontantPchESalaire,
      soldeMontantPchE: soldePchE.soldeMontantPchE,
      consoHeurePchE: soldePchE.consoHeurePchE,
      soldeHeurePchE: soldePchE.soldeHeurePchE,
      droitsStrategiePchE: soldePchE.droitsStrategiePchE,
    });

    this.droitsStrategiePchESSharedCollection = this.droitsStrategiePchEService.addDroitsStrategiePchEToCollectionIfMissing(
      this.droitsStrategiePchESSharedCollection,
      soldePchE.droitsStrategiePchE
    );
  }

  protected loadRelationshipsOptions(): void {
    this.droitsStrategiePchEService
      .query()
      .pipe(map((res: HttpResponse<IDroitsStrategiePchE[]>) => res.body ?? []))
      .pipe(
        map((droitsStrategiePchES: IDroitsStrategiePchE[]) =>
          this.droitsStrategiePchEService.addDroitsStrategiePchEToCollectionIfMissing(
            droitsStrategiePchES,
            this.editForm.get('droitsStrategiePchE')!.value
          )
        )
      )
      .subscribe((droitsStrategiePchES: IDroitsStrategiePchE[]) => (this.droitsStrategiePchESSharedCollection = droitsStrategiePchES));
  }

  protected createFromForm(): ISoldePchE {
    return {
      ...new SoldePchE(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      isActif: this.editForm.get(['isActif'])!.value,
      isDernier: this.editForm.get(['isDernier'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      consoMontantPchECotisations: this.editForm.get(['consoMontantPchECotisations'])!.value,
      consoMontantPchESalaire: this.editForm.get(['consoMontantPchESalaire'])!.value,
      soldeMontantPchE: this.editForm.get(['soldeMontantPchE'])!.value,
      consoHeurePchE: this.editForm.get(['consoHeurePchE'])!.value,
      soldeHeurePchE: this.editForm.get(['soldeHeurePchE'])!.value,
      droitsStrategiePchE: this.editForm.get(['droitsStrategiePchE'])!.value,
    };
  }
}
