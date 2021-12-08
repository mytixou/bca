import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DroitsStrategieCiDetailComponent } from './droits-strategie-ci-detail.component';

describe('DroitsStrategieCi Management Detail Component', () => {
  let comp: DroitsStrategieCiDetailComponent;
  let fixture: ComponentFixture<DroitsStrategieCiDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DroitsStrategieCiDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ droitsStrategieCi: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DroitsStrategieCiDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DroitsStrategieCiDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load droitsStrategieCi on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.droitsStrategieCi).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
