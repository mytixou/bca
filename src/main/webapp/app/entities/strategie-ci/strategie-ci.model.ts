import * as dayjs from 'dayjs';
import { IAide } from 'app/entities/aide/aide.model';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { INatureActivite } from 'app/entities/nature-activite/nature-activite.model';
import { INatureMontant } from 'app/entities/nature-montant/nature-montant.model';

export interface IStrategieCi {
  id?: number;
  isActif?: boolean;
  dateAnnuelleDebutValidite?: dayjs.Dayjs;
  anne?: number;
  montantPlafondDefaut?: number;
  montantPlafondHandicape?: number;
  montantPlafondDefautPlus?: number;
  montantPlafondHandicapePlus?: number;
  tauxSalaire?: number;
  aide?: IAide | null;
  tiersFinanceur?: ITiersFinanceur | null;
  natureActivites?: INatureActivite[] | null;
  natureMontants?: INatureMontant[] | null;
}

export class StrategieCi implements IStrategieCi {
  constructor(
    public id?: number,
    public isActif?: boolean,
    public dateAnnuelleDebutValidite?: dayjs.Dayjs,
    public anne?: number,
    public montantPlafondDefaut?: number,
    public montantPlafondHandicape?: number,
    public montantPlafondDefautPlus?: number,
    public montantPlafondHandicapePlus?: number,
    public tauxSalaire?: number,
    public aide?: IAide | null,
    public tiersFinanceur?: ITiersFinanceur | null,
    public natureActivites?: INatureActivite[] | null,
    public natureMontants?: INatureMontant[] | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getStrategieCiIdentifier(strategieCi: IStrategieCi): number | undefined {
  return strategieCi.id;
}
