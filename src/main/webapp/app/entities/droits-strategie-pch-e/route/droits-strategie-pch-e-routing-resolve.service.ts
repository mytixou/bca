import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDroitsStrategiePchE, DroitsStrategiePchE } from '../droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';

@Injectable({ providedIn: 'root' })
export class DroitsStrategiePchERoutingResolveService implements Resolve<IDroitsStrategiePchE> {
  constructor(protected service: DroitsStrategiePchEService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDroitsStrategiePchE> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((droitsStrategiePchE: HttpResponse<DroitsStrategiePchE>) => {
          if (droitsStrategiePchE.body) {
            return of(droitsStrategiePchE.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DroitsStrategiePchE());
  }
}
