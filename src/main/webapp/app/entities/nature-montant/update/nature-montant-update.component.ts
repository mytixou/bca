import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INatureMontant, NatureMontant } from '../nature-montant.model';
import { NatureMontantService } from '../service/nature-montant.service';

@Component({
  selector: 'jhi-nature-montant-update',
  templateUrl: './nature-montant-update.component.html',
})
export class NatureMontantUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [],
    description: [],
  });

  constructor(protected natureMontantService: NatureMontantService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureMontant }) => {
      this.updateForm(natureMontant);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureMontant = this.createFromForm();
    if (natureMontant.id !== undefined) {
      this.subscribeToSaveResponse(this.natureMontantService.update(natureMontant));
    } else {
      this.subscribeToSaveResponse(this.natureMontantService.create(natureMontant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureMontant>>): void {
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

  protected updateForm(natureMontant: INatureMontant): void {
    this.editForm.patchValue({
      id: natureMontant.id,
      code: natureMontant.code,
      libelle: natureMontant.libelle,
      description: natureMontant.description,
    });
  }

  protected createFromForm(): INatureMontant {
    return {
      ...new NatureMontant(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
