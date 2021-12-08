import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDroitAide } from '../droit-aide.model';

@Component({
  selector: 'jhi-droit-aide-detail',
  templateUrl: './droit-aide-detail.component.html',
})
export class DroitAideDetailComponent implements OnInit {
  droitAide: IDroitAide | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitAide }) => {
      this.droitAide = droitAide;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
