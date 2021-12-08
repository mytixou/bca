import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DroitsStrategiePchComponent } from './list/droits-strategie-pch.component';
import { DroitsStrategiePchDetailComponent } from './detail/droits-strategie-pch-detail.component';
import { DroitsStrategiePchUpdateComponent } from './update/droits-strategie-pch-update.component';
import { DroitsStrategiePchDeleteDialogComponent } from './delete/droits-strategie-pch-delete-dialog.component';
import { DroitsStrategiePchRoutingModule } from './route/droits-strategie-pch-routing.module';

@NgModule({
  imports: [SharedModule, DroitsStrategiePchRoutingModule],
  declarations: [
    DroitsStrategiePchComponent,
    DroitsStrategiePchDetailComponent,
    DroitsStrategiePchUpdateComponent,
    DroitsStrategiePchDeleteDialogComponent,
  ],
  entryComponents: [DroitsStrategiePchDeleteDialogComponent],
})
export class DroitsStrategiePchModule {}
