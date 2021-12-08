import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategiePchE } from '../droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';

@Component({
  templateUrl: './droits-strategie-pch-e-delete-dialog.component.html',
})
export class DroitsStrategiePchEDeleteDialogComponent {
  droitsStrategiePchE?: IDroitsStrategiePchE;

  constructor(protected droitsStrategiePchEService: DroitsStrategiePchEService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.droitsStrategiePchEService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
