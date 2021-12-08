import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategiePchE } from '../droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';
import { DroitsStrategiePchEDeleteDialogComponent } from '../delete/droits-strategie-pch-e-delete-dialog.component';

@Component({
  selector: 'jhi-droits-strategie-pch-e',
  templateUrl: './droits-strategie-pch-e.component.html',
})
export class DroitsStrategiePchEComponent implements OnInit {
  droitsStrategiePchES?: IDroitsStrategiePchE[];
  isLoading = false;

  constructor(protected droitsStrategiePchEService: DroitsStrategiePchEService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.droitsStrategiePchEService.query().subscribe(
      (res: HttpResponse<IDroitsStrategiePchE[]>) => {
        this.isLoading = false;
        this.droitsStrategiePchES = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDroitsStrategiePchE): number {
    return item.id!;
  }

  delete(droitsStrategiePchE: IDroitsStrategiePchE): void {
    const modalRef = this.modalService.open(DroitsStrategiePchEDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.droitsStrategiePchE = droitsStrategiePchE;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
