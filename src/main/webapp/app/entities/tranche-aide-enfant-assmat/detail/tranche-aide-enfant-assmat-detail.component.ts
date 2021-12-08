import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';

@Component({
  selector: 'jhi-tranche-aide-enfant-assmat-detail',
  templateUrl: './tranche-aide-enfant-assmat-detail.component.html',
})
export class TrancheAideEnfantAssmatDetailComponent implements OnInit {
  trancheAideEnfantAssmat: ITrancheAideEnfantAssmat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trancheAideEnfantAssmat }) => {
      this.trancheAideEnfantAssmat = trancheAideEnfantAssmat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
