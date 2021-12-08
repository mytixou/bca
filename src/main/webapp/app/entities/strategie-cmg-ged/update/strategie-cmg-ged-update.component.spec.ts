jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';
import { IStrategieCmgGed, StrategieCmgGed } from '../strategie-cmg-ged.model';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';

import { StrategieCmgGedUpdateComponent } from './strategie-cmg-ged-update.component';

describe('StrategieCmgGed Management Update Component', () => {
  let comp: StrategieCmgGedUpdateComponent;
  let fixture: ComponentFixture<StrategieCmgGedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let strategieCmgGedService: StrategieCmgGedService;
  let aideService: AideService;
  let tiersFinanceurService: TiersFinanceurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StrategieCmgGedUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(StrategieCmgGedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StrategieCmgGedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    strategieCmgGedService = TestBed.inject(StrategieCmgGedService);
    aideService = TestBed.inject(AideService);
    tiersFinanceurService = TestBed.inject(TiersFinanceurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Aide query and add missing value', () => {
      const strategieCmgGed: IStrategieCmgGed = { id: 456 };
      const aide: IAide = { id: 76424 };
      strategieCmgGed.aide = aide;

      const aideCollection: IAide[] = [{ id: 29674 }];
      jest.spyOn(aideService, 'query').mockReturnValue(of(new HttpResponse({ body: aideCollection })));
      const additionalAides = [aide];
      const expectedCollection: IAide[] = [...additionalAides, ...aideCollection];
      jest.spyOn(aideService, 'addAideToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategieCmgGed });
      comp.ngOnInit();

      expect(aideService.query).toHaveBeenCalled();
      expect(aideService.addAideToCollectionIfMissing).toHaveBeenCalledWith(aideCollection, ...additionalAides);
      expect(comp.aidesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TiersFinanceur query and add missing value', () => {
      const strategieCmgGed: IStrategieCmgGed = { id: 456 };
      const tiersFinanceur: ITiersFinanceur = { id: 37033 };
      strategieCmgGed.tiersFinanceur = tiersFinanceur;

      const tiersFinanceurCollection: ITiersFinanceur[] = [{ id: 36294 }];
      jest.spyOn(tiersFinanceurService, 'query').mockReturnValue(of(new HttpResponse({ body: tiersFinanceurCollection })));
      const additionalTiersFinanceurs = [tiersFinanceur];
      const expectedCollection: ITiersFinanceur[] = [...additionalTiersFinanceurs, ...tiersFinanceurCollection];
      jest.spyOn(tiersFinanceurService, 'addTiersFinanceurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategieCmgGed });
      comp.ngOnInit();

      expect(tiersFinanceurService.query).toHaveBeenCalled();
      expect(tiersFinanceurService.addTiersFinanceurToCollectionIfMissing).toHaveBeenCalledWith(
        tiersFinanceurCollection,
        ...additionalTiersFinanceurs
      );
      expect(comp.tiersFinanceursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const strategieCmgGed: IStrategieCmgGed = { id: 456 };
      const aide: IAide = { id: 82740 };
      strategieCmgGed.aide = aide;
      const tiersFinanceur: ITiersFinanceur = { id: 31843 };
      strategieCmgGed.tiersFinanceur = tiersFinanceur;

      activatedRoute.data = of({ strategieCmgGed });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(strategieCmgGed));
      expect(comp.aidesSharedCollection).toContain(aide);
      expect(comp.tiersFinanceursSharedCollection).toContain(tiersFinanceur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategieCmgGed>>();
      const strategieCmgGed = { id: 123 };
      jest.spyOn(strategieCmgGedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategieCmgGed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategieCmgGed }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(strategieCmgGedService.update).toHaveBeenCalledWith(strategieCmgGed);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategieCmgGed>>();
      const strategieCmgGed = new StrategieCmgGed();
      jest.spyOn(strategieCmgGedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategieCmgGed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategieCmgGed }));
      saveSubject.complete();

      // THEN
      expect(strategieCmgGedService.create).toHaveBeenCalledWith(strategieCmgGed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategieCmgGed>>();
      const strategieCmgGed = { id: 123 };
      jest.spyOn(strategieCmgGedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategieCmgGed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(strategieCmgGedService.update).toHaveBeenCalledWith(strategieCmgGed);
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
