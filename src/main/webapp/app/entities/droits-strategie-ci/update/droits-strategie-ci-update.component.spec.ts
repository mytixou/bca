jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DroitsStrategieCiService } from '../service/droits-strategie-ci.service';
import { IDroitsStrategieCi, DroitsStrategieCi } from '../droits-strategie-ci.model';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

import { DroitsStrategieCiUpdateComponent } from './droits-strategie-ci-update.component';

describe('DroitsStrategieCi Management Update Component', () => {
  let comp: DroitsStrategieCiUpdateComponent;
  let fixture: ComponentFixture<DroitsStrategieCiUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let droitsStrategieCiService: DroitsStrategieCiService;
  let beneficiaireService: BeneficiaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategieCiUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DroitsStrategieCiUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategieCiUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    droitsStrategieCiService = TestBed.inject(DroitsStrategieCiService);
    beneficiaireService = TestBed.inject(BeneficiaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Beneficiaire query and add missing value', () => {
      const droitsStrategieCi: IDroitsStrategieCi = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 72008 };
      droitsStrategieCi.beneficiaire = beneficiaire;

      const beneficiaireCollection: IBeneficiaire[] = [{ id: 63652 }];
      jest.spyOn(beneficiaireService, 'query').mockReturnValue(of(new HttpResponse({ body: beneficiaireCollection })));
      const additionalBeneficiaires = [beneficiaire];
      const expectedCollection: IBeneficiaire[] = [...additionalBeneficiaires, ...beneficiaireCollection];
      jest.spyOn(beneficiaireService, 'addBeneficiaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droitsStrategieCi });
      comp.ngOnInit();

      expect(beneficiaireService.query).toHaveBeenCalled();
      expect(beneficiaireService.addBeneficiaireToCollectionIfMissing).toHaveBeenCalledWith(
        beneficiaireCollection,
        ...additionalBeneficiaires
      );
      expect(comp.beneficiairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const droitsStrategieCi: IDroitsStrategieCi = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 68433 };
      droitsStrategieCi.beneficiaire = beneficiaire;

      activatedRoute.data = of({ droitsStrategieCi });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(droitsStrategieCi));
      expect(comp.beneficiairesSharedCollection).toContain(beneficiaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategieCi>>();
      const droitsStrategieCi = { id: 123 };
      jest.spyOn(droitsStrategieCiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategieCi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategieCi }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(droitsStrategieCiService.update).toHaveBeenCalledWith(droitsStrategieCi);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategieCi>>();
      const droitsStrategieCi = new DroitsStrategieCi();
      jest.spyOn(droitsStrategieCiService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategieCi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategieCi }));
      saveSubject.complete();

      // THEN
      expect(droitsStrategieCiService.create).toHaveBeenCalledWith(droitsStrategieCi);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategieCi>>();
      const droitsStrategieCi = { id: 123 };
      jest.spyOn(droitsStrategieCiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategieCi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(droitsStrategieCiService.update).toHaveBeenCalledWith(droitsStrategieCi);
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
