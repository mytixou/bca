import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDroitsStrategiePch, DroitsStrategiePch } from '../droits-strategie-pch.model';
import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';

@Injectable({ providedIn: 'root' })
export class DroitsStrategiePchRoutingResolveService implements Resolve<IDroitsStrategiePch> {
  constructor(protected service: DroitsStrategiePchService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDroitsStrategiePch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((droitsStrategiePch: HttpResponse<DroitsStrategiePch>) => {
          if (droitsStrategiePch.body) {
            return of(droitsStrategiePch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DroitsStrategiePch());
  }
}
