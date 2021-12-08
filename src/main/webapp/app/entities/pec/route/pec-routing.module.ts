import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PecComponent } from '../list/pec.component';
import { PecDetailComponent } from '../detail/pec-detail.component';
import { PecUpdateComponent } from '../update/pec-update.component';
import { PecRoutingResolveService } from './pec-routing-resolve.service';

const pecRoute: Routes = [
  {
    path: '',
    component: PecComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PecDetailComponent,
    resolve: {
      pec: PecRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PecUpdateComponent,
    resolve: {
      pec: PecRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PecUpdateComponent,
    resolve: {
      pec: PecRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pecRoute)],
  exports: [RouterModule],
})
export class PecRoutingModule {}
