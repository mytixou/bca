import * as dayjs from 'dayjs';
import { IEnfant } from 'app/entities/enfant/enfant.model';

export interface IBeneficiaire {
  id?: number;
  externeId?: string;
  isActif?: boolean;
  dateDesactivation?: dayjs.Dayjs | null;
  isInscrit?: boolean;
  dateInscription?: dayjs.Dayjs;
  dateResiliation?: dayjs.Dayjs | null;
  enfants?: IEnfant[] | null;
}

export class Beneficiaire implements IBeneficiaire {
  constructor(
    public id?: number,
    public externeId?: string,
    public isActif?: boolean,
    public dateDesactivation?: dayjs.Dayjs | null,
    public isInscrit?: boolean,
    public dateInscription?: dayjs.Dayjs,
    public dateResiliation?: dayjs.Dayjs | null,
    public enfants?: IEnfant[] | null
  ) {
    this.isActif = this.isActif ?? false;
    this.isInscrit = this.isInscrit ?? false;
  }
}

export function getBeneficiaireIdentifier(beneficiaire: IBeneficiaire): number | undefined {
  return beneficiaire.id;
}
