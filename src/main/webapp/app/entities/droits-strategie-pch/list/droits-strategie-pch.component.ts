import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategiePch } from '../droits-strategie-pch.model';
import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';
import { DroitsStrategiePchDeleteDialogComponent } from '../delete/droits-strategie-pch-delete-dialog.component';

@Component({
  selector: 'jhi-droits-strategie-pch',
  templateUrl: './droits-strategie-pch.component.html',
})
export class DroitsStrategiePchComponent implements OnInit {
  droitsStrategiePches?: IDroitsStrategiePch[];
  isLoading = false;

  constructor(protected droitsStrategiePchService: DroitsStrategiePchService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.droitsStrategiePchService.query().subscribe(
      (res: HttpResponse<IDroitsStrategiePch[]>) => {
        this.isLoading = false;
        this.droitsStrategiePches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDroitsStrategiePch): number {
    return item.id!;
  }

  delete(droitsStrategiePch: IDroitsStrategiePch): void {
    const modalRef = this.modalService.open(DroitsStrategiePchDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.droitsStrategiePch = droitsStrategiePch;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
