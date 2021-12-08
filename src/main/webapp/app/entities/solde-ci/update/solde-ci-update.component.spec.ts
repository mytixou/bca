jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldeCiService } from '../service/solde-ci.service';
import { ISoldeCi, SoldeCi } from '../solde-ci.model';
import { IDroitsStrategieCi } from 'app/entities/droits-strategie-ci/droits-strategie-ci.model';
import { DroitsStrategieCiService } from 'app/entities/droits-strategie-ci/service/droits-strategie-ci.service';

import { SoldeCiUpdateComponent } from './solde-ci-update.component';

describe('SoldeCi Management Update Component', () => {
  let comp: SoldeCiUpdateComponent;
  let fixture: ComponentFixture<SoldeCiUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldeCiService: SoldeCiService;
  let droitsStrategieCiService: DroitsStrategieCiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeCiUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldeCiUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldeCiUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldeCiService = TestBed.inject(SoldeCiService);
    droitsStrategieCiService = TestBed.inject(DroitsStrategieCiService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DroitsStrategieCi query and add missing value', () => {
      const soldeCi: ISoldeCi = { id: 456 };
      const droitsStrategieCi: IDroitsStrategieCi = { id: 87744 };
      soldeCi.droitsStrategieCi = droitsStrategieCi;

      const droitsStrategieCiCollection: IDroitsStrategieCi[] = [{ id: 48244 }];
      jest.spyOn(droitsStrategieCiService, 'query').mockReturnValue(of(new HttpResponse({ body: droitsStrategieCiCollection })));
      const additionalDroitsStrategieCis = [droitsStrategieCi];
      const expectedCollection: IDroitsStrategieCi[] = [...additionalDroitsStrategieCis, ...droitsStrategieCiCollection];
      jest.spyOn(droitsStrategieCiService, 'addDroitsStrategieCiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeCi });
      comp.ngOnInit();

      expect(droitsStrategieCiService.query).toHaveBeenCalled();
      expect(droitsStrategieCiService.addDroitsStrategieCiToCollectionIfMissing).toHaveBeenCalledWith(
        droitsStrategieCiCollection,
        ...additionalDroitsStrategieCis
      );
      expect(comp.droitsStrategieCisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldeCi: ISoldeCi = { id: 456 };
      const droitsStrategieCi: IDroitsStrategieCi = { id: 9809 };
      soldeCi.droitsStrategieCi = droitsStrategieCi;

      activatedRoute.data = of({ soldeCi });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldeCi));
      expect(comp.droitsStrategieCisSharedCollection).toContain(droitsStrategieCi);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeCi>>();
      const soldeCi = { id: 123 };
      jest.spyOn(soldeCiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeCi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeCi }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldeCiService.update).toHaveBeenCalledWith(soldeCi);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeCi>>();
      const soldeCi = new SoldeCi();
      jest.spyOn(soldeCiService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeCi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeCi }));
      saveSubject.complete();

      // THEN
      expect(soldeCiService.create).toHaveBeenCalledWith(soldeCi);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeCi>>();
      const soldeCi = { id: 123 };
      jest.spyOn(soldeCiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeCi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldeCiService.update).toHaveBeenCalledWith(soldeCi);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDroitsStrategieCiById', () => {
      it('Should return tracked DroitsStrategieCi primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDroitsStrategieCiById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
