import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStrategieApa, StrategieApa } from '../strategie-apa.model';
import { StrategieApaService } from '../service/strategie-apa.service';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';
import { INatureActivite } from 'app/entities/nature-activite/nature-activite.model';
import { NatureActiviteService } from 'app/entities/nature-activite/service/nature-activite.service';
import { INatureMontant } from 'app/entities/nature-montant/nature-montant.model';
import { NatureMontantService } from 'app/entities/nature-montant/service/nature-montant.service';

@Component({
  selector: 'jhi-strategie-apa-update',
  templateUrl: './strategie-apa-update.component.html',
})
export class StrategieApaUpdateComponent implements OnInit {
  isSaving = false;

  aidesSharedCollection: IAide[] = [];
  tiersFinanceursSharedCollection: ITiersFinanceur[] = [];
  natureActivitesSharedCollection: INatureActivite[] = [];
  natureMontantsSharedCollection: INatureMontant[] = [];

  editForm = this.fb.group({
    id: [],
    isActif: [null, [Validators.required]],
    dateMensuelleDebutValidite: [null, [Validators.required]],
    anne: [null, [Validators.required]],
    mois: [null, [Validators.required]],
    montantPlafondSalaire: [null, [Validators.required]],
    montantPlafondCotisations: [null, [Validators.required]],
    montantPlafondSalairePlus: [null, [Validators.required]],
    montantPlafondCotisationsPlus: [null, [Validators.required]],
    nbHeureSalairePlafond: [null, [Validators.required]],
    tauxSalaire: [null, [Validators.required]],
    tauxCotisations: [null, [Validators.required]],
    aide: [],
    tiersFinanceur: [],
    natureActivites: [],
    natureMontants: [],
  });

  constructor(
    protected strategieApaService: StrategieApaService,
    protected aideService: AideService,
    protected tiersFinanceurService: TiersFinanceurService,
    protected natureActiviteService: NatureActiviteService,
    protected natureMontantService: NatureMontantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strategieApa }) => {
      this.updateForm(strategieApa);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const strategieApa = this.createFromForm();
    if (strategieApa.id !== undefined) {
      this.subscribeToSaveResponse(this.strategieApaService.update(strategieApa));
    } else {
      this.subscribeToSaveResponse(this.strategieApaService.create(strategieApa));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStrategieApa>>): void {
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

  protected updateForm(strategieApa: IStrategieApa): void {
    this.editForm.patchValue({
      id: strategieApa.id,
      isActif: strategieApa.isActif,
      dateMensuelleDebutValidite: strategieApa.dateMensuelleDebutValidite,
      anne: strategieApa.anne,
      mois: strategieApa.mois,
      montantPlafondSalaire: strategieApa.montantPlafondSalaire,
      montantPlafondCotisations: strategieApa.montantPlafondCotisations,
      montantPlafondSalairePlus: strategieApa.montantPlafondSalairePlus,
      montantPlafondCotisationsPlus: strategieApa.montantPlafondCotisationsPlus,
      nbHeureSalairePlafond: strategieApa.nbHeureSalairePlafond,
      tauxSalaire: strategieApa.tauxSalaire,
      tauxCotisations: strategieApa.tauxCotisations,
      aide: strategieApa.aide,
      tiersFinanceur: strategieApa.tiersFinanceur,
      natureActivites: strategieApa.natureActivites,
      natureMontants: strategieApa.natureMontants,
    });

    this.aidesSharedCollection = this.aideService.addAideToCollectionIfMissing(this.aidesSharedCollection, strategieApa.aide);
    this.tiersFinanceursSharedCollection = this.tiersFinanceurService.addTiersFinanceurToCollectionIfMissing(
      this.tiersFinanceursSharedCollection,
      strategieApa.tiersFinanceur
    );
    this.natureActivitesSharedCollection = this.natureActiviteService.addNatureActiviteToCollectionIfMissing(
      this.natureActivitesSharedCollection,
      ...(strategieApa.natureActivites ?? [])
    );
    this.natureMontantsSharedCollection = this.natureMontantService.addNatureMontantToCollectionIfMissing(
      this.natureMontantsSharedCollection,
      ...(strategieApa.natureMontants ?? [])
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

  protected createFromForm(): IStrategieApa {
    return {
      ...new StrategieApa(),
      id: this.editForm.get(['id'])!.value,
      isActif: this.editForm.get(['isActif'])!.value,
      dateMensuelleDebutValidite: this.editForm.get(['dateMensuelleDebutValidite'])!.value,
      anne: this.editForm.get(['anne'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      montantPlafondSalaire: this.editForm.get(['montantPlafondSalaire'])!.value,
      montantPlafondCotisations: this.editForm.get(['montantPlafondCotisations'])!.value,
      montantPlafondSalairePlus: this.editForm.get(['montantPlafondSalairePlus'])!.value,
      montantPlafondCotisationsPlus: this.editForm.get(['montantPlafondCotisationsPlus'])!.value,
      nbHeureSalairePlafond: this.editForm.get(['nbHeureSalairePlafond'])!.value,
      tauxSalaire: this.editForm.get(['tauxSalaire'])!.value,
      tauxCotisations: this.editForm.get(['tauxCotisations'])!.value,
      aide: this.editForm.get(['aide'])!.value,
      tiersFinanceur: this.editForm.get(['tiersFinanceur'])!.value,
      natureActivites: this.editForm.get(['natureActivites'])!.value,
      natureMontants: this.editForm.get(['natureMontants'])!.value,
    };
  }
}
