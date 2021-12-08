import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';

import { TrancheAideEnfantGedComponent } from './tranche-aide-enfant-ged.component';

describe('TrancheAideEnfantGed Management Component', () => {
  let comp: TrancheAideEnfantGedComponent;
  let fixture: ComponentFixture<TrancheAideEnfantGedComponent>;
  let service: TrancheAideEnfantGedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TrancheAideEnfantGedComponent],
    })
      .overrideTemplate(TrancheAideEnfantGedComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrancheAideEnfantGedComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TrancheAideEnfantGedService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.trancheAideEnfantGeds?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
