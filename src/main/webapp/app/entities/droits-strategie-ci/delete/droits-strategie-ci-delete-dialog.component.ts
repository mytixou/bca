import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategieCi } from '../droits-strategie-ci.model';
import { DroitsStrategieCiService } from '../service/droits-strategie-ci.service';

@Component({
  templateUrl: './droits-strategie-ci-delete-dialog.component.html',
})
export class DroitsStrategieCiDeleteDialogComponent {
  droitsStrategieCi?: IDroitsStrategieCi;

  constructor(protected droitsStrategieCiService: DroitsStrategieCiService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.droitsStrategieCiService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
