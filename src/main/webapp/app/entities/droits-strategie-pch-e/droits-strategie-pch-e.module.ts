import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DroitsStrategiePchEComponent } from './list/droits-strategie-pch-e.component';
import { DroitsStrategiePchEDetailComponent } from './detail/droits-strategie-pch-e-detail.component';
import { DroitsStrategiePchEUpdateComponent } from './update/droits-strategie-pch-e-update.component';
import { DroitsStrategiePchEDeleteDialogComponent } from './delete/droits-strategie-pch-e-delete-dialog.component';
import { DroitsStrategiePchERoutingModule } from './route/droits-strategie-pch-e-routing.module';

@NgModule({
  imports: [SharedModule, DroitsStrategiePchERoutingModule],
  declarations: [
    DroitsStrategiePchEComponent,
    DroitsStrategiePchEDetailComponent,
    DroitsStrategiePchEUpdateComponent,
    DroitsStrategiePchEDeleteDialogComponent,
  ],
  entryComponents: [DroitsStrategiePchEDeleteDialogComponent],
})
export class DroitsStrategiePchEModule {}
