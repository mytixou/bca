import * as dayjs from 'dayjs';
import { IDroitsStrategiePchE } from 'app/entities/droits-strategie-pch-e/droits-strategie-pch-e.model';
import { IPec } from 'app/entities/pec/pec.model';

export interface ISoldePchE {
  id?: number;
  date?: dayjs.Dayjs;
  isActif?: boolean;
  isDernier?: boolean;
  annee?: number;
  mois?: number;
  consoMontantPchECotisations?: number;
  consoMontantPchESalaire?: number;
  soldeMontantPchE?: number;
  consoHeurePchE?: number;
  soldeHeurePchE?: number;
  droitsStrategiePchE?: IDroitsStrategiePchE | null;
  pec?: IPec | null;
}

export class SoldePchE implements ISoldePchE {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public isActif?: boolean,
    public isDernier?: boolean,
    public annee?: number,
    public mois?: number,
    public consoMontantPchECotisations?: number,
    public consoMontantPchESalaire?: number,
    public soldeMontantPchE?: number,
    public consoHeurePchE?: number,
    public soldeHeurePchE?: number,
    public droitsStrategiePchE?: IDroitsStrategiePchE | null,
    public pec?: IPec | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isDernier = this.isDernier ?? false;
  }
}

export function getSoldePchEIdentifier(soldePchE: ISoldePchE): number | undefined {
  return soldePchE.id;
}
