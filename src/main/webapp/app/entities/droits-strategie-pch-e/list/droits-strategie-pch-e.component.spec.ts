import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';

import { DroitsStrategiePchEComponent } from './droits-strategie-pch-e.component';

describe('DroitsStrategiePchE Management Component', () => {
  let comp: DroitsStrategiePchEComponent;
  let fixture: ComponentFixture<DroitsStrategiePchEComponent>;
  let service: DroitsStrategiePchEService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategiePchEComponent],
    })
      .overrideTemplate(DroitsStrategiePchEComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategiePchEComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DroitsStrategiePchEService);

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
    expect(comp.droitsStrategiePchES?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
