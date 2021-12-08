jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';
import { IDroitsStrategieApa, DroitsStrategieApa } from '../droits-strategie-apa.model';
import { IBeneficiaire } from 'app/entities/beneficiaire/beneficiaire.model';
import { BeneficiaireService } from 'app/entities/beneficiaire/service/beneficiaire.service';

import { DroitsStrategieApaUpdateComponent } from './droits-strategie-apa-update.component';

describe('DroitsStrategieApa Management Update Component', () => {
  let comp: DroitsStrategieApaUpdateComponent;
  let fixture: ComponentFixture<DroitsStrategieApaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let droitsStrategieApaService: DroitsStrategieApaService;
  let beneficiaireService: BeneficiaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitsStrategieApaUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DroitsStrategieApaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitsStrategieApaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    droitsStrategieApaService = TestBed.inject(DroitsStrategieApaService);
    beneficiaireService = TestBed.inject(BeneficiaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Beneficiaire query and add missing value', () => {
      const droitsStrategieApa: IDroitsStrategieApa = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 76326 };
      droitsStrategieApa.beneficiaire = beneficiaire;

      const beneficiaireCollection: IBeneficiaire[] = [{ id: 36485 }];
      jest.spyOn(beneficiaireService, 'query').mockReturnValue(of(new HttpResponse({ body: beneficiaireCollection })));
      const additionalBeneficiaires = [beneficiaire];
      const expectedCollection: IBeneficiaire[] = [...additionalBeneficiaires, ...beneficiaireCollection];
      jest.spyOn(beneficiaireService, 'addBeneficiaireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droitsStrategieApa });
      comp.ngOnInit();

      expect(beneficiaireService.query).toHaveBeenCalled();
      expect(beneficiaireService.addBeneficiaireToCollectionIfMissing).toHaveBeenCalledWith(
        beneficiaireCollection,
        ...additionalBeneficiaires
      );
      expect(comp.beneficiairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const droitsStrategieApa: IDroitsStrategieApa = { id: 456 };
      const beneficiaire: IBeneficiaire = { id: 12240 };
      droitsStrategieApa.beneficiaire = beneficiaire;

      activatedRoute.data = of({ droitsStrategieApa });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(droitsStrategieApa));
      expect(comp.beneficiairesSharedCollection).toContain(beneficiaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategieApa>>();
      const droitsStrategieApa = { id: 123 };
      jest.spyOn(droitsStrategieApaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategieApa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategieApa }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(droitsStrategieApaService.update).toHaveBeenCalledWith(droitsStrategieApa);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategieApa>>();
      const droitsStrategieApa = new DroitsStrategieApa();
      jest.spyOn(droitsStrategieApaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategieApa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitsStrategieApa }));
      saveSubject.complete();

      // THEN
      expect(droitsStrategieApaService.create).toHaveBeenCalledWith(droitsStrategieApa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitsStrategieApa>>();
      const droitsStrategieApa = { id: 123 };
      jest.spyOn(droitsStrategieApaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitsStrategieApa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(droitsStrategieApaService.update).toHaveBeenCalledWith(droitsStrategieApa);
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
