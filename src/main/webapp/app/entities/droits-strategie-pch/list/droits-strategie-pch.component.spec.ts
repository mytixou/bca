import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';

import { DroitsStrategiePchComponent } from './droits-strategie-pch.component';

describe('DroitsStrategiePch Management Component', () => {
  let comp: DroitsStrategiePchComponent;
  let fixture: ComponentFixture<DroitsStrategiePchComponent>;
  let service: DroitsStrategiePchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategiePchComponent],
    })
      .overrideTemplate(DroitsStrategiePchComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategiePchComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DroitsStrategiePchService);

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
    expect(comp.droitsStrategiePches?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
