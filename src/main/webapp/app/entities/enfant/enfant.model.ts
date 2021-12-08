import * as dayjs from 'dayjs';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';

export interface IEnfant {
  id?: number;
  isActif?: boolean;
  dateNaissance?: dayjs.Dayjs;
  dateInscription?: dayjs.Dayjs;
  dateResiliation?: dayjs.Dayjs | null;
  beneficiaire?: IBeneficiaire | null;
}

export class Enfant implements IEnfant {
  constructor(
    public id?: number,
    public isActif?: boolean,
    public dateNaissance?: dayjs.Dayjs,
    public dateInscription?: dayjs.Dayjs,
    public dateResiliation?: dayjs.Dayjs | null,
    public beneficiaire?: IBeneficiaire | null
  ) {
    this.isActif = this.isActif ?? false;
  }
}

export function getEnfantIdentifier(enfant: IEnfant): number | undefined {
  return enfant.id;
}
