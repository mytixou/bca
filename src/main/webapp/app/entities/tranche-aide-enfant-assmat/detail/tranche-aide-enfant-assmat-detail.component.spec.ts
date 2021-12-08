import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrancheAideEnfantAssmatDetailComponent } from './tranche-aide-enfant-assmat-detail.component';

describe('TrancheAideEnfantAssmat Management Detail Component', () => {
  let comp: TrancheAideEnfantAssmatDetailComponent;
  let fixture: ComponentFixture<TrancheAideEnfantAssmatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrancheAideEnfantAssmatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trancheAideEnfantAssmat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrancheAideEnfantAssmatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrancheAideEnfantAssmatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trancheAideEnfantAssmat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trancheAideEnfantAssmat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
