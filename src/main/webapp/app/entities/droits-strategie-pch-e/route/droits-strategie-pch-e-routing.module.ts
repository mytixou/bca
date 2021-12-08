import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DroitsStrategiePchEComponent } from '../list/droits-strategie-pch-e.component';
import { DroitsStrategiePchEDetailComponent } from '../detail/droits-strategie-pch-e-detail.component';
import { DroitsStrategiePchEUpdateComponent } from '../update/droits-strategie-pch-e-update.component';
import { DroitsStrategiePchERoutingResolveService } from './droits-strategie-pch-e-routing-resolve.service';

const droitsStrategiePchERoute: Routes = [
  {
    path: '',
    component: DroitsStrategiePchEComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DroitsStrategiePchEDetailComponent,
    resolve: {
      droitsStrategiePchE: DroitsStrategiePchERoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DroitsStrategiePchEUpdateComponent,
    resolve: {
      droitsStrategiePchE: DroitsStrategiePchERoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DroitsStrategiePchEUpdateComponent,
    resolve: {
      droitsStrategiePchE: DroitsStrategiePchERoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(droitsStrategiePchERoute)],
  exports: [RouterModule],
})
export class DroitsStrategiePchERoutingModule {}
