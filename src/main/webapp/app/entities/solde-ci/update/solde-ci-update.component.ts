import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISoldeCi, SoldeCi } from '../solde-ci.model';
import { SoldeCiService } from '../service/solde-ci.service';
import { IDroitsStrategieCi } from 'app/entities/droits-strategie-ci/droits-strategie-ci.model';
import { DroitsStrategieCiService } from 'app/entities/droits-strategie-ci/service/droits-strategie-ci.service';

@Component({
  selector: 'jhi-solde-ci-update',
  templateUrl: './solde-ci-update.component.html',
})
export class SoldeCiUpdateComponent implements OnInit {
  isSaving = false;

  droitsStrategieCisSharedCollection: IDroitsStrategieCi[] = [];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    isDernier: [null, [Validators.required]],
    annee: [null, [Validators.required]],
    consoMontantCi: [null, [Validators.required]],
    consoCiRec: [null, [Validators.required]],
    soldeMontantCi: [null, [Validators.required]],
    soldeMontantCiRec: [null, [Validators.required]],
    droitsStrategieCi: [],
  });

  constructor(
    protected soldeCiService: SoldeCiService,
    protected droitsStrategieCiService: DroitsStrategieCiService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeCi }) => {
      if (soldeCi.id === undefined) {
        const today = dayjs().startOf('day');
        soldeCi.date = today;
      }

      this.updateForm(soldeCi);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const soldeCi = this.createFromForm();
    if (soldeCi.id !== undefined) {
      this.subscribeToSaveResponse(this.soldeCiService.update(soldeCi));
    } else {
      this.subscribeToSaveResponse(this.soldeCiService.create(soldeCi));
    }
  }

  trackDroitsStrategieCiById(index: number, item: IDroitsStrategieCi): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoldeCi>>): void {
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

  protected updateForm(soldeCi: ISoldeCi): void {
    this.editForm.patchValue({
      id: soldeCi.id,
      date: soldeCi.date ? soldeCi.date.format(DATE_TIME_FORMAT) : null,
      isActif: soldeCi.isActif,
      isDernier: soldeCi.isDernier,
      annee: soldeCi.annee,
      consoMontantCi: soldeCi.consoMontantCi,
      consoCiRec: soldeCi.consoCiRec,
      soldeMontantCi: soldeCi.soldeMontantCi,
      soldeMontantCiRec: soldeCi.soldeMontantCiRec,
      droitsStrategieCi: soldeCi.droitsStrategieCi,
    });

    this.droitsStrategieCisSharedCollection = this.droitsStrategieCiService.addDroitsStrategieCiToCollectionIfMissing(
      this.droitsStrategieCisSharedCollection,
      soldeCi.droitsStrategieCi
    );
  }

  protected loadRelationshipsOptions(): void {
    this.droitsStrategieCiService
      .query()
      .pipe(map((res: HttpResponse<IDroitsStrategieCi[]>) => res.body ?? []))
      .pipe(
        map((droitsStrategieCis: IDroitsStrategieCi[]) =>
          this.droitsStrategieCiService.addDroitsStrategieCiToCollectionIfMissing(
            droitsStrategieCis,
            this.editForm.get('droitsStrategieCi')!.value
          )
        )
      )
      .subscribe((droitsStrategieCis: IDroitsStrategieCi[]) => (this.droitsStrategieCisSharedCollection = droitsStrategieCis));
  }

  protected createFromForm(): ISoldeCi {
    return {
      ...new SoldeCi(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      isActif: this.editForm.get(['isActif'])!.value,
      isDernier: this.editForm.get(['isDernier'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      consoMontantCi: this.editForm.get(['consoMontantCi'])!.value,
      consoCiRec: this.editForm.get(['consoCiRec'])!.value,
      soldeMontantCi: this.editForm.get(['soldeMontantCi'])!.value,
      soldeMontantCiRec: this.editForm.get(['soldeMontantCiRec'])!.value,
      droitsStrategieCi: this.editForm.get(['droitsStrategieCi'])!.value,
    };
  }
}
