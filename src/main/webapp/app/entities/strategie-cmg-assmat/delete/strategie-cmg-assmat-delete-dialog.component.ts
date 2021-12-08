import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStrategieCmgAssmat } from '../strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';

@Component({
  templateUrl: './strategie-cmg-assmat-delete-dialog.component.html',
})
export class StrategieCmgAssmatDeleteDialogComponent {
  strategieCmgAssmat?: IStrategieCmgAssmat;

  constructor(protected strategieCmgAssmatService: StrategieCmgAssmatService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.strategieCmgAssmatService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
