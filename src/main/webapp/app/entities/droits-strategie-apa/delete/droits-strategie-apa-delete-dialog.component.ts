import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategieApa } from '../droits-strategie-apa.model';
import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';

@Component({
  templateUrl: './droits-strategie-apa-delete-dialog.component.html',
})
export class DroitsStrategieApaDeleteDialogComponent {
  droitsStrategieApa?: IDroitsStrategieApa;

  constructor(protected droitsStrategieApaService: DroitsStrategieApaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.droitsStrategieApaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
