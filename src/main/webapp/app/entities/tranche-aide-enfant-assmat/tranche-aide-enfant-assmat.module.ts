import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrancheAideEnfantAssmatComponent } from './list/tranche-aide-enfant-assmat.component';
import { TrancheAideEnfantAssmatDetailComponent } from './detail/tranche-aide-enfant-assmat-detail.component';
import { TrancheAideEnfantAssmatUpdateComponent } from './update/tranche-aide-enfant-assmat-update.component';
import { TrancheAideEnfantAssmatDeleteDialogComponent } from './delete/tranche-aide-enfant-assmat-delete-dialog.component';
import { TrancheAideEnfantAssmatRoutingModule } from './route/tranche-aide-enfant-assmat-routing.module';

@NgModule({
  imports: [SharedModule, TrancheAideEnfantAssmatRoutingModule],
  declarations: [
    TrancheAideEnfantAssmatComponent,
    TrancheAideEnfantAssmatDetailComponent,
    TrancheAideEnfantAssmatUpdateComponent,
    TrancheAideEnfantAssmatDeleteDialogComponent,
  ],
  entryComponents: [TrancheAideEnfantAssmatDeleteDialogComponent],
})
export class TrancheAideEnfantAssmatModule {}
