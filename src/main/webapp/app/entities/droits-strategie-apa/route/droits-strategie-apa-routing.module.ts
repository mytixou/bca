import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DroitsStrategieApaComponent } from '../list/droits-strategie-apa.component';
import { DroitsStrategieApaDetailComponent } from '../detail/droits-strategie-apa-detail.component';
import { DroitsStrategieApaUpdateComponent } from '../update/droits-strategie-apa-update.component';
import { DroitsStrategieApaRoutingResolveService } from './droits-strategie-apa-routing-resolve.service';

const droitsStrategieApaRoute: Routes = [
  {
    path: '',
    component: DroitsStrategieApaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DroitsStrategieApaDetailComponent,
    resolve: {
      droitsStrategieApa: DroitsStrategieApaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DroitsStrategieApaUpdateComponent,
    resolve: {
      droitsStrategieApa: DroitsStrategieApaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DroitsStrategieApaUpdateComponent,
    resolve: {
      droitsStrategieApa: DroitsStrategieApaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(droitsStrategieApaRoute)],
  exports: [RouterModule],
})
export class DroitsStrategieApaRoutingModule {}
