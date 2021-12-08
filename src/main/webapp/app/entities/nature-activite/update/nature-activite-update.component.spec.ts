jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureActiviteService } from '../service/nature-activite.service';
import { INatureActivite, NatureActivite } from '../nature-activite.model';

import { NatureActiviteUpdateComponent } from './nature-activite-update.component';

describe('NatureActivite Management Update Component', () => {
  let comp: NatureActiviteUpdateComponent;
  let fixture: ComponentFixture<NatureActiviteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureActiviteService: NatureActiviteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureActiviteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureActiviteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureActiviteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureActiviteService = TestBed.inject(NatureActiviteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureActivite: INatureActivite = { id: 456 };

      activatedRoute.data = of({ natureActivite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureActivite));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureActivite>>();
      const natureActivite = { id: 123 };
      jest.spyOn(natureActiviteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureActivite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureActivite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureActiviteService.update).toHaveBeenCalledWith(natureActivite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureActivite>>();
      const natureActivite = new NatureActivite();
      jest.spyOn(natureActiviteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureActivite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureActivite }));
      saveSubject.complete();

      // THEN
      expect(natureActiviteService.create).toHaveBeenCalledWith(natureActivite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureActivite>>();
      const natureActivite = { id: 123 };
      jest.spyOn(natureActiviteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureActivite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureActiviteService.update).toHaveBeenCalledWith(natureActivite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
