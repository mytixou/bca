import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IStrategieCmgAssmat, StrategieCmgAssmat } from '../strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';

@Component({
  selector: 'jhi-strategie-cmg-assmat-update',
  templateUrl: './strategie-cmg-assmat-update.component.html',
})
export class StrategieCmgAssmatUpdateComponent implements OnInit {
  isSaving = false;

  aidesSharedCollection: IAide[] = [];
  tiersFinanceursSharedCollection: ITiersFinanceur[] = [];

  editForm = this.fb.group({
    id: [],
    anne: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    nbHeureSeuilPlafond: [null, [Validators.required]],
    tauxSalaire: [null, [Validators.required]],
    tauxCotisations: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    dateCreated: [null, [Validators.required]],
    isUpdated: [null, [Validators.required]],
    dateModified: [],
    aide: [],
    tiersFinanceur: [],
  });

  constructor(
    protected strategieCmgAssmatService: StrategieCmgAssmatService,
    protected aideService: AideService,
    protected tiersFinanceurService: TiersFinanceurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strategieCmgAssmat }) => {
      if (strategieCmgAssmat.id === undefined) {
        const today = dayjs().startOf('day');
        strategieCmgAssmat.dateCreated = today;
        strategieCmgAssmat.dateModified = today;
      }

      this.updateForm(strategieCmgAssmat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const strategieCmgAssmat = this.createFromForm();
    if (strategieCmgAssmat.id !== undefined) {
      this.subscribeToSaveResponse(this.strategieCmgAssmatService.update(strategieCmgAssmat));
    } else {
      this.subscribeToSaveResponse(this.strategieCmgAssmatService.create(strategieCmgAssmat));
    }
  }

  trackAideById(index: number, item: IAide): number {
    return item.id!;
  }

  trackTiersFinanceurById(index: number, item: ITiersFinanceur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStrategieCmgAssmat>>): void {
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

  protected updateForm(strategieCmgAssmat: IStrategieCmgAssmat): void {
    this.editForm.patchValue({
      id: strategieCmgAssmat.id,
      anne: strategieCmgAssmat.anne,
      mois: strategieCmgAssmat.mois,
      nbHeureSeuilPlafond: strategieCmgAssmat.nbHeureSeuilPlafond,
      tauxSalaire: strategieCmgAssmat.tauxSalaire,
      tauxCotisations: strategieCmgAssmat.tauxCotisations,
      isActif: strategieCmgAssmat.isActif,
      dateCreated: strategieCmgAssmat.dateCreated ? strategieCmgAssmat.dateCreated.format(DATE_TIME_FORMAT) : null,
      isUpdated: strategieCmgAssmat.isUpdated,
      dateModified: strategieCmgAssmat.dateModified ? strategieCmgAssmat.dateModified.format(DATE_TIME_FORMAT) : null,
      aide: strategieCmgAssmat.aide,
      tiersFinanceur: strategieCmgAssmat.tiersFinanceur,
    });

    this.aidesSharedCollection = this.aideService.addAideToCollectionIfMissing(this.aidesSharedCollection, strategieCmgAssmat.aide);
    this.tiersFinanceursSharedCollection = this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(
      this.tiersFinanceursSharedCollection,
      strategieCmgAssmat.tiersFinanceur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.aideService
      .query()
      .pipe(map((res: HttpResponse<IAide[]>) => res.body ?? []))
      .pipe(map((aides: IAide[]) => this.aideService.addAideToCollectionIfMissing(aides, this.editForm.get('aide')!.value)))
      .subscribe((aides: IAide[]) => (this.aidesSharedCollection = aides));

    this.tiersFinanceurService
      .query()
      .pipe(map((res: HttpResponse<ITiersFinanceur[]>) => res.body ?? []))
      .pipe(
        map((tiersFinanceurs: ITiersFinanceur[]) =>
          this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(tiersFinanceurs, this.editForm.get('tiersFinanceur')!.value)
        )
      )
      .subscribe((tiersFinanceurs: ITiersFinanceur[]) => (this.tiersFinanceursSharedCollection = tiersFinanceurs));
  }

  protected createFromForm(): IStrategieCmgAssmat {
    return {
      ...new StrategieCmgAssmat(),
      id: this.editForm.get(['id'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      nbHeureSeuilPlafond: this.editForm.get(['nbHeureSeuilPlafond'])!.value,
      tauxSalaire: this.editForm.get(['tauxSalaire'])!.value,
      tauxCotisations: this.editForm.get(['tauxCotisations'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? dayjs(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isUpdated: this.editForm.get(['isUpdated'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value
        ? dayjs(this.editForm.get(['dateModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      aide: this.editForm.get(['aide'])!.value,
      tiersFinanceur: this.editForm.get(['tiersFinanceur'])!.value,
    };
  }
}
