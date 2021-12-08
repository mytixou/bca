import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnfant } from '../enfant.model';
import { EnfantService } from '../service/enfant.service';
import { EnfantDeleteDialogComponent } from '../delete/enfant-delete-dialog.component';

@Component({
  selector: 'jhi-enfant',
  templateUrl: './enfant.component.html',
})
export class EnfantComponent implements OnInit {
  enfants?: IEnfant[];
  isLoading = false;

  constructor(protected enfantService: EnfantService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.enfantService.query().subscribe(
      (res: HttpResponse<IEnfant[]>) => {
        this.isLoading = false;
        this.enfants = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEnfant): number {
    return item.id!;
  }

  delete(enfant: IEnfant): void {
    const modalRef = this.modalService.open(EnfantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.enfant = enfant;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
