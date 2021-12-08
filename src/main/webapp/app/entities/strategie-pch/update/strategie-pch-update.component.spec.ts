jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StrategiePchService } from '../service/strategie-pch.service';
import { IStrategiePch, StrategiePch } from '../strategie-pch.model';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';
import { INatureActivite } from 'app/entities/nature-activite/nature-activite.model';
import { NatureActiviteService } from 'app/entities/nature-activite/service/nature-activite.service';
import { INatureMontant } from 'app/entities/nature-montant/nature-montant.model';
import { NatureMontantService } from 'app/entities/nature-montant/service/nature-montant.service';

import { StrategiePchUpdateComponent } from './strategie-pch-update.component';

describe('StrategiePch Management Update Component', () => {
  let comp: StrategiePchUpdateComponent;
  let fixture: ComponentFixture<StrategiePchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let strategiePchService: StrategiePchService;
  let aideService: AideService;
  let tiersFinanceurService: TiersFinanceurService;
  let natureActiviteService: NatureActiviteService;
  let natureMontantService: NatureMontantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [StrategiePchUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(StrategiePchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StrategiePchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    strategiePchService = TestBed.inject(StrategiePchService);
    aideService = TestBed.inject(AideService);
    tiersFinanceurService = TestBed.inject(TiersFinanceurService);
    natureActiviteService = TestBed.inject(NatureActiviteService);
    natureMontantService = TestBed.inject(NatureMontantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Aide query and add missing value', () => {
      const strategiePch: IStrategiePch = { id: 456 };
      const aide: IAide = { id: 12861 };
      strategiePch.aide = aide;

      const aideCollection: IAide[] = [{ id: 67232 }];
      jest.spyOn(aideService, 'query').mockReturnValue(of(new HttpResponse({ body: aideCollection })));
      const additionalAides = [aide];
      const expectedCollection: IAide[] = [...additionalAides, ...aideCollection];
      jest.spyOn(aideService, 'addAideToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      expect(aideService.query).toHaveBeenCalled();
      expect(aideService.addAideToCollectionIfMissing).toHaveBeenCalledWith(aideCollection, ...additionalAides);
      expect(comp.aidesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TiersFinanceur query and add missing value', () => {
      const strategiePch: IStrategiePch = { id: 456 };
      const tiersFinanceur: ITiersFinanceur = { id: 10524 };
      strategiePch.tiersFinanceur = tiersFinanceur;

      const tiersFinanceurCollection: ITiersFinanceur[] = [{ id: 18206 }];
      jest.spyOn(tiersFinanceurService, 'query').mockReturnValue(of(new HttpResponse({ body: tiersFinanceurCollection })));
      const additionalTiersFinanceurs = [tiersFinanceur];
      const expectedCollection: ITiersFinanceur[] = [...additionalTiersFinanceurs, ...tiersFinanceurCollection];
      jest.spyOn(tiersFinanceurService, 'addTiersFinanceurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      expect(tiersFinanceurService.query).toHaveBeenCalled();
      expect(tiersFinanceurService.addTiersFinanceurToCollectionIfMissing).toHaveBeenCalledWith(
        tiersFinanceurCollection,
        ...additionalTiersFinanceurs
      );
      expect(comp.tiersFinanceursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureActivite query and add missing value', () => {
      const strategiePch: IStrategiePch = { id: 456 };
      const natureActivites: INatureActivite[] = [{ id: 29199 }];
      strategiePch.natureActivites = natureActivites;

      const natureActiviteCollection: INatureActivite[] = [{ id: 71296 }];
      jest.spyOn(natureActiviteService, 'query').mockReturnValue(of(new HttpResponse({ body: natureActiviteCollection })));
      const additionalNatureActivites = [...natureActivites];
      const expectedCollection: INatureActivite[] = [...additionalNatureActivites, ...natureActiviteCollection];
      jest.spyOn(natureActiviteService, 'addNatureActiviteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      expect(natureActiviteService.query).toHaveBeenCalled();
      expect(natureActiviteService.addNatureActiviteToCollectionIfMissing).toHaveBeenCalledWith(
        natureActiviteCollection,
        ...additionalNatureActivites
      );
      expect(comp.natureActivitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureMontant query and add missing value', () => {
      const strategiePch: IStrategiePch = { id: 456 };
      const natureMontants: INatureMontant[] = [{ id: 28312 }];
      strategiePch.natureMontants = natureMontants;

      const natureMontantCollection: INatureMontant[] = [{ id: 56837 }];
      jest.spyOn(natureMontantService, 'query').mockReturnValue(of(new HttpResponse({ body: natureMontantCollection })));
      const additionalNatureMontants = [...natureMontants];
      const expectedCollection: INatureMontant[] = [...additionalNatureMontants, ...natureMontantCollection];
      jest.spyOn(natureMontantService, 'addNatureMontantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      expect(natureMontantService.query).toHaveBeenCalled();
      expect(natureMontantService.addNatureMontantToCollectionIfMissing).toHaveBeenCalledWith(
        natureMontantCollection,
        ...additionalNatureMontants
      );
      expect(comp.natureMontantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const strategiePch: IStrategiePch = { id: 456 };
      const aide: IAide = { id: 42618 };
      strategiePch.aide = aide;
      const tiersFinanceur: ITiersFinanceur = { id: 72396 };
      strategiePch.tiersFinanceur = tiersFinanceur;
      const natureActivites: INatureActivite = { id: 26362 };
      strategiePch.natureActivites = [natureActivites];
      const natureMontants: INatureMontant = { id: 41047 };
      strategiePch.natureMontants = [natureMontants];

      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(strategiePch));
      expect(comp.aidesSharedCollection).toContain(aide);
      expect(comp.tiersFinanceursSharedCollection).toContain(tiersFinanceur);
      expect(comp.natureActivitesSharedCollection).toContain(natureActivites);
      expect(comp.natureMontantsSharedCollection).toContain(natureMontants);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategiePch>>();
      const strategiePch = { id: 123 };
      jest.spyOn(strategiePchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategiePch }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(strategiePchService.update).toHaveBeenCalledWith(strategiePch);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategiePch>>();
      const strategiePch = new StrategiePch();
      jest.spyOn(strategiePchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: strategiePch }));
      saveSubject.complete();

      // THEN
      expect(strategiePchService.create).toHaveBeenCalledWith(strategiePch);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StrategiePch>>();
      const strategiePch = { id: 123 };
      jest.spyOn(strategiePchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ strategiePch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(strategiePchService.update).toHaveBeenCalledWith(strategiePch);
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
