import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrancheAideEnfantAssmatComponent } from '../list/tranche-aide-enfant-assmat.component';
import { TrancheAideEnfantAssmatDetailComponent } from '../detail/tranche-aide-enfant-assmat-detail.component';
import { TrancheAideEnfantAssmatUpdateComponent } from '../update/tranche-aide-enfant-assmat-update.component';
import { TrancheAideEnfantAssmatRoutingResolveService } from './tranche-aide-enfant-assmat-routing-resolve.service';

const trancheAideEnfantAssmatRoute: Routes = [
  {
    path: '',
    component: TrancheAideEnfantAssmatComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrancheAideEnfantAssmatDetailComponent,
    resolve: {
      trancheAideEnfantAssmat: TrancheAideEnfantAssmatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrancheAideEnfantAssmatUpdateComponent,
    resolve: {
      trancheAideEnfantAssmat: TrancheAideEnfantAssmatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrancheAideEnfantAssmatUpdateComponent,
    resolve: {
      trancheAideEnfantAssmat: TrancheAideEnfantAssmatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trancheAideEnfantAssmatRoute)],
  exports: [RouterModule],
})
export class TrancheAideEnfantAssmatRoutingModule {}
