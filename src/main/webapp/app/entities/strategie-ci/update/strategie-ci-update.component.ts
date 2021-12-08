import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStrategieCi, StrategieCi } from '../strategie-ci.model';
import { StrategieCiService } from '../service/strategie-ci.service';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';
import { INatureActivite } from 'app/entities/nature-activite/nature-activite.model';
import { NatureActiviteService } from 'app/entities/nature-activite/service/nature-activite.service';
import { INatureMontant } from 'app/entities/nature-montant/nature-montant.model';
import { NatureMontantService } from 'app/entities/nature-montant/service/nature-montant.service';

@Component({
  selector: 'jhi-strategie-ci-update',
  templateUrl: './strategie-ci-update.component.html',
})
export class StrategieCiUpdateComponent implements OnInit {
  isSaving = false;

  aidesSharedCollection: IAide[] = [];
  tiersFinanceursSharedCollection: ITiersFinanceur[] = [];
  natureActivitesSharedCollection: INatureActivite[] = [];
  natureMontantsSharedCollection: INatureMontant[] = [];

  editForm = this.fb.group({
    id: [],
    isActif: [null, [Validators.required]],
    dateAnnuelleDebutValidite: [null, [Validators.required]],
    anne: [null, [Validators.required]],
    montantPlafondDefaut: [null, [Validators.required]],
    montantPlafondHandicape: [null, [Validators.required]],
    montantPlafondDefautPlus: [null, [Validators.required]],
    montantPlafondHandicapePlus: [null, [Validators.required]],
    tauxSalaire: [null, [Validators.required]],
    aide: [],
    tiersFinanceur: [],
    natureActivites: [],
    natureMontants: [],
  });

  constructor(
    protected strategieCiService: StrategieCiService,
    protected aideService: AideService,
    protected tiersFinanceurService: TiersFinanceurService,
    protected natureActiviteService: NatureActiviteService,
    protected natureMontantService: NatureMontantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strategieCi }) => {
      this.updateForm(strategieCi);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const strategieCi = this.createFromForm();
    if (strategieCi.id !== undefined) {
      this.subscribeToSaveResponse(this.strategieCiService.update(strategieCi));
    } else {
      this.subscribeToSaveResponse(this.strategieCiService.create(strategieCi));
    }
  }

  trackAideById(index: number, item: IAide): number {
    return item.id!;
  }

  trackTiersFinanceurById(index: number, item: ITiersFinanceur): number {
    return item.id!;
  }

  trackNatureActiviteById(index: number, item: INatureActivite): number {
    return item.id!;
  }

  trackNatureMontantById(index: number, item: INatureMontant): number {
    return item.id!;
  }

  getSelectedNatureActivite(option: INatureActivite, selectedVals?: INatureActivite[]): INatureActivite {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedNatureMontant(option: INatureMontant, selectedVals?: INatureMontant[]): INatureMontant {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStrategieCi>>): void {
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

  protected updateForm(strategieCi: IStrategieCi): void {
    this.editForm.patchValue({
      id: strategieCi.id,
      isActif: strategieCi.isActif,
      dateAnnuelleDebutValidite: strategieCi.dateAnnuelleDebutValidite,
      anne: strategieCi.anne,
      montantPlafondDefaut: strategieCi.montantPlafondDefaut,
      montantPlafondHandicape: strategieCi.montantPlafondHandicape,
      montantPlafondDefautPlus: strategieCi.montantPlafondDefautPlus,
      montantPlafondHandicapePlus: strategieCi.montantPlafondHandicapePlus,
      tauxSalaire: strategieCi.tauxSalaire,
      aide: strategieCi.aide,
      tiersFinanceur: strategieCi.tiersFinanceur,
      natureActivites: strategieCi.natureActivites,
      natureMontants: strategieCi.natureMontants,
    });

    this.aidesSharedCollection = this.aideService.addAideToCollectionIfMissing(this.aidesSharedCollection, strategieCi.aide);
    this.tiersFinanceursSharedCollection = this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(
      this.tiersFinanceursSharedCollection,
      strategieCi.tiersFinanceur
    );
    this.natureActivitesSharedCollection = this.natureActiviteService.addNatureActiviteToCollectionIfMissing(
      this.natureActivitesSharedCollection,
      ...(strategieCi.natureActivites ?? [])
    );
    this.natureMontantsSharedCollection = this.natureMontantService.addNatureMontantToCollectionIfMissing(
      this.natureMontantsSharedCollection,
      ...(strategieCi.natureMontants ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.aideService
      .query()
      .pipe(map((res: HttpResponse<IAide[]>) => res.body ?? []))
      .pipe(map((aides: IAide[]) => this.aideService.addAideToCollectionIfMissing(aides, this.editForm.get('aide')!.value)))
      .subscribe((aides: IAide[]) => (this.aidesSharedCollection = aides));

    this.tiersFinanceurService
      .query()
      .pipe(map((res: HttpResponse<ITiersFinanceur[]>) => res.body ?? []))
      .pipe(
        map((tiersFinanceurs: ITiersFinanceur[]) =>
          this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(tiersFinanceurs, this.editForm.get('tiersFinanceur')!.value)
        )
      )
      .subscribe((tiersFinanceurs: ITiersFinanceur[]) => (this.tiersFinanceursSharedCollection = tiersFinanceurs));

    this.natureActiviteService
      .query()
      .pipe(map((res: HttpResponse<INatureActivite[]>) => res.body ?? []))
      .pipe(
        map((natureActivites: INatureActivite[]) =>
          this.natureActiviteService.addNatureActiviteToCollectionIfMissing(
            natureActivites,
            ...(this.editForm.get('natureActivites')!.value ?? [])
          )
        )
      )
      .subscribe((natureActivites: INatureActivite[]) => (this.natureActivitesSharedCollection = natureActivites));

    this.natureMontantService
      .query()
      .pipe(map((res: HttpResponse<INatureMontant[]>) => res.body ?? []))
      .pipe(
        map((natureMontants: INatureMontant[]) =>
          this.natureMontantService.addNatureMontantToCollectionIfMissing(
            natureMontants,
            ...(this.editForm.get('natureMontants')!.value ?? [])
          )
        )
      )
      .subscribe((natureMontants: INatureMontant[]) => (this.natureMontantsSharedCollection = natureMontants));
  }

  protected createFromForm(): IStrategieCi {
    return {
      ...new StrategieCi(),
      id: this.editForm.get(['id'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateAnnuelleDebutValidite: this.editForm.get(['dateAnnuelleDebutValidite'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      montantPlafondDefaut: this.editForm.get(['montantPlafondDefaut'])!.value,
      montantPlafondHandicape: this.editForm.get(['montantPlafondHandicape'])!.value,
      montantPlafondDefautPlus: this.editForm.get(['montantPlafondDefautPlus'])!.value,
      montantPlafondHandicapePlus: this.editForm.get(['montantPlafondHandicapePlus'])!.value,
      tauxSalaire: this.editForm.get(['tauxSalaire'])!.value,
      aide: this.editForm.get(['aide'])!.value,
      tiersFinanceur: this.editForm.get(['tiersFinanceur'])!.value,
      natureActivites: this.editForm.get(['natureActivites'])!.value,
      natureMontants: this.editForm.get(['natureMontants'])!.value,
    };
  }
}
