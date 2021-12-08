jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DroitAideService } from '../service/droit-aide.service';
import { IDroitAide, DroitAide } from '../droit-aide.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IAide } from 'app/entities/aide/aide.model';
import { AideService } from 'app/entities/aide/service/aide.service';

import { DroitAideUpdateComponent } from './droit-aide-update.component';

describe('DroitAide Management Update Component', () => {
  let comp: DroitAideUpdateComponent;
  let fixture: ComponentFixture<DroitAideUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let droitAideService: DroitAideService;
  let produitService: ProduitService;
  let aideService: AideService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DroitAideUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DroitAideUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DroitAideUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    droitAideService = TestBed.inject(DroitAideService);
    produitService = TestBed.inject(ProduitService);
    aideService = TestBed.inject(AideService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const droitAide: IDroitAide = { id: 456 };
      const produit: IProduit = { id: 66018 };
      droitAide.produit = produit;

      const produitCollection: IProduit[] = [{ id: 23352 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droitAide });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Aide query and add missing value', () => {
      const droitAide: IDroitAide = { id: 456 };
      const produit: IAide = { id: 91607 };
      droitAide.produit = produit;

      const aideCollection: IAide[] = [{ id: 77431 }];
      jest.spyOn(aideService, 'query').mockReturnValue(of(new HttpResponse({ body: aideCollection })));
      const additionalAides = [produit];
      const expectedCollection: IAide[] = [...additionalAides, ...aideCollection];
      jest.spyOn(aideService, 'addAideToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ droitAide });
      comp.ngOnInit();

      expect(aideService.query).toHaveBeenCalled();
      expect(aideService.addAideToCollectionIfMissing).toHaveBeenCalledWith(aideCollection, ...additionalAides);
      expect(comp.aidesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const droitAide: IDroitAide = { id: 456 };
      const produit: IProduit = { id: 85788 };
      droitAide.produit = produit;
      const produit: IAide = { id: 97302 };
      droitAide.produit = produit;

      activatedRoute.data = of({ droitAide });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(droitAide));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.aidesSharedCollection).toContain(produit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitAide>>();
      const droitAide = { id: 123 };
      jest.spyOn(droitAideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitAide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitAide }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(droitAideService.update).toHaveBeenCalledWith(droitAide);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitAide>>();
      const droitAide = new DroitAide();
      jest.spyOn(droitAideService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitAide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: droitAide }));
      saveSubject.complete();

      // THEN
      expect(droitAideService.create).toHaveBeenCalledWith(droitAide);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DroitAide>>();
      const droitAide = { id: 123 };
      jest.spyOn(droitAideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ droitAide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(droitAideService.update).toHaveBeenCalledWith(droitAide);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProduitById', () => {
      it('Should return tracked Produit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProduitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAideById', () => {
      it('Should return tracked Aide primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAideById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
