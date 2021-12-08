import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategiePch } from '../droits-strategie-pch.model';
import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';

@Component({
  templateUrl: './droits-strategie-pch-delete-dialog.component.html',
})
export class DroitsStrategiePchDeleteDialogComponent {
  droitsStrategiePch?: IDroitsStrategiePch;

  constructor(protected droitsStrategiePchService: DroitsStrategiePchService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.droitsStrategiePchService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
