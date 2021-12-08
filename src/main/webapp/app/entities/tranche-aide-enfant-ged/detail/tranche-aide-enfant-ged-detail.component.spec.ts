import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrancheAideEnfantGedDetailComponent } from './tranche-aide-enfant-ged-detail.component';

describe('TrancheAideEnfantGed Management Detail Component', () => {
  let comp: TrancheAideEnfantGedDetailComponent;
  let fixture: ComponentFixture<TrancheAideEnfantGedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrancheAideEnfantGedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trancheAideEnfantGed: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrancheAideEnfantGedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrancheAideEnfantGedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trancheAideEnfantGed on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trancheAideEnfantGed).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
