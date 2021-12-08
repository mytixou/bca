import { IStrategieCi } from 'app/entities/strategie-ci/strategie-ci.model';
import { IStrategieApa } from 'app/entities/strategie-apa/strategie-apa.model';
import { IStrategiePch } from 'app/entities/strategie-pch/strategie-pch.model';
import { IStrategiePchE } from 'app/entities/strategie-pch-e/strategie-pch-e.model';

export interface INatureMontant {
  id?: number;
  code?: string;
  libelle?: string | null;
  description?: string | null;
  strategieCis?: IStrategieCi[] | null;
  strategieApas?: IStrategieApa[] | null;
  strategiePches?: IStrategiePch[] | null;
  strategiePchES?: IStrategiePchE[] | null;
}

export class NatureMontant implements INatureMontant {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string | null,
    public description?: string | null,
    public strategieCis?: IStrategieCi[] | null,
    public strategieApas?: IStrategieApa[] | null,
    public strategiePches?: IStrategiePch[] | null,
    public strategiePchES?: IStrategiePchE[] | null
  ) {}
}

export function getNatureMontantIdentifier(natureMontant: INatureMontant): number | undefined {
  return natureMontant.id;
}
