import * as dayjs from 'dayjs';
import { IProduit } from 'app/entities/produit/produit.model';
import { IAide } from 'app/entities/aide/aide.model';

export interface IDroitAide {
  id?: number;
  isActif?: boolean;
  anne?: number;
  dateOuverture?: dayjs.Dayjs;
  dateFermeture?: dayjs.Dayjs | null;
  produit?: IProduit | null;
  produit?: IAide | null;
}

export class DroitAide implements IDroitAide {
  constructor(
    public id?: number,
    public isActif?: boolean,
    public anne?: number,
    public dateOuverture?: dayjs.Dayjs,
    public dateFermeture?: dayjs.Dayjs | null,
    public produit?: IProduit | null,
    public produit?: IAide | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getDroitAideIdentifier(droitAide: IDroitAide): number | undefined {
  return droitAide.id;
}
