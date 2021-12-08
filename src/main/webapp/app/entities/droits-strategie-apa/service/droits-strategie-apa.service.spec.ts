import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDroitsStrategieApa, DroitsStrategieApa } from '../droits-strategie-apa.model';

import { DroitsStrategieApaService } from './droits-strategie-apa.service';

describe('DroitsStrategieApa Service', () => {
  let service: DroitsStrategieApaService;
  let httpMock: HttpTestingController;
  let elemDefault: IDroitsStrategieApa;
  let expectedResult: IDroitsStrategieApa | IDroitsStrategieApa[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DroitsStrategieApaService);
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

    it('should create a DroitsStrategieApa', () => {
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

      service.create(new DroitsStrategieApa()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DroitsStrategieApa', () => {
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

    it('should partial update a DroitsStrategieApa', () => {
      const patchObject = Object.assign(
        {
          isActif: true,
          tauxCotisations: 1,
          dateOuverture: currentDate.format(DATE_FORMAT),
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        new DroitsStrategieApa()
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

    it('should return a list of DroitsStrategieApa', () => {
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

    it('should delete a DroitsStrategieApa', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDroitsStrategieApaToCollectionIfMissing', () => {
      it('should add a DroitsStrategieApa to an empty array', () => {
        const droitsStrategieApa: IDroitsStrategieApa = { id: 123 };
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing([], droitsStrategieApa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategieApa);
      });

      it('should not add a DroitsStrategieApa to an array that contains it', () => {
        const droitsStrategieApa: IDroitsStrategieApa = { id: 123 };
        const droitsStrategieApaCollection: IDroitsStrategieApa[] = [
          {
            ...droitsStrategieApa,
          },
          { id: 456 },
        ];
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing(droitsStrategieApaCollection, droitsStrategieApa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DroitsStrategieApa to an array that doesn't contain it", () => {
        const droitsStrategieApa: IDroitsStrategieApa = { id: 123 };
        const droitsStrategieApaCollection: IDroitsStrategieApa[] = [{ id: 456 }];
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing(droitsStrategieApaCollection, droitsStrategieApa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategieApa);
      });

      it('should add only unique DroitsStrategieApa to an array', () => {
        const droitsStrategieApaArray: IDroitsStrategieApa[] = [{ id: 123 }, { id: 456 }, { id: 66540 }];
        const droitsStrategieApaCollection: IDroitsStrategieApa[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing(droitsStrategieApaCollection, ...droitsStrategieApaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const droitsStrategieApa: IDroitsStrategieApa = { id: 123 };
        const droitsStrategieApa2: IDroitsStrategieApa = { id: 456 };
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing([], droitsStrategieApa, droitsStrategieApa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategieApa);
        expect(expectedResult).toContain(droitsStrategieApa2);
      });

      it('should accept null and undefined values', () => {
        const droitsStrategieApa: IDroitsStrategieApa = { id: 123 };
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing([], null, droitsStrategieApa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategieApa);
      });

      it('should return initial array if no DroitsStrategieApa is added', () => {
        const droitsStrategieApaCollection: IDroitsStrategieApa[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategieApaToCollectionIfMissing(droitsStrategieApaCollection, undefined, null);
        expect(expectedResult).toEqual(droitsStrategieApaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
