import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';

import { StrategieCmgGedComponent } from './strategie-cmg-ged.component';

describe('StrategieCmgGed Management Component', () => {
  let comp: StrategieCmgGedComponent;
  let fixture: ComponentFixture<StrategieCmgGedComponent>;
  let service: StrategieCmgGedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StrategieCmgGedComponent],
    })
      .overrideTemplate(StrategieCmgGedComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StrategieCmgGedComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(StrategieCmgGedService);

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
    expect(comp.strategieCmgGeds?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
