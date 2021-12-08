import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDroitAide, DroitAide } from '../droit-aide.model';
import { DroitAideService } from '../service/droit-aide.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';

@Component({
  selector: 'jhi-droit-aide-update',
  templateUrl: './droit-aide-update.component.html',
})
export class DroitAideUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  aidesSharedCollection: IAide[] = [];

  editForm = this.fb.group({
    id: [],
    isActif: [null, [Validators.required]],
    anne: [null, [Validators.required]],
    dateOuverture: [null, [Validators.required]],
    dateFermeture: [],
    produit: [],
    produit: [],
  });

  constructor(
    protected droitAideService: DroitAideService,
    protected produitService: ProduitService,
    protected aideService: AideService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitAide }) => {
      this.updateForm(droitAide);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const droitAide = this.createFromForm();
    if (droitAide.id !== undefined) {
      this.subscribeToSaveResponse(this.droitAideService.update(droitAide));
    } else {
      this.subscribeToSaveResponse(this.droitAideService.create(droitAide));
    }
  }

  trackProduitById(index: number, item: IProduit): number {
    return item.id!;
  }

  trackAideById(index: number, item: IAide): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroitAide>>): void {
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

  protected updateForm(droitAide: IDroitAide): void {
    this.editForm.patchValue({
      id: droitAide.id,
      isActif: droitAide.isActif,
      anne: droitAide.anne,
      dateOuverture: droitAide.dateOuverture,
      dateFermeture: droitAide.dateFermeture,
      produit: droitAide.produit,
      produit: droitAide.produit,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(this.produitsSharedCollection, droitAide.produit);
    this.aidesSharedCollection = this.aideService.addAideToCollectionIfMissing(this.aidesSharedCollection, droitAide.produit);
  }

  protected loadRelationshipsOptions(): void {
    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing(produits, this.editForm.get('produit')!.value))
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));

    this.aideService
      .query()
      .pipe(map((res: HttpResponse<IAide[]>) => res.body ?? []))
      .pipe(map((aides: IAide[]) => this.aideService.addAideToCollectionIfMissing(aides, this.editForm.get('produit')!.value)))
      .subscribe((aides: IAide[]) => (this.aidesSharedCollection = aides));
  }

  protected createFromForm(): IDroitAide {
    return {
      ...new DroitAide(),
      id: this.editForm.get(['id'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      dateOuverture: this.editForm.get(['dateOuverture'])!.value,
      dateFermeture: this.editForm.get(['dateFermeture'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      produit: this.editForm.get(['produit'])!.value,
    };
  }
}
