import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';
import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';
import { TrancheAideEnfantAssmatDeleteDialogComponent } from '../delete/tranche-aide-enfant-assmat-delete-dialog.component';

@Component({
  selector: 'jhi-tranche-aide-enfant-assmat',
  templateUrl: './tranche-aide-enfant-assmat.component.html',
})
export class TrancheAideEnfantAssmatComponent implements OnInit {
  trancheAideEnfantAssmats?: ITrancheAideEnfantAssmat[];
  isLoading = false;

  constructor(protected trancheAideEnfantAssmatService: TrancheAideEnfantAssmatService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.trancheAideEnfantAssmatService.query().subscribe(
      (res: HttpResponse<ITrancheAideEnfantAssmat[]>) => {
        this.isLoading = false;
        this.trancheAideEnfantAssmats = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITrancheAideEnfantAssmat): number {
    return item.id!;
  }

  delete(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): void {
    const modalRef = this.modalService.open(TrancheAideEnfantAssmatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trancheAideEnfantAssmat = trancheAideEnfantAssmat;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
