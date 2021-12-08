import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StrategieCmgAssmatComponent } from '../list/strategie-cmg-assmat.component';
import { StrategieCmgAssmatDetailComponent } from '../detail/strategie-cmg-assmat-detail.component';
import { StrategieCmgAssmatUpdateComponent } from '../update/strategie-cmg-assmat-update.component';
import { StrategieCmgAssmatRoutingResolveService } from './strategie-cmg-assmat-routing-resolve.service';

const strategieCmgAssmatRoute: Routes = [
  {
    path: '',
    component: StrategieCmgAssmatComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StrategieCmgAssmatDetailComponent,
    resolve: {
      strategieCmgAssmat: StrategieCmgAssmatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StrategieCmgAssmatUpdateComponent,
    resolve: {
      strategieCmgAssmat: StrategieCmgAssmatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StrategieCmgAssmatUpdateComponent,
    resolve: {
      strategieCmgAssmat: StrategieCmgAssmatRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(strategieCmgAssmatRoute)],
  exports: [RouterModule],
})
export class StrategieCmgAssmatRoutingModule {}
