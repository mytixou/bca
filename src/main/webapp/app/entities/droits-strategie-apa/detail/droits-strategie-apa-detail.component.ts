import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDroitsStrategieApa } from '../droits-strategie-apa.model';

@Component({
  selector: 'jhi-droits-strategie-apa-detail',
  templateUrl: './droits-strategie-apa-detail.component.html',
})
export class DroitsStrategieApaDetailComponent implements OnInit {
  droitsStrategieApa: IDroitsStrategieApa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ droitsStrategieApa }) => {
      this.droitsStrategieApa = droitsStrategieApa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
