import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDroitsStrategieApa, DroitsStrategieApa } from '../droits-strategie-apa.model';
import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';

@Injectable({ providedIn: 'root' })
export class DroitsStrategieApaRoutingResolveService implements Resolve<IDroitsStrategieApa> {
  constructor(protected service: DroitsStrategieApaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDroitsStrategieApa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((droitsStrategieApa: HttpResponse<DroitsStrategieApa>) => {
          if (droitsStrategieApa.body) {
            return of(droitsStrategieApa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DroitsStrategieApa());
  }
}
