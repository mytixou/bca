import * as dayjs from 'dayjs';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';

export interface IDroitsStrategieApa {
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

export class DroitsStrategieApa implements IDroitsStrategieApa {
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

export function getDroitsStrategieApaIdentifier(droitsStrategieApa: IDroitsStrategieApa): number | undefined {
  return droitsStrategieApa.id;
}
