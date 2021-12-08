jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';

import { DroitsStrategiePchDeleteDialogComponent } from './droits-strategie-pch-delete-dialog.component';

describe('DroitsStrategiePch Management Delete Component', () => {
  let comp: DroitsStrategiePchDeleteDialogComponent;
  let fixture: ComponentFixture<DroitsStrategiePchDeleteDialogComponent>;
  let service: DroitsStrategiePchService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategiePchDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(DroitsStrategiePchDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DroitsStrategiePchDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DroitsStrategiePchService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
