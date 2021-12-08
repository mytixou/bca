import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStrategieCmgAssmat } from '../strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';
import { StrategieCmgAssmatDeleteDialogComponent } from '../delete/strategie-cmg-assmat-delete-dialog.component';

@Component({
  selector: 'jhi-strategie-cmg-assmat',
  templateUrl: './strategie-cmg-assmat.component.html',
})
export class StrategieCmgAssmatComponent implements OnInit {
  strategieCmgAssmats?: IStrategieCmgAssmat[];
  isLoading = false;

  constructor(protected strategieCmgAssmatService: StrategieCmgAssmatService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.strategieCmgAssmatService.query().subscribe(
      (res: HttpResponse<IStrategieCmgAssmat[]>) => {
        this.isLoading = false;
        this.strategieCmgAssmats = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IStrategieCmgAssmat): number {
    return item.id!;
  }

  delete(strategieCmgAssmat: IStrategieCmgAssmat): void {
    const modalRef = this.modalService.open(StrategieCmgAssmatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.strategieCmgAssmat = strategieCmgAssmat;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
