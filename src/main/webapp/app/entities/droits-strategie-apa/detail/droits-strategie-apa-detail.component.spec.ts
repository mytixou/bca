import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DroitsStrategieApaDetailComponent } from './droits-strategie-apa-detail.component';

describe('DroitsStrategieApa Management Detail Component', () => {
  let comp: DroitsStrategieApaDetailComponent;
  let fixture: ComponentFixture<DroitsStrategieApaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DroitsStrategieApaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ droitsStrategieApa: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DroitsStrategieApaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DroitsStrategieApaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load droitsStrategieApa on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.droitsStrategieApa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
