import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';

import { TrancheAideEnfantAssmatComponent } from './tranche-aide-enfant-assmat.component';

describe('TrancheAideEnfantAssmat Management Component', () => {
  let comp: TrancheAideEnfantAssmatComponent;
  let fixture: ComponentFixture<TrancheAideEnfantAssmatComponent>;
  let service: TrancheAideEnfantAssmatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TrancheAideEnfantAssmatComponent],
    })
      .overrideTemplate(TrancheAideEnfantAssmatComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrancheAideEnfantAssmatComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TrancheAideEnfantAssmatService);

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
    expect(comp.trancheAideEnfantAssmats?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
