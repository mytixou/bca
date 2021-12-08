import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITrancheAideEnfantGed, TrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';
import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';
import { IStrategieCmgGed } from 'app/entities/strategie-cmg-ged/strategie-cmg-ged.model';
import { StrategieCmgGedService } from 'app/entities/strategie-cmg-ged/service/strategie-cmg-ged.service';

@Component({
  selector: 'jhi-tranche-aide-enfant-ged-update',
  templateUrl: './tranche-aide-enfant-ged-update.component.html',
})
export class TrancheAideEnfantGedUpdateComponent implements OnInit {
  isSaving = false;

  strategieCmgGedsSharedCollection: IStrategieCmgGed[] = [];

  editForm = this.fb.group({
    id: [],
    anne: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    ageEnfantRevoluSurPeriode: [null, [Validators.required]],
    montantPlafondSalaire: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    dateCreated: [null, [Validators.required]],
    isUpdated: [null, [Validators.required]],
    dateModified: [],
    strategieCmgGed: [],
  });

  constructor(
    protected trancheAideEnfantGedService: TrancheAideEnfantGedService,
    protected strategieCmgGedService: StrategieCmgGedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trancheAideEnfantGed }) => {
      if (trancheAideEnfantGed.id === undefined) {
        const today = dayjs().startOf('day');
        trancheAideEnfantGed.dateCreated = today;
        trancheAideEnfantGed.dateModified = today;
      }

      this.updateForm(trancheAideEnfantGed);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trancheAideEnfantGed = this.createFromForm();
    if (trancheAideEnfantGed.id !== undefined) {
      this.subscribeToSaveResponse(this.trancheAideEnfantGedService.update(trancheAideEnfantGed));
    } else {
      this.subscribeToSaveResponse(this.trancheAideEnfantGedService.create(trancheAideEnfantGed));
    }
  }

  trackStrategieCmgGedById(index: number, item: IStrategieCmgGed): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrancheAideEnfantGed>>): void {
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

  protected updateForm(trancheAideEnfantGed: ITrancheAideEnfantGed): void {
    this.editForm.patchValue({
      id: trancheAideEnfantGed.id,
      anne: trancheAideEnfantGed.anne,
      mois: trancheAideEnfantGed.mois,
      ageEnfantRevoluSurPeriode: trancheAideEnfantGed.ageEnfantRevoluSurPeriode,
      montantPlafondSalaire: trancheAideEnfantGed.montantPlafondSalaire,
      isActif: trancheAideEnfantGed.isActif,
      dateCreated: trancheAideEnfantGed.dateCreated ? trancheAideEnfantGed.dateCreated.format(DATE_TIME_FORMAT) : null,
      isUpdated: trancheAideEnfantGed.isUpdated,
      dateModified: trancheAideEnfantGed.dateModified ? trancheAideEnfantGed.dateModified.format(DATE_TIME_FORMAT) : null,
      strategieCmgGed: trancheAideEnfantGed.strategieCmgGed,
    });

    this.strategieCmgGedsSharedCollection = this.strategieCmgGedService.addStrategieCmgGedToCollectionIfMissing(
      this.strategieCmgGedsSharedCollection,
      trancheAideEnfantGed.strategieCmgGed
    );
  }

  protected loadRelationshipsOptions(): void {
    this.strategieCmgGedService
      .query()
      .pipe(map((res: HttpResponse<IStrategieCmgGed[]>) => res.body ?? []))
      .pipe(
        map((strategieCmgGeds: IStrategieCmgGed[]) =>
          this.strategieCmgGedService.addStrategieCmgGedToCollectionIfMissing(strategieCmgGeds, this.editForm.get('strategieCmgGed')!.value)
        )
      )
      .subscribe((strategieCmgGeds: IStrategieCmgGed[]) => (this.strategieCmgGedsSharedCollection = strategieCmgGeds));
  }

  protected createFromForm(): ITrancheAideEnfantGed {
    return {
      ...new TrancheAideEnfantGed(),
      id: this.editForm.get(['id'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      ageEnfantRevoluSurPeriode: this.editForm.get(['ageEnfantRevoluSurPeriode'])!.value,
      montantPlafondSalaire: this.editForm.get(['montantPlafondSalaire'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? dayjs(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      isUpdated: this.editForm.get(['isUpdated'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value
        ? dayjs(this.editForm.get(['dateModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      strategieCmgGed: this.editForm.get(['strategieCmgGed'])!.value,
    };
  }
}
