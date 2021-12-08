jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';
import { ITrancheAideEnfantGed, TrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';
import { IStrategieCmgGed } from 'app/entities/strategie-cmg-ged/strategie-cmg-ged.model';
import { StrategieCmgGedService } from 'app/entities/strategie-cmg-ged/service/strategie-cmg-ged.service';

import { TrancheAideEnfantGedUpdateComponent } from './tranche-aide-enfant-ged-update.component';

describe('TrancheAideEnfantGed Management Update Component', () => {
  let comp: TrancheAideEnfantGedUpdateComponent;
  let fixture: ComponentFixture<TrancheAideEnfantGedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trancheAideEnfantGedService: TrancheAideEnfantGedService;
  let strategieCmgGedService: StrategieCmgGedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TrancheAideEnfantGedUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TrancheAideEnfantGedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrancheAideEnfantGedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trancheAideEnfantGedService = TestBed.inject(TrancheAideEnfantGedService);
    strategieCmgGedService = TestBed.inject(StrategieCmgGedService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call StrategieCmgGed query and add missing value', () => {
      const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 456 };
      const strategieCmgGed: IStrategieCmgGed = { id: 74253 };
      trancheAideEnfantGed.strategieCmgGed = strategieCmgGed;

      const strategieCmgGedCollection: IStrategieCmgGed[] = [{ id: 54324 }];
      jest.spyOn(strategieCmgGedService, 'query').mockReturnValue(of(new HttpResponse({ body: strategieCmgGedCollection })));
      const additionalStrategieCmgGeds = [strategieCmgGed];
      const expectedCollection: IStrategieCmgGed[] = [...additionalStrategieCmgGeds, ...strategieCmgGedCollection];
      jest.spyOn(strategieCmgGedService, 'addStrategieCmgGedToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trancheAideEnfantGed });
      comp.ngOnInit();

      expect(strategieCmgGedService.query).toHaveBeenCalled();
      expect(strategieCmgGedService.addStrategieCmgGedToCollectionIfMissing).toHaveBeenCalledWith(
        strategieCmgGedCollection,
        ...additionalStrategieCmgGeds
      );
      expect(comp.strategieCmgGedsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 456 };
      const strategieCmgGed: IStrategieCmgGed = { id: 30497 };
      trancheAideEnfantGed.strategieCmgGed = strategieCmgGed;

      activatedRoute.data = of({ trancheAideEnfantGed });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(trancheAideEnfantGed));
      expect(comp.strategieCmgGedsSharedCollection).toContain(strategieCmgGed);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrancheAideEnfantGed>>();
      const trancheAideEnfantGed = { id: 123 };
      jest.spyOn(trancheAideEnfantGedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trancheAideEnfantGed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trancheAideEnfantGed }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(trancheAideEnfantGedService.update).toHaveBeenCalledWith(trancheAideEnfantGed);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrancheAideEnfantGed>>();
      const trancheAideEnfantGed = new TrancheAideEnfantGed();
      jest.spyOn(trancheAideEnfantGedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trancheAideEnfantGed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trancheAideEnfantGed }));
      saveSubject.complete();

      // THEN
      expect(trancheAideEnfantGedService.create).toHaveBeenCalledWith(trancheAideEnfantGed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrancheAideEnfantGed>>();
      const trancheAideEnfantGed = { id: 123 };
      jest.spyOn(trancheAideEnfantGedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trancheAideEnfantGed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trancheAideEnfantGedService.update).toHaveBeenCalledWith(trancheAideEnfantGed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackStrategieCmgGedById', () => {
      it('Should return tracked StrategieCmgGed primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStrategieCmgGedById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
