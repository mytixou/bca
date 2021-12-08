import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProduit, Produit } from '../produit.model';
import { ProduitService } from '../service/produit.service';
import { TypeProduit } from 'app/entities/enumerations/type-produit.model';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;
  typeProduitValues = Object.keys(TypeProduit);

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    isActif: [null, [Validators.required]],
    dateLancement: [null, [Validators.required]],
    anneLancement: [null, [Validators.required]],
    moisLancement: [null, [Validators.required]],
    dateResiliation: [],
    derniereAnnee: [],
    dernierMois: [],
  });

  constructor(protected produitService: ProduitService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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

  protected updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      nom: produit.nom,
      isActif: produit.isActif,
      dateLancement: produit.dateLancement,
      anneLancement: produit.anneLancement,
      moisLancement: produit.moisLancement,
      dateResiliation: produit.dateResiliation,
      derniereAnnee: produit.derniereAnnee,
      dernierMois: produit.dernierMois,
    });
  }

  protected createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateLancement: this.editForm.get(['dateLancement'])!.value,
      anneLancement: this.editForm.get(['anneLancement'])!.value,
      moisLancement: this.editForm.get(['moisLancement'])!.value,
      dateResiliation: this.editForm.get(['dateResiliation'])!.value,
      derniereAnnee: this.editForm.get(['derniereAnnee'])!.value,
      dernierMois: this.editForm.get(['dernierMois'])!.value,
    };
  }
}
