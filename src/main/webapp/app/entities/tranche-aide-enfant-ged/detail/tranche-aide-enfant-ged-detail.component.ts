import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';

@Component({
  selector: 'jhi-tranche-aide-enfant-ged-detail',
  templateUrl: './tranche-aide-enfant-ged-detail.component.html',
})
export class TrancheAideEnfantGedDetailComponent implements OnInit {
  trancheAideEnfantGed: ITrancheAideEnfantGed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trancheAideEnfantGed }) => {
      this.trancheAideEnfantGed = trancheAideEnfantGed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
