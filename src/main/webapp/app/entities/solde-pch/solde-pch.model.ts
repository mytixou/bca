import * as dayjs from 'dayjs';
import { IDroitsStrategiePch } from 'app/entities/droits-strategie-pch/droits-strategie-pch.model';
import { IPec } from 'app/entities/pec/pec.model';

export interface ISoldePch {
  id?: number;
  date?: dayjs.Dayjs;
  isActif?: boolean;
  isDernier?: boolean;
  annee?: number;
  mois?: number;
  consoMontantPchCotisations?: number;
  consoMontantPchSalaire?: number;
  soldeMontantPch?: number;
  consoHeurePch?: number;
  soldeHeurePch?: number;
  droitsStrategiePch?: IDroitsStrategiePch | null;
  pec?: IPec | null;
}

export class SoldePch implements ISoldePch {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public isActif?: boolean,
    public isDernier?: boolean,
    public annee?: number,
    public mois?: number,
    public consoMontantPchCotisations?: number,
    public consoMontantPchSalaire?: number,
    public soldeMontantPch?: number,
    public consoHeurePch?: number,
    public soldeHeurePch?: number,
    public droitsStrategiePch?: IDroitsStrategiePch | null,
    public pec?: IPec | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isDernier = this.isDernier ?? false;
  }
}

export function getSoldePchIdentifier(soldePch: ISoldePch): number | undefined {
  return soldePch.id;
}
