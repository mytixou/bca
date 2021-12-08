import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStrategieCmgAssmat } from '../strategie-cmg-assmat.model';

@Component({
  selector: 'jhi-strategie-cmg-assmat-detail',
  templateUrl: './strategie-cmg-assmat-detail.component.html',
})
export class StrategieCmgAssmatDetailComponent implements OnInit {
  strategieCmgAssmat: IStrategieCmgAssmat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strategieCmgAssmat }) => {
      this.strategieCmgAssmat = strategieCmgAssmat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
