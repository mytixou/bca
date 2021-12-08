jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AideService } from '../service/aide.service';
import { IAide, Aide } from '../aide.model';
import { ITiersFinanceur } from 'app/entities/tiers-financeur/tiers-financeur.model';
import { TiersFinanceurService } from 'app/entities/tiers-financeur/service/tiers-financeur.service';

import { AideUpdateComponent } from './aide-update.component';

describe('Aide Management Update Component', () => {
  let comp: AideUpdateComponent;
  let fixture: ComponentFixture<AideUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let aideService: AideService;
  let tiersFinanceurService: TiersFinanceurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AideUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AideUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AideUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    aideService = TestBed.inject(AideService);
    tiersFinanceurService = TestBed.inject(TiersFinanceurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TiersFinanceur query and add missing value', () => {
      const aide: IAide = { id: 456 };
      const aide: ITiersFinanceur = { id: 73919 };
      aide.aide = aide;

      const tiersFinanceurCollection: ITiersFinanceur[] = [{ id: 83092 }];
      jest.spyOn(tiersFinanceurService, 'query').mockReturnValue(of(new HttpResponse({ body: tiersFinanceurCollection })));
      const additionalTiersFinanceurs = [aide];
      const expectedCollection: ITiersFinanceur[] = [...additionalTiersFinanceurs, ...tiersFinanceurCollection];
      jest.spyOn(tiersFinanceurService, 'addTiersFinanceurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ aide });
      comp.ngOnInit();

      expect(tiersFinanceurService.query).toHaveBeenCalled();
      expect(tiersFinanceurService.addTiersFinanceurToCollectionIfMissing).toHaveBeenCalledWith(
        tiersFinanceurCollection,
        ...additionalTiersFinanceurs
      );
      expect(comp.tiersFinanceursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const aide: IAide = { id: 456 };
      const aide: ITiersFinanceur = { id: 6806 };
      aide.aide = aide;

      activatedRoute.data = of({ aide });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(aide));
      expect(comp.tiersFinanceursSharedCollection).toContain(aide);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aide>>();
      const aide = { id: 123 };
      jest.spyOn(aideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aide }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(aideService.update).toHaveBeenCalledWith(aide);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aide>>();
      const aide = new Aide();
      jest.spyOn(aideService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aide }));
      saveSubject.complete();

      // THEN
      expect(aideService.create).toHaveBeenCalledWith(aide);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Aide>>();
      const aide = { id: 123 };
      jest.spyOn(aideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(aideService.update).toHaveBeenCalledWith(aide);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTiersFinanceurById', () => {
      it('Should return tracked TiersFinanceur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTiersFinanceurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
