import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStrategieCmgGed } from '../strategie-cmg-ged.model';
import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';
import { StrategieCmgGedDeleteDialogComponent } from '../delete/strategie-cmg-ged-delete-dialog.component';

@Component({
  selector: 'jhi-strategie-cmg-ged',
  templateUrl: './strategie-cmg-ged.component.html',
})
export class StrategieCmgGedComponent implements OnInit {
  strategieCmgGeds?: IStrategieCmgGed[];
  isLoading = false;

  constructor(protected strategieCmgGedService: StrategieCmgGedService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.strategieCmgGedService.query().subscribe(
      (res: HttpResponse<IStrategieCmgGed[]>) => {
        this.isLoading = false;
        this.strategieCmgGeds = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IStrategieCmgGed): number {
    return item.id!;
  }

  delete(strategieCmgGed: IStrategieCmgGed): void {
    const modalRef = this.modalService.open(StrategieCmgGedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.strategieCmgGed = strategieCmgGed;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
