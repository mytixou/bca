import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStrategieCmgGed } from '../strategie-cmg-ged.model';
import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';

@Component({
  templateUrl: './strategie-cmg-ged-delete-dialog.component.html',
})
export class StrategieCmgGedDeleteDialogComponent {
  strategieCmgGed?: IStrategieCmgGed;

  constructor(protected strategieCmgGedService: StrategieCmgGedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.strategieCmgGedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
