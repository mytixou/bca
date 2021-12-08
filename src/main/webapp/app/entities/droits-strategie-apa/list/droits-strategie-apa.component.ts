import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDroitsStrategieApa } from '../droits-strategie-apa.model';
import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';
import { DroitsStrategieApaDeleteDialogComponent } from '../delete/droits-strategie-apa-delete-dialog.component';

@Component({
  selector: 'jhi-droits-strategie-apa',
  templateUrl: './droits-strategie-apa.component.html',
})
export class DroitsStrategieApaComponent implements OnInit {
  droitsStrategieApas?: IDroitsStrategieApa[];
  isLoading = false;

  constructor(protected droitsStrategieApaService: DroitsStrategieApaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.droitsStrategieApaService.query().subscribe(
      (res: HttpResponse<IDroitsStrategieApa[]>) => {
        this.isLoading = false;
        this.droitsStrategieApas = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDroitsStrategieApa): number {
    return item.id!;
  }

  delete(droitsStrategieApa: IDroitsStrategieApa): void {
    const modalRef = this.modalService.open(DroitsStrategieApaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.droitsStrategieApa = droitsStrategieApa;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
