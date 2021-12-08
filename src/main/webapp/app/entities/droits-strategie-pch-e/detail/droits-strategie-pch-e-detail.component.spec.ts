import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DroitsStrategiePchEDetailComponent } from './droits-strategie-pch-e-detail.component';

describe('DroitsStrategiePchE Management Detail Component', () => {
  let comp: DroitsStrategiePchEDetailComponent;
  let fixture: ComponentFixture<DroitsStrategiePchEDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DroitsStrategiePchEDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ droitsStrategiePchE: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DroitsStrategiePchEDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DroitsStrategiePchEDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load droitsStrategiePchE on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.droitsStrategiePchE).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
