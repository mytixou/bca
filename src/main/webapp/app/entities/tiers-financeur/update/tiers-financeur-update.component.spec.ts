jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TiersFinanceurService } from '../service/tiers-financeur.service';
import { ITiersFinanceur, TiersFinanceur } from '../tiers-financeur.model';

import { TiersFinanceurUpdateComponent } from './tiers-financeur-update.component';

describe('TiersFinanceur Management Update Component', () => {
  let comp: TiersFinanceurUpdateComponent;
  let fixture: ComponentFixture<TiersFinanceurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tiersFinanceurService: TiersFinanceurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TiersFinanceurUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TiersFinanceurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TiersFinanceurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tiersFinanceurService = TestBed.inject(TiersFinanceurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tiersFinanceur: ITiersFinanceur = { id: 456 };

      activatedRoute.data = of({ tiersFinanceur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tiersFinanceur));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TiersFinanceur>>();
      const tiersFinanceur = { id: 123 };
      jest.spyOn(tiersFinanceurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tiersFinanceur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tiersFinanceur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tiersFinanceurService.update).toHaveBeenCalledWith(tiersFinanceur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TiersFinanceur>>();
      const tiersFinanceur = new TiersFinanceur();
      jest.spyOn(tiersFinanceurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tiersFinanceur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tiersFinanceur }));
      saveSubject.complete();

      // THEN
      expect(tiersFinanceurService.create).toHaveBeenCalledWith(tiersFinanceur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TiersFinanceur>>();
      const tiersFinanceur = { id: 123 };
      jest.spyOn(tiersFinanceurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tiersFinanceur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tiersFinanceurService.update).toHaveBeenCalledWith(tiersFinanceur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
