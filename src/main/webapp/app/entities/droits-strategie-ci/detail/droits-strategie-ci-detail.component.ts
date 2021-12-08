import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDroitsStrategieCi } from '../droits-strategie-ci.model';

@Component({
  selector: 'jhi-droits-strategie-ci-detail',
  templateUrl: './droits-strategie-ci-detail.component.html',
})
export class DroitsStrategieCiDetailComponent implements OnInit {
  droitsStrategieCi: IDroitsStrategieCi | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategieCi }) => {
      this.droitsStrategieCi = droitsStrategieCi;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
