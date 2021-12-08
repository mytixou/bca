import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StrategieCmgGedComponent } from './list/strategie-cmg-ged.component';
import { StrategieCmgGedDetailComponent } from './detail/strategie-cmg-ged-detail.component';
import { StrategieCmgGedUpdateComponent } from './update/strategie-cmg-ged-update.component';
import { StrategieCmgGedDeleteDialogComponent } from './delete/strategie-cmg-ged-delete-dialog.component';
import { StrategieCmgGedRoutingModule } from './route/strategie-cmg-ged-routing.module';

@NgModule({
  imports: [SharedModule, StrategieCmgGedRoutingModule],
  declarations: [
    StrategieCmgGedComponent,
    StrategieCmgGedDetailComponent,
    StrategieCmgGedUpdateComponent,
    StrategieCmgGedDeleteDialogComponent,
  ],
  entryComponents: [StrategieCmgGedDeleteDialogComponent],
})
export class StrategieCmgGedModule {}
