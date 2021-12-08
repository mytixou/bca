import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitAide } from '../droit-aide.model';
import { DroitAideService } from '../service/droit-aide.service';
import { DroitAideDeleteDialogComponent } from '../delete/droit-aide-delete-dialog.component';

@Component({
  selector: 'jhi-droit-aide',
  templateUrl: './droit-aide.component.html',
})
export class DroitAideComponent implements OnInit {
  droitAides?: IDroitAide[];
  isLoading = false;

  constructor(protected droitAideService: DroitAideService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.droitAideService.query().subscribe(
      (res: HttpResponse<IDroitAide[]>) => {
        this.isLoading = false;
        this.droitAides = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDroitAide): number {
    return item.id!;
  }

  delete(droitAide: IDroitAide): void {
    const modalRef = this.modalService.open(DroitAideDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.droitAide = droitAide;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
