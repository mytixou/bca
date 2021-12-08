import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDroitsStrategiePch } from '../droits-strategie-pch.model';

@Component({
  selector: 'jhi-droits-strategie-pch-detail',
  templateUrl: './droits-strategie-pch-detail.component.html',
})
export class DroitsStrategiePchDetailComponent implements OnInit {
  droitsStrategiePch: IDroitsStrategiePch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategiePch }) => {
      this.droitsStrategiePch = droitsStrategiePch;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
