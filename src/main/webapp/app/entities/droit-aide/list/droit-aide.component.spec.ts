import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DroitAideService } from '../service/droit-aide.service';

import { DroitAideComponent } from './droit-aide.component';

describe('DroitAide Management Component', () => {
  let comp: DroitAideComponent;
  let fixture: ComponentFixture<DroitAideComponent>;
  let service: DroitAideService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitAideComponent],
    })
      .overrideTemplate(DroitAideComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitAideComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DroitAideService);

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
    expect(comp.droitAides?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
