import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITiersFinanceur, TiersFinanceur } from '../tiers-financeur.model';
import { TiersFinanceurService } from '../service/tiers-financeur.service';

@Component({
  selector: 'jhi-tiers-financeur-update',
  templateUrl: './tiers-financeur-update.component.html',
})
export class TiersFinanceurUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    localisation: [],
    isActif: [null, [Validators.required]],
    dateInscription: [null, [Validators.required]],
    anneLancement: [null, [Validators.required]],
    moisLancement: [null, [Validators.required]],
    recupHeureActif: [null, [Validators.required]],
    dateResiliation: [],
    derniereAnnee: [],
    dernierMois: [],
  });

  constructor(
    protected tiersFinanceurService: TiersFinanceurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiersFinanceur }) => {
      this.updateForm(tiersFinanceur);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tiersFinanceur = this.createFromForm();
    if (tiersFinanceur.id !== undefined) {
      this.subscribeToSaveResponse(this.tiersFinanceurService.update(tiersFinanceur));
    } else {
      this.subscribeToSaveResponse(this.tiersFinanceurService.create(tiersFinanceur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITiersFinanceur>>): void {
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

  protected updateForm(tiersFinanceur: ITiersFinanceur): void {
    this.editForm.patchValue({
      id: tiersFinanceur.id,
      nom: tiersFinanceur.nom,
      localisation: tiersFinanceur.localisation,
      isActif: tiersFinanceur.isActif,
      dateInscription: tiersFinanceur.dateInscription,
      anneLancement: tiersFinanceur.anneLancement,
      moisLancement: tiersFinanceur.moisLancement,
      recupHeureActif: tiersFinanceur.recupHeureActif,
      dateResiliation: tiersFinanceur.dateResiliation,
      derniereAnnee: tiersFinanceur.derniereAnnee,
      dernierMois: tiersFinanceur.dernierMois,
    });
  }

  protected createFromForm(): ITiersFinanceur {
    return {
      ...new TiersFinanceur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateInscription: this.editForm.get(['dateInscription'])!.value,
      anneLancement: this.editForm.get(['anneLancement'])!.value,
      moisLancement: this.editForm.get(['moisLancement'])!.value,
      recupHeureActif: this.editForm.get(['recupHeureActif'])!.value,
      dateResiliation: this.editForm.get(['dateResiliation'])!.value,
      derniereAnnee: this.editForm.get(['derniereAnnee'])!.value,
      dernierMois: this.editForm.get(['dernierMois'])!.value,
    };
  }
}
