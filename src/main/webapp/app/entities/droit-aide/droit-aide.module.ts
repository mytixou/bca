import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DroitAideComponent } from './list/droit-aide.component';
import { DroitAideDetailComponent } from './detail/droit-aide-detail.component';
import { DroitAideUpdateComponent } from './update/droit-aide-update.component';
import { DroitAideDeleteDialogComponent } from './delete/droit-aide-delete-dialog.component';
import { DroitAideRoutingModule } from './route/droit-aide-routing.module';

@NgModule({
  imports: [SharedModule, DroitAideRoutingModule],
  declarations: [DroitAideComponent, DroitAideDetailComponent, DroitAideUpdateComponent, DroitAideDeleteDialogComponent],
  entryComponents: [DroitAideDeleteDialogComponent],
})
export class DroitAideModule {}
