import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DroitAideDetailComponent } from './droit-aide-detail.component';

describe('DroitAide Management Detail Component', () => {
  let comp: DroitAideDetailComponent;
  let fixture: ComponentFixture<DroitAideDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DroitAideDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ droitAide: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DroitAideDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DroitAideDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load droitAide on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.droitAide).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
