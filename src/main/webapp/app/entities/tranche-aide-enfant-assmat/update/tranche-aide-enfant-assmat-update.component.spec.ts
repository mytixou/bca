jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';
import { ITrancheAideEnfantAssmat, TrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';
import { IStrategieCmgAssmat } from 'app/entities/strategie-cmg-assmat/strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from 'app/entities/strategie-cmg-assmat/service/strategie-cmg-assmat.service';

import { TrancheAideEnfantAssmatUpdateComponent } from './tranche-aide-enfant-assmat-update.component';

describe('TrancheAideEnfantAssmat Management Update Component', () => {
  let comp: TrancheAideEnfantAssmatUpdateComponent;
  let fixture: ComponentFixture<TrancheAideEnfantAssmatUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trancheAideEnfantAssmatService: TrancheAideEnfantAssmatService;
  let strategieCmgAssmatService: StrategieCmgAssmatService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TrancheAideEnfantAssmatUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TrancheAideEnfantAssmatUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrancheAideEnfantAssmatUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trancheAideEnfantAssmatService = TestBed.inject(TrancheAideEnfantAssmatService);
    strategieCmgAssmatService = TestBed.inject(StrategieCmgAssmatService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call StrategieCmgAssmat query and add missing value', () => {
      const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 456 };
      const strategieCmgAssmat: IStrategieCmgAssmat = { id: 66252 };
      trancheAideEnfantAssmat.strategieCmgAssmat = strategieCmgAssmat;

      const strategieCmgAssmatCollection: IStrategieCmgAssmat[] = [{ id: 15334 }];
      jest.spyOn(strategieCmgAssmatService, 'query').mockReturnValue(of(new HttpResponse({ body: strategieCmgAssmatCollection })));
      const additionalStrategieCmgAssmats = [strategieCmgAssmat];
      const expectedCollection: IStrategieCmgAssmat[] = [...additionalStrategieCmgAssmats, ...strategieCmgAssmatCollection];
      jest.spyOn(strategieCmgAssmatService, 'addStrategieCmgAssmatToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trancheAideEnfantAssmat });
      comp.ngOnInit();

      expect(strategieCmgAssmatService.query).toHaveBeenCalled();
      expect(strategieCmgAssmatService.addStrategieCmgAssmatToCollectionIfMissing).toHaveBeenCalledWith(
        strategieCmgAssmatCollection,
        ...additionalStrategieCmgAssmats
      );
      expect(comp.strategieCmgAssmatsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 456 };
      const strategieCmgAssmat: IStrategieCmgAssmat = { id: 33387 };
      trancheAideEnfantAssmat.strategieCmgAssmat = strategieCmgAssmat;

      activatedRoute.data = of({ trancheAideEnfantAssmat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(trancheAideEnfantAssmat));
      expect(comp.strategieCmgAssmatsSharedCollection).toContain(strategieCmgAssmat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrancheAideEnfantAssmat>>();
      const trancheAideEnfantAssmat = { id: 123 };
      jest.spyOn(trancheAideEnfantAssmatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trancheAideEnfantAssmat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trancheAideEnfantAssmat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(trancheAideEnfantAssmatService.update).toHaveBeenCalledWith(trancheAideEnfantAssmat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrancheAideEnfantAssmat>>();
      const trancheAideEnfantAssmat = new TrancheAideEnfantAssmat();
      jest.spyOn(trancheAideEnfantAssmatService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trancheAideEnfantAssmat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trancheAideEnfantAssmat }));
      saveSubject.complete();

      // THEN
      expect(trancheAideEnfantAssmatService.create).toHaveBeenCalledWith(trancheAideEnfantAssmat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrancheAideEnfantAssmat>>();
      const trancheAideEnfantAssmat = { id: 123 };
      jest.spyOn(trancheAideEnfantAssmatService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trancheAideEnfantAssmat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trancheAideEnfantAssmatService.update).toHaveBeenCalledWith(trancheAideEnfantAssmat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackStrategieCmgAssmatById', () => {
      it('Should return tracked StrategieCmgAssmat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStrategieCmgAssmatById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
