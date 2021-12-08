import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPec } from '../pec.model';
import { PecService } from '../service/pec.service';

@Component({
  templateUrl: './pec-delete-dialog.component.html',
})
export class PecDeleteDialogComponent {
  pec?: IPec;

  constructor(protected pecService: PecService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.pecService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
