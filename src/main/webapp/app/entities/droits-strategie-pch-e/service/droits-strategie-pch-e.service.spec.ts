import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDroitsStrategiePchE, DroitsStrategiePchE } from '../droits-strategie-pch-e.model';

import { DroitsStrategiePchEService } from './droits-strategie-pch-e.service';

describe('DroitsStrategiePchE Service', () => {
  let service: DroitsStrategiePchEService;
  let httpMock: HttpTestingController;
  let elemDefault: IDroitsStrategiePchE;
  let expectedResult: IDroitsStrategiePchE | IDroitsStrategiePchE[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DroitsStrategiePchEService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      isActif: false,
      anne: 0,
      mois: 0,
      montantPlafond: 0,
      montantPlafondPlus: 0,
      nbHeurePlafond: 0,
      tauxCotisations: 0,
      dateOuverture: currentDate,
      dateFermeture: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DroitsStrategiePchE', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateFermeture: currentDate,
        },
        returnedFromService
      );

      service.create(new DroitsStrategiePchE()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DroitsStrategiePchE', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          anne: 1,
          mois: 1,
          montantPlafond: 1,
          montantPlafondPlus: 1,
          nbHeurePlafond: 1,
          tauxCotisations: 1,
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateFermeture: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DroitsStrategiePchE', () => {
      const patchObject = Object.assign(
        {
          isActif: true,
          montantPlafond: 1,
          nbHeurePlafond: 1,
          tauxCotisations: 1,
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        new DroitsStrategiePchE()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateFermeture: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DroitsStrategiePchE', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          anne: 1,
          mois: 1,
          montantPlafond: 1,
          montantPlafondPlus: 1,
          nbHeurePlafond: 1,
          tauxCotisations: 1,
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOuverture: currentDate,
          dateFermeture: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DroitsStrategiePchE', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDroitsStrategiePchEToCollectionIfMissing', () => {
      it('should add a DroitsStrategiePchE to an empty array', () => {
        const droitsStrategiePchE: IDroitsStrategiePchE = { id: 123 };
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing([], droitsStrategiePchE);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategiePchE);
      });

      it('should not add a DroitsStrategiePchE to an array that contains it', () => {
        const droitsStrategiePchE: IDroitsStrategiePchE = { id: 123 };
        const droitsStrategiePchECollection: IDroitsStrategiePchE[] = [
          {
            ...droitsStrategiePchE,
          },
          { id: 456 },
        ];
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing(droitsStrategiePchECollection, droitsStrategiePchE);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DroitsStrategiePchE to an array that doesn't contain it", () => {
        const droitsStrategiePchE: IDroitsStrategiePchE = { id: 123 };
        const droitsStrategiePchECollection: IDroitsStrategiePchE[] = [{ id: 456 }];
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing(droitsStrategiePchECollection, droitsStrategiePchE);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategiePchE);
      });

      it('should add only unique DroitsStrategiePchE to an array', () => {
        const droitsStrategiePchEArray: IDroitsStrategiePchE[] = [{ id: 123 }, { id: 456 }, { id: 34897 }];
        const droitsStrategiePchECollection: IDroitsStrategiePchE[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing(droitsStrategiePchECollection, ...droitsStrategiePchEArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const droitsStrategiePchE: IDroitsStrategiePchE = { id: 123 };
        const droitsStrategiePchE2: IDroitsStrategiePchE = { id: 456 };
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing([], droitsStrategiePchE, droitsStrategiePchE2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategiePchE);
        expect(expectedResult).toContain(droitsStrategiePchE2);
      });

      it('should accept null and undefined values', () => {
        const droitsStrategiePchE: IDroitsStrategiePchE = { id: 123 };
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing([], null, droitsStrategiePchE, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategiePchE);
      });

      it('should return initial array if no DroitsStrategiePchE is added', () => {
        const droitsStrategiePchECollection: IDroitsStrategiePchE[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategiePchEToCollectionIfMissing(droitsStrategiePchECollection, undefined, null);
        expect(expectedResult).toEqual(droitsStrategiePchECollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
