jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldePchService } from '../service/solde-pch.service';
import { ISoldePch, SoldePch } from '../solde-pch.model';
import { IDroitsStrategiePch } from 'app/entities/droits-strategie-pch/droits-strategie-pch.model';
import { DroitsStrategiePchService } from 'app/entities/droits-strategie-pch/service/droits-strategie-pch.service';

import { SoldePchUpdateComponent } from './solde-pch-update.component';

describe('SoldePch Management Update Component', () => {
  let comp: SoldePchUpdateComponent;
  let fixture: ComponentFixture<SoldePchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldePchService: SoldePchService;
  let droitsStrategiePchService: DroitsStrategiePchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldePchUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldePchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldePchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldePchService = TestBed.inject(SoldePchService);
    droitsStrategiePchService = TestBed.inject(DroitsStrategiePchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DroitsStrategiePch query and add missing value', () => {
      const soldePch: ISoldePch = { id: 456 };
      const droitsStrategiePch: IDroitsStrategiePch = { id: 62781 };
      soldePch.droitsStrategiePch = droitsStrategiePch;

      const droitsStrategiePchCollection: IDroitsStrategiePch[] = [{ id: 34349 }];
      jest.spyOn(droitsStrategiePchService, 'query').mockReturnValue(of(new HttpResponse({ body: droitsStrategiePchCollection })));
      const additionalDroitsStrategiePches = [droitsStrategiePch];
      const expectedCollection: IDroitsStrategiePch[] = [...additionalDroitsStrategiePches, ...droitsStrategiePchCollection];
      jest.spyOn(droitsStrategiePchService, 'addDroitsStrategiePchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldePch });
      comp.ngOnInit();

      expect(droitsStrategiePchService.query).toHaveBeenCalled();
      expect(droitsStrategiePchService.addDroitsStrategiePchToCollectionIfMissing).toHaveBeenCalledWith(
        droitsStrategiePchCollection,
        ...additionalDroitsStrategiePches
      );
      expect(comp.droitsStrategiePchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldePch: ISoldePch = { id: 456 };
      const droitsStrategiePch: IDroitsStrategiePch = { id: 13547 };
      soldePch.droitsStrategiePch = droitsStrategiePch;

      activatedRoute.data = of({ soldePch });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldePch));
      expect(comp.droitsStrategiePchesSharedCollection).toContain(droitsStrategiePch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldePch>>();
      const soldePch = { id: 123 };
      jest.spyOn(soldePchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldePch }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldePchService.update).toHaveBeenCalledWith(soldePch);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldePch>>();
      const soldePch = new SoldePch();
      jest.spyOn(soldePchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldePch }));
      saveSubject.complete();

      // THEN
      expect(soldePchService.create).toHaveBeenCalledWith(soldePch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldePch>>();
      const soldePch = { id: 123 };
      jest.spyOn(soldePchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldePchService.update).toHaveBeenCalledWith(soldePch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDroitsStrategiePchById', () => {
      it('Should return tracked DroitsStrategiePch primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDroitsStrategiePchById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
