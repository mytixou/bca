import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';

import { DroitsStrategieApaComponent } from './droits-strategie-apa.component';

describe('DroitsStrategieApa Management Component', () => {
  let comp: DroitsStrategieApaComponent;
  let fixture: ComponentFixture<DroitsStrategieApaComponent>;
  let service: DroitsStrategieApaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategieApaComponent],
    })
      .overrideTemplate(DroitsStrategieApaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategieApaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DroitsStrategieApaService);

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
    expect(comp.droitsStrategieApas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
