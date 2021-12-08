import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StrategieCmgAssmatDetailComponent } from './strategie-cmg-assmat-detail.component';

describe('StrategieCmgAssmat Management Detail Component', () => {
  let comp: StrategieCmgAssmatDetailComponent;
  let fixture: ComponentFixture<StrategieCmgAssmatDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StrategieCmgAssmatDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ strategieCmgAssmat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StrategieCmgAssmatDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StrategieCmgAssmatDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load strategieCmgAssmat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.strategieCmgAssmat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
