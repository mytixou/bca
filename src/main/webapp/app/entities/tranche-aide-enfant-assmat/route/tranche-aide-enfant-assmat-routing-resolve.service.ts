import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrancheAideEnfantAssmat, TrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';
import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';

@Injectable({ providedIn: 'root' })
export class TrancheAideEnfantAssmatRoutingResolveService implements Resolve<ITrancheAideEnfantAssmat> {
  constructor(protected service: TrancheAideEnfantAssmatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrancheAideEnfantAssmat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trancheAideEnfantAssmat: HttpResponse<TrancheAideEnfantAssmat>) => {
          if (trancheAideEnfantAssmat.body) {
            return of(trancheAideEnfantAssmat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrancheAideEnfantAssmat());
  }
}
