import * as dayjs from 'dayjs';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TypeAide } from 'app/entities/enumerations/type-aide.model';

export interface IAide {
  id?: number;
  nom?: TypeAide;
  priorite?: number;
  isActif?: boolean;
  dateLancement?: dayjs.Dayjs;
  anneLancement?: number;
  moisLancement?: number;
  dateArret?: dayjs.Dayjs | null;
  derniereAnnee?: number | null;
  dernierMois?: number | null;
  aide?: ITiersFinanceur | null;
}

export class Aide implements IAide {
  constructor(
    public id?: number,
    public nom?: TypeAide,
    public priorite?: number,
    public isActif?: boolean,
    public dateLancement?: dayjs.Dayjs,
    public anneLancement?: number,
    public moisLancement?: number,
    public dateArret?: dayjs.Dayjs | null,
    public derniereAnnee?: number | null,
    public dernierMois?: number | null,
    public aide?: ITiersFinanceur | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getAideIdentifier(aide: IAide): number | undefined {
  return aide.id;
}
