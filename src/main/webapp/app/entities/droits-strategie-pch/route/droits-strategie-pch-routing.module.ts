import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DroitsStrategiePchComponent } from '../list/droits-strategie-pch.component';
import { DroitsStrategiePchDetailComponent } from '../detail/droits-strategie-pch-detail.component';
import { DroitsStrategiePchUpdateComponent } from '../update/droits-strategie-pch-update.component';
import { DroitsStrategiePchRoutingResolveService } from './droits-strategie-pch-routing-resolve.service';

const droitsStrategiePchRoute: Routes = [
  {
    path: '',
    component: DroitsStrategiePchComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DroitsStrategiePchDetailComponent,
    resolve: {
      droitsStrategiePch: DroitsStrategiePchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DroitsStrategiePchUpdateComponent,
    resolve: {
      droitsStrategiePch: DroitsStrategiePchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DroitsStrategiePchUpdateComponent,
    resolve: {
      droitsStrategiePch: DroitsStrategiePchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(droitsStrategiePchRoute)],
  exports: [RouterModule],
})
export class DroitsStrategiePchRoutingModule {}
