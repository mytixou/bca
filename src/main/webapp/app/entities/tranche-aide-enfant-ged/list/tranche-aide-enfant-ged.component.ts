import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';
import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';
import { TrancheAideEnfantGedDeleteDialogComponent } from '../delete/tranche-aide-enfant-ged-delete-dialog.component';

@Component({
  selector: 'jhi-tranche-aide-enfant-ged',
  templateUrl: './tranche-aide-enfant-ged.component.html',
})
export class TrancheAideEnfantGedComponent implements OnInit {
  trancheAideEnfantGeds?: ITrancheAideEnfantGed[];
  isLoading = false;

  constructor(protected trancheAideEnfantGedService: TrancheAideEnfantGedService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.trancheAideEnfantGedService.query().subscribe(
      (res: HttpResponse<ITrancheAideEnfantGed[]>) => {
        this.isLoading = false;
        this.trancheAideEnfantGeds = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITrancheAideEnfantGed): number {
    return item.id!;
  }

  delete(trancheAideEnfantGed: ITrancheAideEnfantGed): void {
    const modalRef = this.modalService.open(TrancheAideEnfantGedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trancheAideEnfantGed = trancheAideEnfantGed;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
