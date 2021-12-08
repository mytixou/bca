import * as dayjs from 'dayjs';
import { IStrategieCmgAssmat } from 'app/entities/strategie-cmg-assmat/strategie-cmg-assmat.model';

export interface ITrancheAideEnfantAssmat {
  id?: number;
  anne?: number;
  mois?: number;
  ageEnfantRevoluSurPeriode?: number;
  montantPlafondSalaire?: number;
  isActif?: boolean;
  dateCreated?: dayjs.Dayjs;
  isUpdated?: boolean;
  dateModified?: dayjs.Dayjs | null;
  strategieCmgAssmat?: IStrategieCmgAssmat | null;
}

export class TrancheAideEnfantAssmat implements ITrancheAideEnfantAssmat {
  constructor(
    public id?: number,
    public anne?: number,
    public mois?: number,
    public ageEnfantRevoluSurPeriode?: number,
    public montantPlafondSalaire?: number,
    public isActif?: boolean,
    public dateCreated?: dayjs.Dayjs,
    public isUpdated?: boolean,
    public dateModified?: dayjs.Dayjs | null,
    public strategieCmgAssmat?: IStrategieCmgAssmat | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isUpdated = this.isUpdated ?? false;
  }
}

export function getTrancheAideEnfantAssmatIdentifier(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): number | undefined {
  return trancheAideEnfantAssmat.id;
}
