import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPec, Pec } from '../pec.model';
import { PecService } from '../service/pec.service';

@Injectable({ providedIn: 'root' })
export class PecRoutingResolveService implements Resolve<IPec> {
  constructor(protected service: PecService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPec> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pec: HttpResponse<Pec>) => {
          if (pec.body) {
            return of(pec.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pec());
  }
}
