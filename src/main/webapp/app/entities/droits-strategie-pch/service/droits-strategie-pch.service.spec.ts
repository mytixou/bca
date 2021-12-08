import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDroitsStrategiePch, DroitsStrategiePch } from '../droits-strategie-pch.model';

import { DroitsStrategiePchService } from './droits-strategie-pch.service';

describe('DroitsStrategiePch Service', () => {
  let service: DroitsStrategiePchService;
  let httpMock: HttpTestingController;
  let elemDefault: IDroitsStrategiePch;
  let expectedResult: IDroitsStrategiePch | IDroitsStrategiePch[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DroitsStrategiePchService);
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

    it('should create a DroitsStrategiePch', () => {
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

      service.create(new DroitsStrategiePch()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DroitsStrategiePch', () => {
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

    it('should partial update a DroitsStrategiePch', () => {
      const patchObject = Object.assign(
        {
          isActif: true,
          mois: 1,
          tauxCotisations: 1,
        },
        new DroitsStrategiePch()
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

    it('should return a list of DroitsStrategiePch', () => {
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

    it('should delete a DroitsStrategiePch', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDroitsStrategiePchToCollectionIfMissing', () => {
      it('should add a DroitsStrategiePch to an empty array', () => {
        const droitsStrategiePch: IDroitsStrategiePch = { id: 123 };
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing([], droitsStrategiePch);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategiePch);
      });

      it('should not add a DroitsStrategiePch to an array that contains it', () => {
        const droitsStrategiePch: IDroitsStrategiePch = { id: 123 };
        const droitsStrategiePchCollection: IDroitsStrategiePch[] = [
          {
            ...droitsStrategiePch,
          },
          { id: 456 },
        ];
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing(droitsStrategiePchCollection, droitsStrategiePch);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DroitsStrategiePch to an array that doesn't contain it", () => {
        const droitsStrategiePch: IDroitsStrategiePch = { id: 123 };
        const droitsStrategiePchCollection: IDroitsStrategiePch[] = [{ id: 456 }];
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing(droitsStrategiePchCollection, droitsStrategiePch);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategiePch);
      });

      it('should add only unique DroitsStrategiePch to an array', () => {
        const droitsStrategiePchArray: IDroitsStrategiePch[] = [{ id: 123 }, { id: 456 }, { id: 52770 }];
        const droitsStrategiePchCollection: IDroitsStrategiePch[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing(droitsStrategiePchCollection, ...droitsStrategiePchArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const droitsStrategiePch: IDroitsStrategiePch = { id: 123 };
        const droitsStrategiePch2: IDroitsStrategiePch = { id: 456 };
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing([], droitsStrategiePch, droitsStrategiePch2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategiePch);
        expect(expectedResult).toContain(droitsStrategiePch2);
      });

      it('should accept null and undefined values', () => {
        const droitsStrategiePch: IDroitsStrategiePch = { id: 123 };
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing([], null, droitsStrategiePch, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategiePch);
      });

      it('should return initial array if no DroitsStrategiePch is added', () => {
        const droitsStrategiePchCollection: IDroitsStrategiePch[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategiePchToCollectionIfMissing(droitsStrategiePchCollection, undefined, null);
        expect(expectedResult).toEqual(droitsStrategiePchCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
