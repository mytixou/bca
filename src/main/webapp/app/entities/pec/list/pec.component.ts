import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPec } from '../pec.model';
import { PecService } from '../service/pec.service';
import { PecDeleteDialogComponent } from '../delete/pec-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-pec',
  templateUrl: './pec.component.html',
})
export class PecComponent implements OnInit {
  pecs?: IPec[];
  isLoading = false;

  constructor(protected pecService: PecService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pecService.query().subscribe(
      (res: HttpResponse<IPec[]>) => {
        this.isLoading = false;
        this.pecs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPec): string {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(pec: IPec): void {
    const modalRef = this.modalService.open(PecDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pec = pec;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
