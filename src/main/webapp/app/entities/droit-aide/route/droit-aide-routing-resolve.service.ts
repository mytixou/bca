import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDroitAide, DroitAide } from '../droit-aide.model';
import { DroitAideService } from '../service/droit-aide.service';

@Injectable({ providedIn: 'root' })
export class DroitAideRoutingResolveService implements Resolve<IDroitAide> {
  constructor(protected service: DroitAideService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDroitAide> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((droitAide: HttpResponse<DroitAide>) => {
          if (droitAide.body) {
            return of(droitAide.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DroitAide());
  }
}
