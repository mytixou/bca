jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldePchEService } from '../service/solde-pch-e.service';
import { ISoldePchE, SoldePchE } from '../solde-pch-e.model';
import { IDroitsStrategiePchE } from 'app/entities/droits-strategie-pch-e/droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from 'app/entities/droits-strategie-pch-e/service/droits-strategie-pch-e.service';

import { SoldePchEUpdateComponent } from './solde-pch-e-update.component';

describe('SoldePchE Management Update Component', () => {
  let comp: SoldePchEUpdateComponent;
  let fixture: ComponentFixture<SoldePchEUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldePchEService: SoldePchEService;
  let droitsStrategiePchEService: DroitsStrategiePchEService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldePchEUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldePchEUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldePchEUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldePchEService = TestBed.inject(SoldePchEService);
    droitsStrategiePchEService = TestBed.inject(DroitsStrategiePchEService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DroitsStrategiePchE query and add missing value', () => {
      const soldePchE: ISoldePchE = { id: 456 };
      const droitsStrategiePchE: IDroitsStrategiePchE = { id: 48741 };
      soldePchE.droitsStrategiePchE = droitsStrategiePchE;

      const droitsStrategiePchECollection: IDroitsStrategiePchE[] = [{ id: 58956 }];
      jest.spyOn(droitsStrategiePchEService, 'query').mockReturnValue(of(new HttpResponse({ body: droitsStrategiePchECollection })));
      const additionalDroitsStrategiePchES = [droitsStrategiePchE];
      const expectedCollection: IDroitsStrategiePchE[] = [...additionalDroitsStrategiePchES, ...droitsStrategiePchECollection];
      jest.spyOn(droitsStrategiePchEService, 'addDroitsStrategiePchEToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldePchE });
      comp.ngOnInit();

      expect(droitsStrategiePchEService.query).toHaveBeenCalled();
      expect(droitsStrategiePchEService.addDroitsStrategiePchEToCollectionIfMissing).toHaveBeenCalledWith(
        droitsStrategiePchECollection,
        ...additionalDroitsStrategiePchES
      );
      expect(comp.droitsStrategiePchESSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldePchE: ISoldePchE = { id: 456 };
      const droitsStrategiePchE: IDroitsStrategiePchE = { id: 49370 };
      soldePchE.droitsStrategiePchE = droitsStrategiePchE;

      activatedRoute.data = of({ soldePchE });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldePchE));
      expect(comp.droitsStrategiePchESSharedCollection).toContain(droitsStrategiePchE);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldePchE>>();
      const soldePchE = { id: 123 };
      jest.spyOn(soldePchEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldePchE }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldePchEService.update).toHaveBeenCalledWith(soldePchE);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldePchE>>();
      const soldePchE = new SoldePchE();
      jest.spyOn(soldePchEService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldePchE }));
      saveSubject.complete();

      // THEN
      expect(soldePchEService.create).toHaveBeenCalledWith(soldePchE);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldePchE>>();
      const soldePchE = { id: 123 };
      jest.spyOn(soldePchEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldePchEService.update).toHaveBeenCalledWith(soldePchE);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDroitsStrategiePchEById', () => {
      it('Should return tracked DroitsStrategiePchE primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDroitsStrategiePchEById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
