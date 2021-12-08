import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDroitsStrategiePchE, DroitsStrategiePchE } from '../droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

@Component({
  selector: 'jhi-droits-strategie-pch-e-update',
  templateUrl: './droits-strategie-pch-e-update.component.html',
})
export class DroitsStrategiePchEUpdateComponent implements OnInit {
  isSaving = false;

  beneficiairesSharedCollection: IBeneficiaire[] = [];

  editForm = this.fb.group({
    id: [],
    isActif: [null, [Validators.required]],
    anne: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    montantPlafond: [null, [Validators.required]],
    montantPlafondPlus: [null, [Validators.required]],
    nbHeurePlafond: [null, [Validators.required]],
    tauxCotisations: [null, [Validators.required]],
    dateOuverture: [null, [Validators.required]],
    dateFermeture: [],
    beneficiaire: [],
  });

  constructor(
    protected droitsStrategiePchEService: DroitsStrategiePchEService,
    protected beneficiaireService: BeneficiaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategiePchE }) => {
      this.updateForm(droitsStrategiePchE);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const droitsStrategiePchE = this.createFromForm();
    if (droitsStrategiePchE.id !== undefined) {
      this.subscribeToSaveResponse(this.droitsStrategiePchEService.update(droitsStrategiePchE));
    } else {
      this.subscribeToSaveResponse(this.droitsStrategiePchEService.create(droitsStrategiePchE));
    }
  }

  trackBeneficiaireById(index: number, item: IBeneficiaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroitsStrategiePchE>>): void {
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

  protected updateForm(droitsStrategiePchE: IDroitsStrategiePchE): void {
    this.editForm.patchValue({
      id: droitsStrategiePchE.id,
      isActif: droitsStrategiePchE.isActif,
      anne: droitsStrategiePchE.anne,
      mois: droitsStrategiePchE.mois,
      montantPlafond: droitsStrategiePchE.montantPlafond,
      montantPlafondPlus: droitsStrategiePchE.montantPlafondPlus,
      nbHeurePlafond: droitsStrategiePchE.nbHeurePlafond,
      tauxCotisations: droitsStrategiePchE.tauxCotisations,
      dateOuverture: droitsStrategiePchE.dateOuverture,
      dateFermeture: droitsStrategiePchE.dateFermeture,
      beneficiaire: droitsStrategiePchE.beneficiaire,
    });

    this.beneficiairesSharedCollection = this.beneficiaireService.addBeneficiaireToCollectionIfMissing(
      this.beneficiairesSharedCollection,
      droitsStrategiePchE.beneficiaire
    );
  }

  protected loadRelationshipsOptions(): void {
    this.beneficiaireService
      .query()
      .pipe(map((res: HttpResponse<IBeneficiaire[]>) => res.body ?? []))
      .pipe(
        map((beneficiaires: IBeneficiaire[]) =>
          this.beneficiaireService.addBeneficiaireToCollectionIfMissing(beneficiaires, this.editForm.get('beneficiaire')!.value)
        )
      )
      .subscribe((beneficiaires: IBeneficiaire[]) => (this.beneficiairesSharedCollection = beneficiaires));
  }

  protected createFromForm(): IDroitsStrategiePchE {
    return {
      ...new DroitsStrategiePchE(),
      id: this.editForm.get(['id'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      montantPlafond: this.editForm.get(['montantPlafond'])!.value,
      montantPlafondPlus: this.editForm.get(['montantPlafondPlus'])!.value,
      nbHeurePlafond: this.editForm.get(['nbHeurePlafond'])!.value,
      tauxCotisations: this.editForm.get(['tauxCotisations'])!.value,
      dateOuverture: this.editForm.get(['dateOuverture'])!.value,
      dateFermeture: this.editForm.get(['dateFermeture'])!.value,
      beneficiaire: this.editForm.get(['beneficiaire'])!.value,
    };
  }
}
