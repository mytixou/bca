import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DroitsStrategieApaComponent } from './list/droits-strategie-apa.component';
import { DroitsStrategieApaDetailComponent } from './detail/droits-strategie-apa-detail.component';
import { DroitsStrategieApaUpdateComponent } from './update/droits-strategie-apa-update.component';
import { DroitsStrategieApaDeleteDialogComponent } from './delete/droits-strategie-apa-delete-dialog.component';
import { DroitsStrategieApaRoutingModule } from './route/droits-strategie-apa-routing.module';

@NgModule({
  imports: [SharedModule, DroitsStrategieApaRoutingModule],
  declarations: [
    DroitsStrategieApaComponent,
    DroitsStrategieApaDetailComponent,
    DroitsStrategieApaUpdateComponent,
    DroitsStrategieApaDeleteDialogComponent,
  ],
  entryComponents: [DroitsStrategieApaDeleteDialogComponent],
})
export class DroitsStrategieApaModule {}
