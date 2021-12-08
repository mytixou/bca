import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StrategieCmgGedDetailComponent } from './strategie-cmg-ged-detail.component';

describe('StrategieCmgGed Management Detail Component', () => {
  let comp: StrategieCmgGedDetailComponent;
  let fixture: ComponentFixture<StrategieCmgGedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StrategieCmgGedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ strategieCmgGed: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StrategieCmgGedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StrategieCmgGedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load strategieCmgGed on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.strategieCmgGed).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
