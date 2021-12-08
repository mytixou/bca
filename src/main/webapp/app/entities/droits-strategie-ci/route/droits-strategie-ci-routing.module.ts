import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DroitsStrategieCiComponent } from '../list/droits-strategie-ci.component';
import { DroitsStrategieCiDetailComponent } from '../detail/droits-strategie-ci-detail.component';
import { DroitsStrategieCiUpdateComponent } from '../update/droits-strategie-ci-update.component';
import { DroitsStrategieCiRoutingResolveService } from './droits-strategie-ci-routing-resolve.service';

const droitsStrategieCiRoute: Routes = [
  {
    path: '',
    component: DroitsStrategieCiComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DroitsStrategieCiDetailComponent,
    resolve: {
      droitsStrategieCi: DroitsStrategieCiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DroitsStrategieCiUpdateComponent,
    resolve: {
      droitsStrategieCi: DroitsStrategieCiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DroitsStrategieCiUpdateComponent,
    resolve: {
      droitsStrategieCi: DroitsStrategieCiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(droitsStrategieCiRoute)],
  exports: [RouterModule],
})
export class DroitsStrategieCiRoutingModule {}
