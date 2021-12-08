import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPec } from '../pec.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-pec-detail',
  templateUrl: './pec-detail.component.html',
})
export class PecDetailComponent implements OnInit {
  pec: IPec | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pec }) => {
      this.pec = pec;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
