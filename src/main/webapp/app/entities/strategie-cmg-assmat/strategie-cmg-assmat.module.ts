import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StrategieCmgAssmatComponent } from './list/strategie-cmg-assmat.component';
import { StrategieCmgAssmatDetailComponent } from './detail/strategie-cmg-assmat-detail.component';
import { StrategieCmgAssmatUpdateComponent } from './update/strategie-cmg-assmat-update.component';
import { StrategieCmgAssmatDeleteDialogComponent } from './delete/strategie-cmg-assmat-delete-dialog.component';
import { StrategieCmgAssmatRoutingModule } from './route/strategie-cmg-assmat-routing.module';

@NgModule({
  imports: [SharedModule, StrategieCmgAssmatRoutingModule],
  declarations: [
    StrategieCmgAssmatComponent,
    StrategieCmgAssmatDetailComponent,
    StrategieCmgAssmatUpdateComponent,
    StrategieCmgAssmatDeleteDialogComponent,
  ],
  entryComponents: [StrategieCmgAssmatDeleteDialogComponent],
})
export class StrategieCmgAssmatModule {}
