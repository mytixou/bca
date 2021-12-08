import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategieCi } from '../droits-strategie-ci.model';
import { DroitsStrategieCiService } from '../service/droits-strategie-ci.service';
import { DroitsStrategieCiDeleteDialogComponent } from '../delete/droits-strategie-ci-delete-dialog.component';

@Component({
  selector: 'jhi-droits-strategie-ci',
  templateUrl: './droits-strategie-ci.component.html',
})
export class DroitsStrategieCiComponent implements OnInit {
  droitsStrategieCis?: IDroitsStrategieCi[];
  isLoading = false;

  constructor(protected droitsStrategieCiService: DroitsStrategieCiService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.droitsStrategieCiService.query().subscribe(
      (res: HttpResponse<IDroitsStrategieCi[]>) => {
        this.isLoading = false;
        this.droitsStrategieCis = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDroitsStrategieCi): number {
    return item.id!;
  }

  delete(droitsStrategieCi: IDroitsStrategieCi): void {
    const modalRef = this.modalService.open(DroitsStrategieCiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.droitsStrategieCi = droitsStrategieCi;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
