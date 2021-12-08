import * as dayjs from 'dayjs';
import { IDroitsStrategieCi } from 'app/entities/droits-strategie-ci/droits-strategie-ci.model';
import { IPec } from 'app/entities/pec/pec.model';

export interface ISoldeCi {
  id?: number;
  date?: dayjs.Dayjs;
  isActif?: boolean;
  isDernier?: boolean;
  annee?: number;
  consoMontantCi?: number;
  consoCiRec?: number;
  soldeMontantCi?: number;
  soldeMontantCiRec?: number;
  droitsStrategieCi?: IDroitsStrategieCi | null;
  pec?: IPec | null;
}

export class SoldeCi implements ISoldeCi {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public isActif?: boolean,
    public isDernier?: boolean,
    public annee?: number,
    public consoMontantCi?: number,
    public consoCiRec?: number,
    public soldeMontantCi?: number,
    public soldeMontantCiRec?: number,
    public droitsStrategieCi?: IDroitsStrategieCi | null,
    public pec?: IPec | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isDernier = this.isDernier ?? false;
  }
}

export function getSoldeCiIdentifier(soldeCi: ISoldeCi): number | undefined {
  return soldeCi.id;
}
