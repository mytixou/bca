import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITrancheAideEnfantAssmat, TrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';
import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';
import { IStrategieCmgAssmat } from 'app/entities/strategie-cmg-assmat/strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from 'app/entities/strategie-cmg-assmat/service/strategie-cmg-assmat.service';

@Component({
  selector: 'jhi-tranche-aide-enfant-assmat-update',
  templateUrl: './tranche-aide-enfant-assmat-update.component.html',
})
export class TrancheAideEnfantAssmatUpdateComponent implements OnInit {
  isSaving = false;

  strategieCmgAssmatsSharedCollection: IStrategieCmgAssmat[] = [];

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
    strategieCmgAssmat: [],
  });

  constructor(
    protected trancheAideEnfantAssmatService: TrancheAideEnfantAssmatService,
    protected strategieCmgAssmatService: StrategieCmgAssmatService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trancheAideEnfantAssmat }) => {
      if (trancheAideEnfantAssmat.id === undefined) {
        const today = dayjs().startOf('day');
        trancheAideEnfantAssmat.dateCreated = today;
        trancheAideEnfantAssmat.dateModified = today;
      }

      this.updateForm(trancheAideEnfantAssmat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trancheAideEnfantAssmat = this.createFromForm();
    if (trancheAideEnfantAssmat.id !== undefined) {
      this.subscribeToSaveResponse(this.trancheAideEnfantAssmatService.update(trancheAideEnfantAssmat));
    } else {
      this.subscribeToSaveResponse(this.trancheAideEnfantAssmatService.create(trancheAideEnfantAssmat));
    }
  }

  trackStrategieCmgAssmatById(index: number, item: IStrategieCmgAssmat): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrancheAideEnfantAssmat>>): void {
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

  protected updateForm(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): void {
    this.editForm.patchValue({
      id: trancheAideEnfantAssmat.id,
      anne: trancheAideEnfantAssmat.anne,
      mois: trancheAideEnfantAssmat.mois,
      ageEnfantRevoluSurPeriode: trancheAideEnfantAssmat.ageEnfantRevoluSurPeriode,
      montantPlafondSalaire: trancheAideEnfantAssmat.montantPlafondSalaire,
      isActif: trancheAideEnfantAssmat.isActif,
      dateCreated: trancheAideEnfantAssmat.dateCreated ? trancheAideEnfantAssmat.dateCreated.format(DATE_TIME_FORMAT) : null,
      isUpdated: trancheAideEnfantAssmat.isUpdated,
      dateModified: trancheAideEnfantAssmat.dateModified ? trancheAideEnfantAssmat.dateModified.format(DATE_TIME_FORMAT) : null,
      strategieCmgAssmat: trancheAideEnfantAssmat.strategieCmgAssmat,
    });

    this.strategieCmgAssmatsSharedCollection = this.strategieCmgAssmatService.addStrategieCmgAssmatToCollectionIfMissing(
      this.strategieCmgAssmatsSharedCollection,
      trancheAideEnfantAssmat.strategieCmgAssmat
    );
  }

  protected loadRelationshipsOptions(): void {
    this.strategieCmgAssmatService
      .query()
      .pipe(map((res: HttpResponse<IStrategieCmgAssmat[]>) => res.body ?? []))
      .pipe(
        map((strategieCmgAssmats: IStrategieCmgAssmat[]) =>
          this.strategieCmgAssmatService.addStrategieCmgAssmatToCollectionIfMissing(
            strategieCmgAssmats,
            this.editForm.get('strategieCmgAssmat')!.value
          )
        )
      )
      .subscribe((strategieCmgAssmats: IStrategieCmgAssmat[]) => (this.strategieCmgAssmatsSharedCollection = strategieCmgAssmats));
  }

  protected createFromForm(): ITrancheAideEnfantAssmat {
    return {
      ...new TrancheAideEnfantAssmat(),
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
      strategieCmgAssmat: this.editForm.get(['strategieCmgAssmat'])!.value,
    };
  }
}
