import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrancheAideEnfantGed, TrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';
import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';

@Injectable({ providedIn: 'root' })
export class TrancheAideEnfantGedRoutingResolveService implements Resolve<ITrancheAideEnfantGed> {
  constructor(protected service: TrancheAideEnfantGedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrancheAideEnfantGed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trancheAideEnfantGed: HttpResponse<TrancheAideEnfantGed>) => {
          if (trancheAideEnfantGed.body) {
            return of(trancheAideEnfantGed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrancheAideEnfantGed());
  }
}
