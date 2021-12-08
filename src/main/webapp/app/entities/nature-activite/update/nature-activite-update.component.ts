import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INatureActivite, NatureActivite } from '../nature-activite.model';
import { NatureActiviteService } from '../service/nature-activite.service';

@Component({
  selector: 'jhi-nature-activite-update',
  templateUrl: './nature-activite-update.component.html',
})
export class NatureActiviteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [],
    description: [],
  });

  constructor(
    protected natureActiviteService: NatureActiviteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureActivite }) => {
      this.updateForm(natureActivite);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureActivite = this.createFromForm();
    if (natureActivite.id !== undefined) {
      this.subscribeToSaveResponse(this.natureActiviteService.update(natureActivite));
    } else {
      this.subscribeToSaveResponse(this.natureActiviteService.create(natureActivite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureActivite>>): void {
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

  protected updateForm(natureActivite: INatureActivite): void {
    this.editForm.patchValue({
      id: natureActivite.id,
      code: natureActivite.code,
      libelle: natureActivite.libelle,
      description: natureActivite.description,
    });
  }

  protected createFromForm(): INatureActivite {
    return {
      ...new NatureActivite(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
