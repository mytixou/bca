import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStrategieCmgAssmat, StrategieCmgAssmat } from '../strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';

@Injectable({ providedIn: 'root' })
export class StrategieCmgAssmatRoutingResolveService implements Resolve<IStrategieCmgAssmat> {
  constructor(protected service: StrategieCmgAssmatService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStrategieCmgAssmat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((strategieCmgAssmat: HttpResponse<StrategieCmgAssmat>) => {
          if (strategieCmgAssmat.body) {
            return of(strategieCmgAssmat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StrategieCmgAssmat());
  }
}
