import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IStrategieCmgGed, StrategieCmgGed } from '../strategie-cmg-ged.model';
import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';

@Component({
  selector: 'jhi-strategie-cmg-ged-update',
  templateUrl: './strategie-cmg-ged-update.component.html',
})
export class StrategieCmgGedUpdateComponent implements OnInit {
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
    protected strategieCmgGedService: StrategieCmgGedService,
    protected aideService: AideService,
    protected tiersFinanceurService: TiersFinanceurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strategieCmgGed }) => {
      if (strategieCmgGed.id === undefined) {
        const today = dayjs().startOf('day');
        strategieCmgGed.dateCreated = today;
        strategieCmgGed.dateModified = today;
      }

      this.updateForm(strategieCmgGed);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const strategieCmgGed = this.createFromForm();
    if (strategieCmgGed.id !== undefined) {
      this.subscribeToSaveResponse(this.strategieCmgGedService.update(strategieCmgGed));
    } else {
      this.subscribeToSaveResponse(this.strategieCmgGedService.create(strategieCmgGed));
    }
  }

  trackAideById(index: number, item: IAide): number {
    return item.id!;
  }

  trackTiersFinanceurById(index: number, item: ITiersFinanceur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStrategieCmgGed>>): void {
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

  protected updateForm(strategieCmgGed: IStrategieCmgGed): void {
    this.editForm.patchValue({
      id: strategieCmgGed.id,
      anne: strategieCmgGed.anne,
      mois: strategieCmgGed.mois,
      nbHeureSeuilPlafond: strategieCmgGed.nbHeureSeuilPlafond,
      tauxSalaire: strategieCmgGed.tauxSalaire,
      tauxCotisations: strategieCmgGed.tauxCotisations,
      isActif: strategieCmgGed.isActif,
      dateCreated: strategieCmgGed.dateCreated ? strategieCmgGed.dateCreated.format(DATE_TIME_FORMAT) : null,
      isUpdated: strategieCmgGed.isUpdated,
      dateModified: strategieCmgGed.dateModified ? strategieCmgGed.dateModified.format(DATE_TIME_FORMAT) : null,
      aide: strategieCmgGed.aide,
      tiersFinanceur: strategieCmgGed.tiersFinanceur,
    });

    this.aidesSharedCollection = this.aideService.addAideToCollectionIfMissing(this.aidesSharedCollection, strategieCmgGed.aide);
    this.tiersFinanceursSharedCollection = this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(
      this.tiersFinanceursSharedCollection,
      strategieCmgGed.tiersFinanceur
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

  protected createFromForm(): IStrategieCmgGed {
    return {
      ...new StrategieCmgGed(),
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
