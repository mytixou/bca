import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrancheAideEnfantGedComponent } from '../list/tranche-aide-enfant-ged.component';
import { TrancheAideEnfantGedDetailComponent } from '../detail/tranche-aide-enfant-ged-detail.component';
import { TrancheAideEnfantGedUpdateComponent } from '../update/tranche-aide-enfant-ged-update.component';
import { TrancheAideEnfantGedRoutingResolveService } from './tranche-aide-enfant-ged-routing-resolve.service';

const trancheAideEnfantGedRoute: Routes = [
  {
    path: '',
    component: TrancheAideEnfantGedComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrancheAideEnfantGedDetailComponent,
    resolve: {
      trancheAideEnfantGed: TrancheAideEnfantGedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrancheAideEnfantGedUpdateComponent,
    resolve: {
      trancheAideEnfantGed: TrancheAideEnfantGedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrancheAideEnfantGedUpdateComponent,
    resolve: {
      trancheAideEnfantGed: TrancheAideEnfantGedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trancheAideEnfantGedRoute)],
  exports: [RouterModule],
})
export class TrancheAideEnfantGedRoutingModule {}
