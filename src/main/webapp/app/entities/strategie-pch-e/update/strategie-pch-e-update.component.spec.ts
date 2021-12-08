jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StrategiePchEService } from '../service/strategie-pch-e.service';
import { IStrategiePchE, StrategiePchE } from '../strategie-pch-e.model';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';
import { INatureActivite } from 'app/entities/nature-activite/nature-activite.model';
import { NatureActiviteService } from 'app/entities/nature-activite/service/nature-activite.service';
import { INatureMontant } from 'app/entities/nature-montant/nature-montant.model';
import { NatureMontantService } from 'app/entities/nature-montant/service/nature-montant.service';

import { StrategiePchEUpdateComponent } from './strategie-pch-e-update.component';

describe('StrategiePchE Management Update Component', () => {
  let comp: StrategiePchEUpdateComponent;
  let fixture: ComponentFixture<StrategiePchEUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let strategiePchEService: StrategiePchEService;
  let aideService: AideService;
  let tiersFinanceurService: TiersFinanceurService;
  let natureActiviteService: NatureActiviteService;
  let natureMontantService: NatureMontantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StrategiePchEUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(StrategiePchEUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StrategiePchEUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    strategiePchEService = TestBed.inject(StrategiePchEService);
    aideService = TestBed.inject(AideService);
    tiersFinanceurService = TestBed.inject(TiersFinanceurService);
    natureActiviteService = TestBed.inject(NatureActiviteService);
    natureMontantService = TestBed.inject(NatureMontantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Aide query and add missing value', () => {
      const strategiePchE: IStrategiePchE = { id: 456 };
      const aide: IAide = { id: 60680 };
      strategiePchE.aide = aide;

      const aideCollection: IAide[] = [{ id: 4398 }];
      jest.spyOn(aideService, 'query').mockReturnValue(of(new HttpResponse({ body: aideCollection })));
      const additionalAides = [aide];
      const expectedCollection: IAide[] = [...additionalAides, ...aideCollection];
      jest.spyOn(aideService, 'addAideToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      expect(aideService.query).toHaveBeenCalled();
      expect(aideService.addAideToCollectionIfMissing).toHaveBeenCalledWith(aideCollection, ...additionalAides);
      expect(comp.aidesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TiersFinanceur query and add missing value', () => {
      const strategiePchE: IStrategiePchE = { id: 456 };
      const tiersFinanceur: ITiersFinanceur = { id: 44512 };
      strategiePchE.tiersFinanceur = tiersFinanceur;

      const tiersFinanceurCollection: ITiersFinanceur[] = [{ id: 58683 }];
      jest.spyOn(tiersFinanceurService, 'query').mockReturnValue(of(new HttpResponse({ body: tiersFinanceurCollection })));
      const additionalTiersFinanceurs = [tiersFinanceur];
      const expectedCollection: ITiersFinanceur[] = [...additionalTiersFinanceurs, ...tiersFinanceurCollection];
      jest.spyOn(tiersFinanceurService, 'addTiersFinanceurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      expect(tiersFinanceurService.query).toHaveBeenCalled();
      expect(tiersFinanceurService.addTiersFinanceurToCollectionIfMissing).toHaveBeenCalledWith(
        tiersFinanceurCollection,
        ...additionalTiersFinanceurs
      );
      expect(comp.tiersFinanceursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureActivite query and add missing value', () => {
      const strategiePchE: IStrategiePchE = { id: 456 };
      const natureActivites: INatureActivite[] = [{ id: 16772 }];
      strategiePchE.natureActivites = natureActivites;

      const natureActiviteCollection: INatureActivite[] = [{ id: 92670 }];
      jest.spyOn(natureActiviteService, 'query').mockReturnValue(of(new HttpResponse({ body: natureActiviteCollection })));
      const additionalNatureActivites = [...natureActivites];
      const expectedCollection: INatureActivite[] = [...additionalNatureActivites, ...natureActiviteCollection];
      jest.spyOn(natureActiviteService, 'addNatureActiviteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      expect(natureActiviteService.query).toHaveBeenCalled();
      expect(natureActiviteService.addNatureActiviteToCollectionIfMissing).toHaveBeenCalledWith(
        natureActiviteCollection,
        ...additionalNatureActivites
      );
      expect(comp.natureActivitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureMontant query and add missing value', () => {
      const strategiePchE: IStrategiePchE = { id: 456 };
      const natureMontants: INatureMontant[] = [{ id: 85449 }];
      strategiePchE.natureMontants = natureMontants;

      const natureMontantCollection: INatureMontant[] = [{ id: 52050 }];
      jest.spyOn(natureMontantService, 'query').mockReturnValue(of(new HttpResponse({ body: natureMontantCollection })));
      const additionalNatureMontants = [...natureMontants];
      const expectedCollection: INatureMontant[] = [...additionalNatureMontants, ...natureMontantCollection];
      jest.spyOn(natureMontantService, 'addNatureMontantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      expect(natureMontantService.query).toHaveBeenCalled();
      expect(natureMontantService.addNatureMontantToCollectionIfMissing).toHaveBeenCalledWith(
        natureMontantCollection,
        ...additionalNatureMontants
      );
      expect(comp.natureMontantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const strategiePchE: IStrategiePchE = { id: 456 };
      const aide: IAide = { id: 49635 };
      strategiePchE.aide = aide;
      const tiersFinanceur: ITiersFinanceur = { id: 1053 };
      strategiePchE.tiersFinanceur = tiersFinanceur;
      const natureActivites: INatureActivite = { id: 38837 };
      strategiePchE.natureActivites = [natureActivites];
      const natureMontants: INatureMontant = { id: 79810 };
      strategiePchE.natureMontants = [natureMontants];

      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(strategiePchE));
      expect(comp.aidesSharedCollection).toContain(aide);
      expect(comp.tiersFinanceursSharedCollection).toContain(tiersFinanceur);
      expect(comp.natureActivitesSharedCollection).toContain(natureActivites);
      expect(comp.natureMontantsSharedCollection).toContain(natureMontants);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategiePchE>>();
      const strategiePchE = { id: 123 };
      jest.spyOn(strategiePchEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategiePchE }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(strategiePchEService.update).toHaveBeenCalledWith(strategiePchE);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategiePchE>>();
      const strategiePchE = new StrategiePchE();
      jest.spyOn(strategiePchEService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategiePchE }));
      saveSubject.complete();

      // THEN
      expect(strategiePchEService.create).toHaveBeenCalledWith(strategiePchE);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategiePchE>>();
      const strategiePchE = { id: 123 };
      jest.spyOn(strategiePchEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategiePchE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(strategiePchEService.update).toHaveBeenCalledWith(strategiePchE);
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

    describe('trackNatureActiviteById', () => {
      it('Should return tracked NatureActivite primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureActiviteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNatureMontantById', () => {
      it('Should return tracked NatureMontant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureMontantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedNatureActivite', () => {
      it('Should return option if no NatureActivite is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedNatureActivite(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected NatureActivite for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedNatureActivite(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this NatureActivite is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedNatureActivite(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });

    describe('getSelectedNatureMontant', () => {
      it('Should return option if no NatureMontant is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedNatureMontant(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected NatureMontant for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedNatureMontant(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this NatureMontant is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedNatureMontant(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
