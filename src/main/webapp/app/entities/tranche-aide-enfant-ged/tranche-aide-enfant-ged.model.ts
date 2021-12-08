import * as dayjs from 'dayjs';
import { IStrategieCmgGed } from 'app/entities/strategie-cmg-ged/strategie-cmg-ged.model';

export interface ITrancheAideEnfantGed {
  id?: number;
  anne?: number;
  mois?: number;
  ageEnfantRevoluSurPeriode?: number;
  montantPlafondSalaire?: number;
  isActif?: boolean;
  dateCreated?: dayjs.Dayjs;
  isUpdated?: boolean;
  dateModified?: dayjs.Dayjs | null;
  strategieCmgGed?: IStrategieCmgGed | null;
}

export class TrancheAideEnfantGed implements ITrancheAideEnfantGed {
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
    public strategieCmgGed?: IStrategieCmgGed | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isUpdated = this.isUpdated ?? false;
  }
}

export function getTrancheAideEnfantGedIdentifier(trancheAideEnfantGed: ITrancheAideEnfantGed): number | undefined {
  return trancheAideEnfantGed.id;
}
