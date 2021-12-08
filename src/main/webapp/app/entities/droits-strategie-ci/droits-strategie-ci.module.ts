import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DroitsStrategieCiComponent } from './list/droits-strategie-ci.component';
import { DroitsStrategieCiDetailComponent } from './detail/droits-strategie-ci-detail.component';
import { DroitsStrategieCiUpdateComponent } from './update/droits-strategie-ci-update.component';
import { DroitsStrategieCiDeleteDialogComponent } from './delete/droits-strategie-ci-delete-dialog.component';
import { DroitsStrategieCiRoutingModule } from './route/droits-strategie-ci-routing.module';

@NgModule({
  imports: [SharedModule, DroitsStrategieCiRoutingModule],
  declarations: [
    DroitsStrategieCiComponent,
    DroitsStrategieCiDetailComponent,
    DroitsStrategieCiUpdateComponent,
    DroitsStrategieCiDeleteDialogComponent,
  ],
  entryComponents: [DroitsStrategieCiDeleteDialogComponent],
})
export class DroitsStrategieCiModule {}
