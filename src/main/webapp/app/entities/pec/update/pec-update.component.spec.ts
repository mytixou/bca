jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PecService } from '../service/pec.service';
import { IPec, Pec } from '../pec.model';
import { ISoldeCi } from 'app/entities/solde-ci/solde-ci.model';
import { SoldeCiService } from 'app/entities/solde-ci/service/solde-ci.service';
import { ISoldeApa } from 'app/entities/solde-apa/solde-apa.model';
import { SoldeApaService } from 'app/entities/solde-apa/service/solde-apa.service';
import { ISoldePch } from 'app/entities/solde-pch/solde-pch.model';
import { SoldePchService } from 'app/entities/solde-pch/service/solde-pch.service';
import { ISoldePchE } from 'app/entities/solde-pch-e/solde-pch-e.model';
import { SoldePchEService } from 'app/entities/solde-pch-e/service/solde-pch-e.service';

import { PecUpdateComponent } from './pec-update.component';

describe('Pec Management Update Component', () => {
  let comp: PecUpdateComponent;
  let fixture: ComponentFixture<PecUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pecService: PecService;
  let soldeCiService: SoldeCiService;
  let soldeApaService: SoldeApaService;
  let soldePchService: SoldePchService;
  let soldePchEService: SoldePchEService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PecUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PecUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PecUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pecService = TestBed.inject(PecService);
    soldeCiService = TestBed.inject(SoldeCiService);
    soldeApaService = TestBed.inject(SoldeApaService);
    soldePchService = TestBed.inject(SoldePchService);
    soldePchEService = TestBed.inject(SoldePchEService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call soldeCi query and add missing value', () => {
      const pec: IPec = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const soldeCi: ISoldeCi = { id: 13468 };
      pec.soldeCi = soldeCi;

      const soldeCiCollection: ISoldeCi[] = [{ id: 91620 }];
      jest.spyOn(soldeCiService, 'query').mockReturnValue(of(new HttpResponse({ body: soldeCiCollection })));
      const expectedCollection: ISoldeCi[] = [soldeCi, ...soldeCiCollection];
      jest.spyOn(soldeCiService, 'addSoldeCiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      expect(soldeCiService.query).toHaveBeenCalled();
      expect(soldeCiService.addSoldeCiToCollectionIfMissing).toHaveBeenCalledWith(soldeCiCollection, soldeCi);
      expect(comp.soldeCisCollection).toEqual(expectedCollection);
    });

    it('Should call soldeApa query and add missing value', () => {
      const pec: IPec = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const soldeApa: ISoldeApa = { id: 52178 };
      pec.soldeApa = soldeApa;

      const soldeApaCollection: ISoldeApa[] = [{ id: 52256 }];
      jest.spyOn(soldeApaService, 'query').mockReturnValue(of(new HttpResponse({ body: soldeApaCollection })));
      const expectedCollection: ISoldeApa[] = [soldeApa, ...soldeApaCollection];
      jest.spyOn(soldeApaService, 'addSoldeApaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      expect(soldeApaService.query).toHaveBeenCalled();
      expect(soldeApaService.addSoldeApaToCollectionIfMissing).toHaveBeenCalledWith(soldeApaCollection, soldeApa);
      expect(comp.soldeApasCollection).toEqual(expectedCollection);
    });

    it('Should call soldePch query and add missing value', () => {
      const pec: IPec = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const soldePch: ISoldePch = { id: 44017 };
      pec.soldePch = soldePch;

      const soldePchCollection: ISoldePch[] = [{ id: 3025 }];
      jest.spyOn(soldePchService, 'query').mockReturnValue(of(new HttpResponse({ body: soldePchCollection })));
      const expectedCollection: ISoldePch[] = [soldePch, ...soldePchCollection];
      jest.spyOn(soldePchService, 'addSoldePchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      expect(soldePchService.query).toHaveBeenCalled();
      expect(soldePchService.addSoldePchToCollectionIfMissing).toHaveBeenCalledWith(soldePchCollection, soldePch);
      expect(comp.soldePchesCollection).toEqual(expectedCollection);
    });

    it('Should call soldePchE query and add missing value', () => {
      const pec: IPec = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const soldePchE: ISoldePchE = { id: 79020 };
      pec.soldePchE = soldePchE;

      const soldePchECollection: ISoldePchE[] = [{ id: 15203 }];
      jest.spyOn(soldePchEService, 'query').mockReturnValue(of(new HttpResponse({ body: soldePchECollection })));
      const expectedCollection: ISoldePchE[] = [soldePchE, ...soldePchECollection];
      jest.spyOn(soldePchEService, 'addSoldePchEToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      expect(soldePchEService.query).toHaveBeenCalled();
      expect(soldePchEService.addSoldePchEToCollectionIfMissing).toHaveBeenCalledWith(soldePchECollection, soldePchE);
      expect(comp.soldePchESCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pec: IPec = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const soldeCi: ISoldeCi = { id: 64164 };
      pec.soldeCi = soldeCi;
      const soldeApa: ISoldeApa = { id: 34397 };
      pec.soldeApa = soldeApa;
      const soldePch: ISoldePch = { id: 62331 };
      pec.soldePch = soldePch;
      const soldePchE: ISoldePchE = { id: 17022 };
      pec.soldePchE = soldePchE;

      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pec));
      expect(comp.soldeCisCollection).toContain(soldeCi);
      expect(comp.soldeApasCollection).toContain(soldeApa);
      expect(comp.soldePchesCollection).toContain(soldePch);
      expect(comp.soldePchESCollection).toContain(soldePchE);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pec>>();
      const pec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(pecService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pec }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pecService.update).toHaveBeenCalledWith(pec);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pec>>();
      const pec = new Pec();
      jest.spyOn(pecService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pec }));
      saveSubject.complete();

      // THEN
      expect(pecService.create).toHaveBeenCalledWith(pec);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pec>>();
      const pec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(pecService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pec });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pecService.update).toHaveBeenCalledWith(pec);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSoldeCiById', () => {
      it('Should return tracked SoldeCi primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSoldeCiById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSoldeApaById', () => {
      it('Should return tracked SoldeApa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSoldeApaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSoldePchById', () => {
      it('Should return tracked SoldePch primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSoldePchById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSoldePchEById', () => {
      it('Should return tracked SoldePchE primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSoldePchEById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
