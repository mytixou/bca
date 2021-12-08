import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStrategieCmgGed, StrategieCmgGed } from '../strategie-cmg-ged.model';
import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';

@Injectable({ providedIn: 'root' })
export class StrategieCmgGedRoutingResolveService implements Resolve<IStrategieCmgGed> {
  constructor(protected service: StrategieCmgGedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStrategieCmgGed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((strategieCmgGed: HttpResponse<StrategieCmgGed>) => {
          if (strategieCmgGed.body) {
            return of(strategieCmgGed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StrategieCmgGed());
  }
}
