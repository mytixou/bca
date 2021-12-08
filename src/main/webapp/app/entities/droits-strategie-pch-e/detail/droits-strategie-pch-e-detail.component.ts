import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDroitsStrategiePchE } from '../droits-strategie-pch-e.model';

@Component({
  selector: 'jhi-droits-strategie-pch-e-detail',
  templateUrl: './droits-strategie-pch-e-detail.component.html',
})
export class DroitsStrategiePchEDetailComponent implements OnInit {
  droitsStrategiePchE: IDroitsStrategiePchE | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategiePchE }) => {
      this.droitsStrategiePchE = droitsStrategiePchE;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
