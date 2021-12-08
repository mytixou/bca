import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDroitsStrategieCi, DroitsStrategieCi } from '../droits-strategie-ci.model';
import { DroitsStrategieCiService } from '../service/droits-strategie-ci.service';

@Injectable({ providedIn: 'root' })
export class DroitsStrategieCiRoutingResolveService implements Resolve<IDroitsStrategieCi> {
  constructor(protected service: DroitsStrategieCiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDroitsStrategieCi> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((droitsStrategieCi: HttpResponse<DroitsStrategieCi>) => {
          if (droitsStrategieCi.body) {
            return of(droitsStrategieCi.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DroitsStrategieCi());
  }
}
