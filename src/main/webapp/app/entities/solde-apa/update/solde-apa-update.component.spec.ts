jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldeApaService } from '../service/solde-apa.service';
import { ISoldeApa, SoldeApa } from '../solde-apa.model';
import { IDroitsStrategieApa } from 'app/entities/droits-strategie-apa/droits-strategie-apa.model';
import { DroitsStrategieApaService } from 'app/entities/droits-strategie-apa/service/droits-strategie-apa.service';

import { SoldeApaUpdateComponent } from './solde-apa-update.component';

describe('SoldeApa Management Update Component', () => {
  let comp: SoldeApaUpdateComponent;
  let fixture: ComponentFixture<SoldeApaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldeApaService: SoldeApaService;
  let droitsStrategieApaService: DroitsStrategieApaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeApaUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldeApaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldeApaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldeApaService = TestBed.inject(SoldeApaService);
    droitsStrategieApaService = TestBed.inject(DroitsStrategieApaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DroitsStrategieApa query and add missing value', () => {
      const soldeApa: ISoldeApa = { id: 456 };
      const droitsStrategieApa: IDroitsStrategieApa = { id: 56637 };
      soldeApa.droitsStrategieApa = droitsStrategieApa;

      const droitsStrategieApaCollection: IDroitsStrategieApa[] = [{ id: 80169 }];
      jest.spyOn(droitsStrategieApaService, 'query').mockReturnValue(of(new HttpResponse({ body: droitsStrategieApaCollection })));
      const additionalDroitsStrategieApas = [droitsStrategieApa];
      const expectedCollection: IDroitsStrategieApa[] = [...additionalDroitsStrategieApas, ...droitsStrategieApaCollection];
      jest.spyOn(droitsStrategieApaService, 'addDroitsStrategieApaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeApa });
      comp.ngOnInit();

      expect(droitsStrategieApaService.query).toHaveBeenCalled();
      expect(droitsStrategieApaService.addDroitsStrategieApaToCollectionIfMissing).toHaveBeenCalledWith(
        droitsStrategieApaCollection,
        ...additionalDroitsStrategieApas
      );
      expect(comp.droitsStrategieApasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldeApa: ISoldeApa = { id: 456 };
      const droitsStrategieApa: IDroitsStrategieApa = { id: 74534 };
      soldeApa.droitsStrategieApa = droitsStrategieApa;

      activatedRoute.data = of({ soldeApa });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldeApa));
      expect(comp.droitsStrategieApasSharedCollection).toContain(droitsStrategieApa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeApa>>();
      const soldeApa = { id: 123 };
      jest.spyOn(soldeApaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeApa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeApa }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldeApaService.update).toHaveBeenCalledWith(soldeApa);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeApa>>();
      const soldeApa = new SoldeApa();
      jest.spyOn(soldeApaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeApa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeApa }));
      saveSubject.complete();

      // THEN
      expect(soldeApaService.create).toHaveBeenCalledWith(soldeApa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeApa>>();
      const soldeApa = { id: 123 };
      jest.spyOn(soldeApaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeApa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldeApaService.update).toHaveBeenCalledWith(soldeApa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDroitsStrategieApaById', () => {
      it('Should return tracked DroitsStrategieApa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDroitsStrategieApaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
