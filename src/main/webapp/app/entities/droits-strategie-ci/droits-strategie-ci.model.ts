import * as dayjs from 'dayjs';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';

export interface IDroitsStrategieCi {
  id?: number;
  isActif?: boolean;
  anne?: number;
  montantPlafondDefaut?: number;
  montantPlafondHandicape?: number;
  montantPlafondDefautPlus?: number;
  montantPlafondHandicapePlus?: number;
  tauxSalaire?: number;
  dateOuverture?: dayjs.Dayjs;
  dateFermeture?: dayjs.Dayjs | null;
  beneficiaire?: IBeneficiaire | null;
}

export class DroitsStrategieCi implements IDroitsStrategieCi {
  constructor(
    public id?: number,
    public isActif?: boolean,
    public anne?: number,
    public montantPlafondDefaut?: number,
    public montantPlafondHandicape?: number,
    public montantPlafondDefautPlus?: number,
    public montantPlafondHandicapePlus?: number,
    public tauxSalaire?: number,
    public dateOuverture?: dayjs.Dayjs,
    public dateFermeture?: dayjs.Dayjs | null,
    public beneficiaire?: IBeneficiaire | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getDroitsStrategieCiIdentifier(droitsStrategieCi: IDroitsStrategieCi): number | undefined {
  return droitsStrategieCi.id;
}
