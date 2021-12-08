import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DroitsStrategieCiService } from '../service/droits-strategie-ci.service';

import { DroitsStrategieCiComponent } from './droits-strategie-ci.component';

describe('DroitsStrategieCi Management Component', () => {
  let comp: DroitsStrategieCiComponent;
  let fixture: ComponentFixture<DroitsStrategieCiComponent>;
  let service: DroitsStrategieCiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategieCiComponent],
    })
      .overrideTemplate(DroitsStrategieCiComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategieCiComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DroitsStrategieCiService);

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
    expect(comp.droitsStrategieCis?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
