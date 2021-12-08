import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStrategieCmgGed } from '../strategie-cmg-ged.model';

@Component({
  selector: 'jhi-strategie-cmg-ged-detail',
  templateUrl: './strategie-cmg-ged-detail.component.html',
})
export class StrategieCmgGedDetailComponent implements OnInit {
  strategieCmgGed: IStrategieCmgGed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strategieCmgGed }) => {
      this.strategieCmgGed = strategieCmgGed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
