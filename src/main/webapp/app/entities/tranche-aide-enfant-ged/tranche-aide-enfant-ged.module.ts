import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrancheAideEnfantGedComponent } from './list/tranche-aide-enfant-ged.component';
import { TrancheAideEnfantGedDetailComponent } from './detail/tranche-aide-enfant-ged-detail.component';
import { TrancheAideEnfantGedUpdateComponent } from './update/tranche-aide-enfant-ged-update.component';
import { TrancheAideEnfantGedDeleteDialogComponent } from './delete/tranche-aide-enfant-ged-delete-dialog.component';
import { TrancheAideEnfantGedRoutingModule } from './route/tranche-aide-enfant-ged-routing.module';

@NgModule({
  imports: [SharedModule, TrancheAideEnfantGedRoutingModule],
  declarations: [
    TrancheAideEnfantGedComponent,
    TrancheAideEnfantGedDetailComponent,
    TrancheAideEnfantGedUpdateComponent,
    TrancheAideEnfantGedDeleteDialogComponent,
  ],
  entryComponents: [TrancheAideEnfantGedDeleteDialogComponent],
})
export class TrancheAideEnfantGedModule {}
