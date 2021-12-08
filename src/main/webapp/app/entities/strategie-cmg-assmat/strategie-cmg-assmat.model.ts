import * as dayjs from 'dayjs';
import { ITrancheAideEnfantAssmat } from 'app/entities/tranche-aide-enfant-assmat/tranche-aide-enfant-assmat.model';
import { IAide } from 'app/entities/aide/aide.model';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';

export interface IStrategieCmgAssmat {
  id?: number;
  anne?: number;
  mois?: number;
  nbHeureSeuilPlafond?: number;
  tauxSalaire?: number;
  tauxCotisations?: number;
  isActif?: boolean;
  dateCreated?: dayjs.Dayjs;
  isUpdated?: boolean;
  dateModified?: dayjs.Dayjs | null;
  trancheAideEnfantAssmats?: ITrancheAideEnfantAssmat[] | null;
  aide?: IAide | null;
  tiersFinanceur?: ITiersFinanceur | null;
}

export class StrategieCmgAssmat implements IStrategieCmgAssmat {
  constructor(
    public id?: number,
    public anne?: number,
    public mois?: number,
    public nbHeureSeuilPlafond?: number,
    public tauxSalaire?: number,
    public tauxCotisations?: number,
    public isActif?: boolean,
    public dateCreated?: dayjs.Dayjs,
    public isUpdated?: boolean,
    public dateModified?: dayjs.Dayjs | null,
    public trancheAideEnfantAssmats?: ITrancheAideEnfantAssmat[] | null,
    public aide?: IAide | null,
    public tiersFinanceur?: ITiersFinanceur | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isUpdated = this.isUpdated ?? false;
  }
}

export function getStrategieCmgAssmatIdentifier(strategieCmgAssmat: IStrategieCmgAssmat): number | undefined {
  return strategieCmgAssmat.id;
}
