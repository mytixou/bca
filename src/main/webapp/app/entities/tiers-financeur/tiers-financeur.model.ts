import * as dayjs from 'dayjs';

export interface ITiersFinanceur {
  id?: number;
  nom?: string;
  localisation?: string | null;
  isActif?: boolean;
  dateInscription?: dayjs.Dayjs;
  anneLancement?: number;
  moisLancement?: number;
  recupHeureActif?: boolean;
  dateResiliation?: dayjs.Dayjs | null;
  derniereAnnee?: number | null;
  dernierMois?: number | null;
}

export class TiersFinanceur implements ITiersFinanceur {
  constructor(
    public id?: number,
    public nom?: string,
    public localisation?: string | null,
    public isActif?: boolean,
    public dateInscription?: dayjs.Dayjs,
    public anneLancement?: number,
    public moisLancement?: number,
    public recupHeureActif?: boolean,
    public dateResiliation?: dayjs.Dayjs | null,
    public derniereAnnee?: number | null,
    public dernierMois?: number | null
  ) {
    this.isActif = this.isActif ?? false;
    this.recupHeureActif = this.recupHeureActif ?? false;
  }
}

export function getTiersFinanceurIdentifier(tiersFinanceur: ITiersFinanceur): number | undefined {
  return tiersFinanceur.id;
}
