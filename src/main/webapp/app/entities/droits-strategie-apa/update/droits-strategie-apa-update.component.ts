import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDroitsStrategieApa, DroitsStrategieApa } from '../droits-strategie-apa.model';
import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

@Component({
  selector: 'jhi-droits-strategie-apa-update',
  templateUrl: './droits-strategie-apa-update.component.html',
})
export class DroitsStrategieApaUpdateComponent implements OnInit {
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
    protected droitsStrategieApaService: DroitsStrategieApaService,
    protected beneficiaireService: BeneficiaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategieApa }) => {
      this.updateForm(droitsStrategieApa);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const droitsStrategieApa = this.createFromForm();
    if (droitsStrategieApa.id !== undefined) {
      this.subscribeToSaveResponse(this.droitsStrategieApaService.update(droitsStrategieApa));
    } else {
      this.subscribeToSaveResponse(this.droitsStrategieApaService.create(droitsStrategieApa));
    }
  }

  trackBeneficiaireById(index: number, item: IBeneficiaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroitsStrategieApa>>): void {
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

  protected updateForm(droitsStrategieApa: IDroitsStrategieApa): void {
    this.editForm.patchValue({
      id: droitsStrategieApa.id,
      isActif: droitsStrategieApa.isActif,
      anne: droitsStrategieApa.anne,
      mois: droitsStrategieApa.mois,
      montantPlafond: droitsStrategieApa.montantPlafond,
      montantPlafondPlus: droitsStrategieApa.montantPlafondPlus,
      nbHeurePlafond: droitsStrategieApa.nbHeurePlafond,
      tauxCotisations: droitsStrategieApa.tauxCotisations,
      dateOuverture: droitsStrategieApa.dateOuverture,
      dateFermeture: droitsStrategieApa.dateFermeture,
      beneficiaire: droitsStrategieApa.beneficiaire,
    });

    this.beneficiairesSharedCollection = this.beneficiaireService.addBeneficiaireToCollectionIfMissing(
      this.beneficiairesSharedCollection,
      droitsStrategieApa.beneficiaire
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

  protected createFromForm(): IDroitsStrategieApa {
    return {
      ...new DroitsStrategieApa(),
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
