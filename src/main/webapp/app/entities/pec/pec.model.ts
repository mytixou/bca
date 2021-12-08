import * as dayjs from 'dayjs';
import { ISoldeCi } from 'app/entities/solde-ci/solde-ci.model';
import { ISoldeApa } from 'app/entities/solde-apa/solde-apa.model';
import { ISoldePch } from 'app/entities/solde-pch/solde-pch.model';
import { ISoldePchE } from 'app/entities/solde-pch-e/solde-pch-e.model';
import { TypeProduit } from 'app/entities/enumerations/type-produit.model';

export interface IPec {
  id?: string;
  idProduit?: string;
  produit?: TypeProduit;
  isPlus?: boolean;
  dateCreated?: dayjs.Dayjs;
  isUpdated?: boolean;
  dateModified?: dayjs.Dayjs | null;
  isActif?: boolean;
  pecDetails?: string;
  soldeCi?: ISoldeCi | null;
  soldeApa?: ISoldeApa | null;
  soldePch?: ISoldePch | null;
  soldePchE?: ISoldePchE | null;
}

export class Pec implements IPec {
  constructor(
    public id?: string,
    public idProduit?: string,
    public produit?: TypeProduit,
    public isPlus?: boolean,
    public dateCreated?: dayjs.Dayjs,
    public isUpdated?: boolean,
    public dateModified?: dayjs.Dayjs | null,
    public isActif?: boolean,
    public pecDetails?: string,
    public soldeCi?: ISoldeCi | null,
    public soldeApa?: ISoldeApa | null,
    public soldePch?: ISoldePch | null,
    public soldePchE?: ISoldePchE | null
  ) {
    this.isPlus = this.isPlus ?? false;
    this.isUpdated = this.isUpdated ?? false;
    this.isActif = this.isActif ?? false;
  }
}

export function getPecIdentifier(pec: IPec): string | undefined {
  return pec.id;
}
