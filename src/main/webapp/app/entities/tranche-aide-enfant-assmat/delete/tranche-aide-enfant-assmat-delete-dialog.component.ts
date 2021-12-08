import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';
import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';

@Component({
  templateUrl: './tranche-aide-enfant-assmat-delete-dialog.component.html',
})
export class TrancheAideEnfantAssmatDeleteDialogComponent {
  trancheAideEnfantAssmat?: ITrancheAideEnfantAssmat;

  constructor(protected trancheAideEnfantAssmatService: TrancheAideEnfantAssmatService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trancheAideEnfantAssmatService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
