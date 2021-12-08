import * as dayjs from 'dayjs';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';

export interface IDroitsStrategiePchE {
  id?: number;
  isActif?: boolean;
  anne?: number;
  mois?: number;
  montantPlafond?: number;
  montantPlafondPlus?: number;
  nbHeurePlafond?: number;
  tauxCotisations?: number;
  dateOuverture?: dayjs.Dayjs;
  dateFermeture?: dayjs.Dayjs | null;
  beneficiaire?: IBeneficiaire | null;
}

export class DroitsStrategiePchE implements IDroitsStrategiePchE {
  constructor(
    public id?: number,
    public isActif?: boolean,
    public anne?: number,
    public mois?: number,
    public montantPlafond?: number,
    public montantPlafondPlus?: number,
    public nbHeurePlafond?: number,
    public tauxCotisations?: number,
    public dateOuverture?: dayjs.Dayjs,
    public dateFermeture?: dayjs.Dayjs | null,
    public beneficiaire?: IBeneficiaire | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getDroitsStrategiePchEIdentifier(droitsStrategiePchE: IDroitsStrategiePchE): number | undefined {
  return droitsStrategiePchE.id;
}
