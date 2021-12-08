jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';
import { IDroitsStrategiePchE, DroitsStrategiePchE } from '../droits-strategie-pch-e.model';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

import { DroitsStrategiePchEUpdateComponent } from './droits-strategie-pch-e-update.component';

describe('DroitsStrategiePchE Management Update Component', () => {
  let comp: DroitsStrategiePchEUpdateComponent;
  let fixture: ComponentFixture<DroitsStrategiePchEUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let droitsStrategiePchEService: DroitsStrategiePchEService;
  let beneficiaireService: BeneficiaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategiePchEUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DroitsStrategiePchEUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategiePchEUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    droitsStrategiePchEService = TestBed.inject(DroitsStrategiePchEService);
    beneficiaireService = TestBed.inject(BeneficiaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Beneficiaire query and add missing value', () => {
      const droitsStrategiePchE: IDroitsStrategiePchE = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 87266 };
      droitsStrategiePchE.beneficiaire = beneficiaire;

      const beneficiaireCollection: IBeneficiaire[] = [{ id: 52829 }];
      jest.spyOn(beneficiaireService, 'query').mockReturnValue(of(new HttpResponse({ body: beneficiaireCollection })));
      const additionalBeneficiaires = [beneficiaire];
      const expectedCollection: IBeneficiaire[] = [...additionalBeneficiaires, ...beneficiaireCollection];
      jest.spyOn(beneficiaireService, 'addBeneficiaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droitsStrategiePchE });
      comp.ngOnInit();

      expect(beneficiaireService.query).toHaveBeenCalled();
      expect(beneficiaireService.addBeneficiaireToCollectionIfMissing).toHaveBeenCalledWith(
        beneficiaireCollection,
        ...additionalBeneficiaires
      );
      expect(comp.beneficiairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const droitsStrategiePchE: IDroitsStrategiePchE = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 14980 };
      droitsStrategiePchE.beneficiaire = beneficiaire;

      activatedRoute.data = of({ droitsStrategiePchE });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(droitsStrategiePchE));
      expect(comp.beneficiairesSharedCollection).toContain(beneficiaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategiePchE>>();
      const droitsStrategiePchE = { id: 123 };
      jest.spyOn(droitsStrategiePchEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategiePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategiePchE }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(droitsStrategiePchEService.update).toHaveBeenCalledWith(droitsStrategiePchE);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategiePchE>>();
      const droitsStrategiePchE = new DroitsStrategiePchE();
      jest.spyOn(droitsStrategiePchEService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategiePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategiePchE }));
      saveSubject.complete();

      // THEN
      expect(droitsStrategiePchEService.create).toHaveBeenCalledWith(droitsStrategiePchE);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategiePchE>>();
      const droitsStrategiePchE = { id: 123 };
      jest.spyOn(droitsStrategiePchEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategiePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(droitsStrategiePchEService.update).toHaveBeenCalledWith(droitsStrategiePchE);
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
