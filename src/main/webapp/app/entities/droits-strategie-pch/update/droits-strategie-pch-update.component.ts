import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDroitsStrategiePch, DroitsStrategiePch } from '../droits-strategie-pch.model';
import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

@Component({
  selector: 'jhi-droits-strategie-pch-update',
  templateUrl: './droits-strategie-pch-update.component.html',
})
export class DroitsStrategiePchUpdateComponent implements OnInit {
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
    protected droitsStrategiePchService: DroitsStrategiePchService,
    protected beneficiaireService: BeneficiaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategiePch }) => {
      this.updateForm(droitsStrategiePch);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const droitsStrategiePch = this.createFromForm();
    if (droitsStrategiePch.id !== undefined) {
      this.subscribeToSaveResponse(this.droitsStrategiePchService.update(droitsStrategiePch));
    } else {
      this.subscribeToSaveResponse(this.droitsStrategiePchService.create(droitsStrategiePch));
    }
  }

  trackBeneficiaireById(index: number, item: IBeneficiaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroitsStrategiePch>>): void {
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

  protected updateForm(droitsStrategiePch: IDroitsStrategiePch): void {
    this.editForm.patchValue({
      id: droitsStrategiePch.id,
      isActif: droitsStrategiePch.isActif,
      anne: droitsStrategiePch.anne,
      mois: droitsStrategiePch.mois,
      montantPlafond: droitsStrategiePch.montantPlafond,
      montantPlafondPlus: droitsStrategiePch.montantPlafondPlus,
      nbHeurePlafond: droitsStrategiePch.nbHeurePlafond,
      tauxCotisations: droitsStrategiePch.tauxCotisations,
      dateOuverture: droitsStrategiePch.dateOuverture,
      dateFermeture: droitsStrategiePch.dateFermeture,
      beneficiaire: droitsStrategiePch.beneficiaire,
    });

    this.beneficiairesSharedCollection = this.beneficiaireService.addBeneficiaireToCollectionIfMissing(
      this.beneficiairesSharedCollection,
      droitsStrategiePch.beneficiaire
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

  protected createFromForm(): IDroitsStrategiePch {
    return {
      ...new DroitsStrategiePch(),
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
