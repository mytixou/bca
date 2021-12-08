import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DroitAideComponent } from '../list/droit-aide.component';
import { DroitAideDetailComponent } from '../detail/droit-aide-detail.component';
import { DroitAideUpdateComponent } from '../update/droit-aide-update.component';
import { DroitAideRoutingResolveService } from './droit-aide-routing-resolve.service';

const droitAideRoute: Routes = [
  {
    path: '',
    component: DroitAideComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DroitAideDetailComponent,
    resolve: {
      droitAide: DroitAideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DroitAideUpdateComponent,
    resolve: {
      droitAide: DroitAideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DroitAideUpdateComponent,
    resolve: {
      droitAide: DroitAideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(droitAideRoute)],
  exports: [RouterModule],
})
export class DroitAideRoutingModule {}
