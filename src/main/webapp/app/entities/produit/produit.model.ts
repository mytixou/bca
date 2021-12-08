import * as dayjs from 'dayjs';
import { TypeProduit } from 'app/entities/enumerations/type-produit.model';

export interface IProduit {
  id?: number;
  nom?: TypeProduit;
  isActif?: boolean;
  dateLancement?: dayjs.Dayjs;
  anneLancement?: number;
  moisLancement?: number;
  dateResiliation?: dayjs.Dayjs | null;
  derniereAnnee?: number | null;
  dernierMois?: number | null;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public nom?: TypeProduit,
    public isActif?: boolean,
    public dateLancement?: dayjs.Dayjs,
    public anneLancement?: number,
    public moisLancement?: number,
    public dateResiliation?: dayjs.Dayjs | null,
    public derniereAnnee?: number | null,
    public dernierMois?: number | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getProduitIdentifier(produit: IProduit): number | undefined {
  return produit.id;
}
