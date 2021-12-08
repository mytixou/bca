import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldePch, SoldePch } from '../solde-pch.model';
import { SoldePchService } from '../service/solde-pch.service';
import { IDroitsStrategiePch } from 'app/entities/droits-strategie-pch/droits-strategie-pch.model';
import { DroitsStrategiePchService } from 'app/entities/droits-strategie-pch/service/droits-strategie-pch.service';

@Component({
  selector: 'jhi-solde-pch-update',
  templateUrl: './solde-pch-update.component.html',
})
export class SoldePchUpdateComponent implements OnInit {
  isSaving = false;

  droitsStrategiePchesSharedCollection: IDroitsStrategiePch[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    isDernier: [null, [Validators.required]],
    annee: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    consoMontantPchCotisations: [null, [Validators.required]],
    consoMontantPchSalaire: [null, [Validators.required]],
    soldeMontantPch: [null, [Validators.required]],
    consoHeurePch: [null, [Validators.required]],
    soldeHeurePch: [null, [Validators.required]],
    droitsStrategiePch: [],
  });

  constructor(
    protected soldePchService: SoldePchService,
    protected droitsStrategiePchService: DroitsStrategiePchService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldePch }) => {
      if (soldePch.id === undefined) {
        const today = dayjs().startOf('day');
        soldePch.date = today;
      }

      this.updateForm(soldePch);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldePch = this.createFromForm();
    if (soldePch.id !== undefined) {
      this.subscribeToSaveResponse(this.soldePchService.update(soldePch));
    } else {
      this.subscribeToSaveResponse(this.soldePchService.create(soldePch));
    }
  }

  trackDroitsStrategiePchById(index: number, item: IDroitsStrategiePch): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldePch>>): void {
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

  protected updateForm(soldePch: ISoldePch): void {
    this.editForm.patchValue({
      id: soldePch.id,
      date: soldePch.date ? soldePch.date.format(DATE_TIME_FORMAT) : null,
      isActif: soldePch.isActif,
      isDernier: soldePch.isDernier,
      annee: soldePch.annee,
      mois: soldePch.mois,
      consoMontantPchCotisations: soldePch.consoMontantPchCotisations,
      consoMontantPchSalaire: soldePch.consoMontantPchSalaire,
      soldeMontantPch: soldePch.soldeMontantPch,
      consoHeurePch: soldePch.consoHeurePch,
      soldeHeurePch: soldePch.soldeHeurePch,
      droitsStrategiePch: soldePch.droitsStrategiePch,
    });

    this.droitsStrategiePchesSharedCollection = this.droitsStrategiePchService.addDroitsStrategiePchToCollectionIfMissing(
      this.droitsStrategiePchesSharedCollection,
      soldePch.droitsStrategiePch
    );
  }

  protected loadRelationshipsOptions(): void {
    this.droitsStrategiePchService
      .query()
      .pipe(map((res: HttpResponse<IDroitsStrategiePch[]>) => res.body ?? []))
      .pipe(
        map((droitsStrategiePches: IDroitsStrategiePch[]) =>
          this.droitsStrategiePchService.addDroitsStrategiePchToCollectionIfMissing(
            droitsStrategiePches,
            this.editForm.get('droitsStrategiePch')!.value
          )
        )
      )
      .subscribe((droitsStrategiePches: IDroitsStrategiePch[]) => (this.droitsStrategiePchesSharedCollection = droitsStrategiePches));
  }

  protected createFromForm(): ISoldePch {
    return {
      ...new SoldePch(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      isActif: this.editForm.get(['isActif'])!.value,
      isDernier: this.editForm.get(['isDernier'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      consoMontantPchCotisations: this.editForm.get(['consoMontantPchCotisations'])!.value,
      consoMontantPchSalaire: this.editForm.get(['consoMontantPchSalaire'])!.value,
      soldeMontantPch: this.editForm.get(['soldeMontantPch'])!.value,
      consoHeurePch: this.editForm.get(['consoHeurePch'])!.value,
      soldeHeurePch: this.editForm.get(['soldeHeurePch'])!.value,
      droitsStrategiePch: this.editForm.get(['droitsStrategiePch'])!.value,
    };
  }
}
