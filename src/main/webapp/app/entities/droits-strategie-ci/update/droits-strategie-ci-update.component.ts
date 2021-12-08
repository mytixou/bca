import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDroitsStrategieCi, DroitsStrategieCi } from '../droits-strategie-ci.model';
import { DroitsStrategieCiService } from '../service/droits-strategie-ci.service';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

@Component({
  selector: 'jhi-droits-strategie-ci-update',
  templateUrl: './droits-strategie-ci-update.component.html',
})
export class DroitsStrategieCiUpdateComponent implements OnInit {
  isSaving = false;

  beneficiairesSharedCollection: IBeneficiaire[] = [];

  editForm = this.fb.group({
    id: [],
    isActif: [null, [Validators.required]],
    anne: [null, [Validators.required]],
    montantPlafondDefaut: [null, [Validators.required]],
    montantPlafondHandicape: [null, [Validators.required]],
    montantPlafondDefautPlus: [null, [Validators.required]],
    montantPlafondHandicapePlus: [null, [Validators.required]],
    tauxSalaire: [null, [Validators.required]],
    dateOuverture: [null, [Validators.required]],
    dateFermeture: [],
    beneficiaire: [],
  });

  constructor(
    protected droitsStrategieCiService: DroitsStrategieCiService,
    protected beneficiaireService: BeneficiaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategieCi }) => {
      this.updateForm(droitsStrategieCi);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const droitsStrategieCi = this.createFromForm();
    if (droitsStrategieCi.id !== undefined) {
      this.subscribeToSaveResponse(this.droitsStrategieCiService.update(droitsStrategieCi));
    } else {
      this.subscribeToSaveResponse(this.droitsStrategieCiService.create(droitsStrategieCi));
    }
  }

  trackBeneficiaireById(index: number, item: IBeneficiaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroitsStrategieCi>>): void {
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

  protected updateForm(droitsStrategieCi: IDroitsStrategieCi): void {
    this.editForm.patchValue({
      id: droitsStrategieCi.id,
      isActif: droitsStrategieCi.isActif,
      anne: droitsStrategieCi.anne,
      montantPlafondDefaut: droitsStrategieCi.montantPlafondDefaut,
      montantPlafondHandicape: droitsStrategieCi.montantPlafondHandicape,
      montantPlafondDefautPlus: droitsStrategieCi.montantPlafondDefautPlus,
      montantPlafondHandicapePlus: droitsStrategieCi.montantPlafondHandicapePlus,
      tauxSalaire: droitsStrategieCi.tauxSalaire,
      dateOuverture: droitsStrategieCi.dateOuverture,
      dateFermeture: droitsStrategieCi.dateFermeture,
      beneficiaire: droitsStrategieCi.beneficiaire,
    });

    this.beneficiairesSharedCollection = this.beneficiaireService.addBeneficiaireToCollectionIfMissing(
      this.beneficiairesSharedCollection,
      droitsStrategieCi.beneficiaire
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

  protected createFromForm(): IDroitsStrategieCi {
    return {
      ...new DroitsStrategieCi(),
      id: this.editForm.get(['id'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      montantPlafondDefaut: this.editForm.get(['montantPlafondDefaut'])!.value,
      montantPlafondHandicape: this.editForm.get(['montantPlafondHandicape'])!.value,
      montantPlafondDefautPlus: this.editForm.get(['montantPlafondDefautPlus'])!.value,
      montantPlafondHandicapePlus: this.editForm.get(['montantPlafondHandicapePlus'])!.value,
      tauxSalaire: this.editForm.get(['tauxSalaire'])!.value,
      dateOuverture: this.editForm.get(['dateOuverture'])!.value,
      dateFermeture: this.editForm.get(['dateFermeture'])!.value,
      beneficiaire: this.editForm.get(['beneficiaire'])!.value,
    };
  }
}
