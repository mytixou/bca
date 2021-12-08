import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PecService } from '../service/pec.service';

import { PecComponent } from './pec.component';

describe('Pec Management Component', () => {
  let comp: PecComponent;
  let fixture: ComponentFixture<PecComponent>;
  let service: PecService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PecComponent],
    })
      .overrideTemplate(PecComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PecComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PecService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }],
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
    expect(comp.pecs?.[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
  });
});
