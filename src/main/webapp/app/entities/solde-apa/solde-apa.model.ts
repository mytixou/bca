import * as dayjs from 'dayjs';
import { IDroitsStrategieApa } from 'app/entities/droits-strategie-apa/droits-strategie-apa.model';
import { IPec } from 'app/entities/pec/pec.model';

export interface ISoldeApa {
  id?: number;
  date?: dayjs.Dayjs;
  isActif?: boolean;
  isDernier?: boolean;
  annee?: number;
  mois?: number;
  consoMontantApaCotisations?: number;
  consoMontantApaSalaire?: number;
  soldeMontantApa?: number;
  consoHeureApa?: number;
  soldeHeureApa?: number;
  droitsStrategieApa?: IDroitsStrategieApa | null;
  pec?: IPec | null;
}

export class SoldeApa implements ISoldeApa {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public isActif?: boolean,
    public isDernier?: boolean,
    public annee?: number,
    public mois?: number,
    public consoMontantApaCotisations?: number,
    public consoMontantApaSalaire?: number,
    public soldeMontantApa?: number,
    public consoHeureApa?: number,
    public soldeHeureApa?: number,
    public droitsStrategieApa?: IDroitsStrategieApa | null,
    public pec?: IPec | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isDernier = this.isDernier ?? false;
  }
}

export function getSoldeApaIdentifier(soldeApa: ISoldeApa): number | undefined {
  return soldeApa.id;
}
