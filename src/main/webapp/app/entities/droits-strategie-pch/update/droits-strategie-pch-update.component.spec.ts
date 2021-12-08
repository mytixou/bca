jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';
import { IDroitsStrategiePch, DroitsStrategiePch } from '../droits-strategie-pch.model';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

import { DroitsStrategiePchUpdateComponent } from './droits-strategie-pch-update.component';

describe('DroitsStrategiePch Management Update Component', () => {
  let comp: DroitsStrategiePchUpdateComponent;
  let fixture: ComponentFixture<DroitsStrategiePchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let droitsStrategiePchService: DroitsStrategiePchService;
  let beneficiaireService: BeneficiaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategiePchUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DroitsStrategiePchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategiePchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    droitsStrategiePchService = TestBed.inject(DroitsStrategiePchService);
    beneficiaireService = TestBed.inject(BeneficiaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Beneficiaire query and add missing value', () => {
      const droitsStrategiePch: IDroitsStrategiePch = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 96724 };
      droitsStrategiePch.beneficiaire = beneficiaire;

      const beneficiaireCollection: IBeneficiaire[] = [{ id: 23588 }];
      jest.spyOn(beneficiaireService, 'query').mockReturnValue(of(new HttpResponse({ body: beneficiaireCollection })));
      const additionalBeneficiaires = [beneficiaire];
      const expectedCollection: IBeneficiaire[] = [...additionalBeneficiaires, ...beneficiaireCollection];
      jest.spyOn(beneficiaireService, 'addBeneficiaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droitsStrategiePch });
      comp.ngOnInit();

      expect(beneficiaireService.query).toHaveBeenCalled();
      expect(beneficiaireService.addBeneficiaireToCollectionIfMissing).toHaveBeenCalledWith(
        beneficiaireCollection,
        ...additionalBeneficiaires
      );
      expect(comp.beneficiairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const droitsStrategiePch: IDroitsStrategiePch = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 94955 };
      droitsStrategiePch.beneficiaire = beneficiaire;

      activatedRoute.data = of({ droitsStrategiePch });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(droitsStrategiePch));
      expect(comp.beneficiairesSharedCollection).toContain(beneficiaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategiePch>>();
      const droitsStrategiePch = { id: 123 };
      jest.spyOn(droitsStrategiePchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategiePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategiePch }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(droitsStrategiePchService.update).toHaveBeenCalledWith(droitsStrategiePch);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategiePch>>();
      const droitsStrategiePch = new DroitsStrategiePch();
      jest.spyOn(droitsStrategiePchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategiePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategiePch }));
      saveSubject.complete();

      // THEN
      expect(droitsStrategiePchService.create).toHaveBeenCalledWith(droitsStrategiePch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategiePch>>();
      const droitsStrategiePch = { id: 123 };
      jest.spyOn(droitsStrategiePchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategiePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(droitsStrategiePchService.update).toHaveBeenCalledWith(droitsStrategiePch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBeneficiaireById', () => {
      it('Should return tracked Beneficiaire primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBeneficiaireById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
