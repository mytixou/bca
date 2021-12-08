jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';
import { IStrategieCmgAssmat, StrategieCmgAssmat } from '../strategie-cmg-assmat.model';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';

import { StrategieCmgAssmatUpdateComponent } from './strategie-cmg-assmat-update.component';

describe('StrategieCmgAssmat Management Update Component', () => {
  let comp: StrategieCmgAssmatUpdateComponent;
  let fixture: ComponentFixture<StrategieCmgAssmatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let strategieCmgAssmatService: StrategieCmgAssmatService;
  let aideService: AideService;
  let tiersFinanceurService: TiersFinanceurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StrategieCmgAssmatUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(StrategieCmgAssmatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StrategieCmgAssmatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    strategieCmgAssmatService = TestBed.inject(StrategieCmgAssmatService);
    aideService = TestBed.inject(AideService);
    tiersFinanceurService = TestBed.inject(TiersFinanceurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Aide query and add missing value', () => {
      const strategieCmgAssmat: IStrategieCmgAssmat = { id: 456 };
      const aide: IAide = { id: 49103 };
      strategieCmgAssmat.aide = aide;

      const aideCollection: IAide[] = [{ id: 34694 }];
      jest.spyOn(aideService, 'query').mockReturnValue(of(new HttpResponse({ body: aideCollection })));
      const additionalAides = [aide];
      const expectedCollection: IAide[] = [...additionalAides, ...aideCollection];
      jest.spyOn(aideService, 'addAideToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategieCmgAssmat });
      comp.ngOnInit();

      expect(aideService.query).toHaveBeenCalled();
      expect(aideService.addAideToCollectionIfMissing).toHaveBeenCalledWith(aideCollection, ...additionalAides);
      expect(comp.aidesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TiersFinanceur query and add missing value', () => {
      const strategieCmgAssmat: IStrategieCmgAssmat = { id: 456 };
      const tiersFinanceur: ITiersFinanceur = { id: 29542 };
      strategieCmgAssmat.tiersFinanceur = tiersFinanceur;

      const tiersFinanceurCollection: ITiersFinanceur[] = [{ id: 84670 }];
      jest.spyOn(tiersFinanceurService, 'query').mockReturnValue(of(new HttpResponse({ body: tiersFinanceurCollection })));
      const additionalTiersFinanceurs = [tiersFinanceur];
      const expectedCollection: ITiersFinanceur[] = [...additionalTiersFinanceurs, ...tiersFinanceurCollection];
      jest.spyOn(tiersFinanceurService, 'addTiersFinanceurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategieCmgAssmat });
      comp.ngOnInit();

      expect(tiersFinanceurService.query).toHaveBeenCalled();
      expect(tiersFinanceurService.addTiersFinanceurToCollectionIfMissing).toHaveBeenCalledWith(
        tiersFinanceurCollection,
        ...additionalTiersFinanceurs
      );
      expect(comp.tiersFinanceursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const strategieCmgAssmat: IStrategieCmgAssmat = { id: 456 };
      const aide: IAide = { id: 85418 };
      strategieCmgAssmat.aide = aide;
      const tiersFinanceur: ITiersFinanceur = { id: 55578 };
      strategieCmgAssmat.tiersFinanceur = tiersFinanceur;

      activatedRoute.data = of({ strategieCmgAssmat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(strategieCmgAssmat));
      expect(comp.aidesSharedCollection).toContain(aide);
      expect(comp.tiersFinanceursSharedCollection).toContain(tiersFinanceur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategieCmgAssmat>>();
      const strategieCmgAssmat = { id: 123 };
      jest.spyOn(strategieCmgAssmatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategieCmgAssmat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategieCmgAssmat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(strategieCmgAssmatService.update).toHaveBeenCalledWith(strategieCmgAssmat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategieCmgAssmat>>();
      const strategieCmgAssmat = new StrategieCmgAssmat();
      jest.spyOn(strategieCmgAssmatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategieCmgAssmat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategieCmgAssmat }));
      saveSubject.complete();

      // THEN
      expect(strategieCmgAssmatService.create).toHaveBeenCalledWith(strategieCmgAssmat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategieCmgAssmat>>();
      const strategieCmgAssmat = { id: 123 };
      jest.spyOn(strategieCmgAssmatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategieCmgAssmat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(strategieCmgAssmatService.update).toHaveBeenCalledWith(strategieCmgAssmat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAideById', () => {
      it('Should return tracked Aide primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAideById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTiersFinanceurById', () => {
      it('Should return tracked TiersFinanceur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTiersFinanceurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
