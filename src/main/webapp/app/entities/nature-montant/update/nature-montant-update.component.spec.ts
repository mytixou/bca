jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureMontantService } from '../service/nature-montant.service';
import { INatureMontant, NatureMontant } from '../nature-montant.model';

import { NatureMontantUpdateComponent } from './nature-montant-update.component';

describe('NatureMontant Management Update Component', () => {
  let comp: NatureMontantUpdateComponent;
  let fixture: ComponentFixture<NatureMontantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureMontantService: NatureMontantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureMontantUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureMontantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureMontantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureMontantService = TestBed.inject(NatureMontantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureMontant: INatureMontant = { id: 456 };

      activatedRoute.data = of({ natureMontant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureMontant));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureMontant>>();
      const natureMontant = { id: 123 };
      jest.spyOn(natureMontantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureMontant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureMontant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureMontantService.update).toHaveBeenCalledWith(natureMontant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureMontant>>();
      const natureMontant = new NatureMontant();
      jest.spyOn(natureMontantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureMontant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureMontant }));
      saveSubject.complete();

      // THEN
      expect(natureMontantService.create).toHaveBeenCalledWith(natureMontant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureMontant>>();
      const natureMontant = { id: 123 };
      jest.spyOn(natureMontantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureMontant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureMontantService.update).toHaveBeenCalledWith(natureMontant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
