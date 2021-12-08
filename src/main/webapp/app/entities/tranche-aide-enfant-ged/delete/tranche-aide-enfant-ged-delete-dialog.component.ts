import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';
import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';

@Component({
  templateUrl: './tranche-aide-enfant-ged-delete-dialog.component.html',
})
export class TrancheAideEnfantGedDeleteDialogComponent {
  trancheAideEnfantGed?: ITrancheAideEnfantGed;

  constructor(protected trancheAideEnfantGedService: TrancheAideEnfantGedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trancheAideEnfantGedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
