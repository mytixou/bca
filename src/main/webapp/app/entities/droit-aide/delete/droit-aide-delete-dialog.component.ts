import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitAide } from '../droit-aide.model';
import { DroitAideService } from '../service/droit-aide.service';

@Component({
  templateUrl: './droit-aide-delete-dialog.component.html',
})
export class DroitAideDeleteDialogComponent {
  droitAide?: IDroitAide;

  constructor(protected droitAideService: DroitAideService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.droitAideService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
