import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DroitsStrategiePchDetailComponent } from './droits-strategie-pch-detail.component';

describe('DroitsStrategiePch Management Detail Component', () => {
  let comp: DroitsStrategiePchDetailComponent;
  let fixture: ComponentFixture<DroitsStrategiePchDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DroitsStrategiePchDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ droitsStrategiePch: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DroitsStrategiePchDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DroitsStrategiePchDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load droitsStrategiePch on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.droitsStrategiePch).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
