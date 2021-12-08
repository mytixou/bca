import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEnfant, Enfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

@Component({
  selector: 'jhi-enfant-update',
  templateUrl: './enfant-update.component.html',
})
export class EnfantUpdateComponent implements OnInit {
  isSaving = false;

  beneficiairesSharedCollection: IBeneficiaire[] = [];

  editForm = this.fb.group({
    id: [],
    isActif: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    dateInscription: [null, [Validators.required]],
    dateResiliation: [],
    beneficiaire: [],
  });

  constructor(
    protected enfantService: EnfantService,
    protected beneficiaireService: BeneficiaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enfant }) => {
      this.updateForm(enfant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enfant = this.createFromForm();
    if (enfant.id !== undefined) {
      this.subscribeToSaveResponse(this.enfantService.update(enfant));
    } else {
      this.subscribeToSaveResponse(this.enfantService.create(enfant));
    }
  }

  trackBeneficiaireById(index: number, item: IBeneficiaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnfant>>): void {
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

  protected updateForm(enfant: IEnfant): void {
    this.editForm.patchValue({
      id: enfant.id,
      isActif: enfant.isActif,
      dateNaissance: enfant.dateNaissance,
      dateInscription: enfant.dateInscription,
      dateResiliation: enfant.dateResiliation,
      beneficiaire: enfant.beneficiaire,
    });

    this.beneficiairesSharedCollection = this.beneficiaireService.addBeneficiaireToCollectionIfMissing(
      this.beneficiairesSharedCollection,
      enfant.beneficiaire
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

  protected createFromForm(): IEnfant {
    return {
      ...new Enfant(),
      id: this.editForm.get(['id'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      dateInscription: this.editForm.get(['dateInscription'])!.value,
      dateResiliation: this.editForm.get(['dateResiliation'])!.value,
      beneficiaire: this.editForm.get(['beneficiaire'])!.value,
    };
  }
}
