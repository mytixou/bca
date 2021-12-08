import * as dayjs from 'dayjs';
import { IAide } from 'app/entities/aide/aide.model';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { INatureActivite } from 'app/entities/nature-activite/nature-activite.model';
import { INatureMontant } from 'app/entities/nature-montant/nature-montant.model';

export interface IStrategiePchE {
  id?: number;
  isActif?: boolean;
  dateMensuelleDebutValidite?: dayjs.Dayjs;
  anne?: number;
  mois?: number;
  montantPlafondSalaire?: number;
  montantPlafondCotisations?: number;
  montantPlafondSalairePlus?: number;
  montantPlafondCotisationsPlus?: number;
  nbHeureSalairePlafond?: number;
  tauxSalaire?: number;
  tauxCotisations?: number;
  aide?: IAide | null;
  tiersFinanceur?: ITiersFinanceur | null;
  natureActivites?: INatureActivite[] | null;
  natureMontants?: INatureMontant[] | null;
}

export class StrategiePchE implements IStrategiePchE {
  constructor(
    public id?: number,
    public isActif?: boolean,
    public dateMensuelleDebutValidite?: dayjs.Dayjs,
    public anne?: number,
    public mois?: number,
    public montantPlafondSalaire?: number,
    public montantPlafondCotisations?: number,
    public montantPlafondSalairePlus?: number,
    public montantPlafondCotisationsPlus?: number,
    public nbHeureSalairePlafond?: number,
    public tauxSalaire?: number,
    public tauxCotisations?: number,
    public aide?: IAide | null,
    public tiersFinanceur?: ITiersFinanceur | null,
    public natureActivites?: INatureActivite[] | null,
    public natureMontants?: INatureMontant[] | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getStrategiePchEIdentifier(strategiePchE: IStrategiePchE): number | undefined {
  return strategiePchE.id;
}
