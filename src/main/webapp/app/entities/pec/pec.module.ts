import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PecComponent } from './list/pec.component';
import { PecDetailComponent } from './detail/pec-detail.component';
import { PecUpdateComponent } from './update/pec-update.component';
import { PecDeleteDialogComponent } from './delete/pec-delete-dialog.component';
import { PecRoutingModule } from './route/pec-routing.module';

@NgModule({
  imports: [SharedModule, PecRoutingModule],
  declarations: [PecComponent, PecDetailComponent, PecUpdateComponent, PecDeleteDialogComponent],
  entryComponents: [PecDeleteDialogComponent],
})
export class PecModule {}
