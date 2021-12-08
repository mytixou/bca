import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';

import { StrategieCmgAssmatComponent } from './strategie-cmg-assmat.component';

describe('StrategieCmgAssmat Management Component', () => {
  let comp: StrategieCmgAssmatComponent;
  let fixture: ComponentFixture<StrategieCmgAssmatComponent>;
  let service: StrategieCmgAssmatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StrategieCmgAssmatComponent],
    })
      .overrideTemplate(StrategieCmgAssmatComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StrategieCmgAssmatComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(StrategieCmgAssmatService);

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
    expect(comp.strategieCmgAssmats?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
