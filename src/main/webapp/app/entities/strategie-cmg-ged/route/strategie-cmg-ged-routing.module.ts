import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StrategieCmgGedComponent } from '../list/strategie-cmg-ged.component';
import { StrategieCmgGedDetailComponent } from '../detail/strategie-cmg-ged-detail.component';
import { StrategieCmgGedUpdateComponent } from '../update/strategie-cmg-ged-update.component';
import { StrategieCmgGedRoutingResolveService } from './strategie-cmg-ged-routing-resolve.service';

const strategieCmgGedRoute: Routes = [
  {
    path: '',
    component: StrategieCmgGedComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StrategieCmgGedDetailComponent,
    resolve: {
      strategieCmgGed: StrategieCmgGedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StrategieCmgGedUpdateComponent,
    resolve: {
      strategieCmgGed: StrategieCmgGedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StrategieCmgGedUpdateComponent,
    resolve: {
      strategieCmgGed: StrategieCmgGedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(strategieCmgGedRoute)],
  exports: [RouterModule],
})
export class StrategieCmgGedRoutingModule {}
