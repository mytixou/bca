import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAide, Aide } from '../aide.model';
import { AideService } from '../service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';
import { TypeAide } from 'app/entities/enumerations/type-aide.model';

@Component({
  selector: 'jhi-aide-update',
  templateUrl: './aide-update.component.html',
})
export class AideUpdateComponent implements OnInit {
  isSaving = false;
  typeAideValues = Object.keys(TypeAide);

  tiersFinanceursSharedCollection: ITiersFinanceur[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    priorite: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    dateLancement: [null, [Validators.required]],
    anneLancement: [null, [Validators.required]],
    moisLancement: [null, [Validators.required]],
    dateArret: [],
    derniereAnnee: [],
    dernierMois: [],
    aide: [],
  });

  constructor(
    protected aideService: AideService,
    protected tiersFinanceurService: TiersFinanceurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aide }) => {
      this.updateForm(aide);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aide = this.createFromForm();
    if (aide.id !== undefined) {
      this.subscribeToSaveResponse(this.aideService.update(aide));
    } else {
      this.subscribeToSaveResponse(this.aideService.create(aide));
    }
  }

  trackTiersFinanceurById(index: number, item: ITiersFinanceur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAide>>): void {
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

  protected updateForm(aide: IAide): void {
    this.editForm.patchValue({
      id: aide.id,
      nom: aide.nom,
      priorite: aide.priorite,
      isActif: aide.isActif,
      dateLancement: aide.dateLancement,
      anneLancement: aide.anneLancement,
      moisLancement: aide.moisLancement,
      dateArret: aide.dateArret,
      derniereAnnee: aide.derniereAnnee,
      dernierMois: aide.dernierMois,
      aide: aide.aide,
    });

    this.tiersFinanceursSharedCollection = this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(
      this.tiersFinanceursSharedCollection,
      aide.aide
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tiersFinanceurService
      .query()
      .pipe(map((res: HttpResponse<ITiersFinanceur[]>) => res.body ?? []))
      .pipe(
        map((tiersFinanceurs: ITiersFinanceur[]) =>
          this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(tiersFinanceurs, this.editForm.get('aide')!.value)
        )
      )
      .subscribe((tiersFinanceurs: ITiersFinanceur[]) => (this.tiersFinanceursSharedCollection = tiersFinanceurs));
  }

  protected createFromForm(): IAide {
    return {
      ...new Aide(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateLancement: this.editForm.get(['dateLancement'])!.value,
      anneLancement: this.editForm.get(['anneLancement'])!.value,
      moisLancement: this.editForm.get(['moisLancement'])!.value,
      dateArret: this.editForm.get(['dateArret'])!.value,
      derniereAnnee: this.editForm.get(['derniereAnnee'])!.value,
      dernierMois: this.editForm.get(['dernierMois'])!.value,
      aide: this.editForm.get(['aide'])!.value,
    };
  }
}
