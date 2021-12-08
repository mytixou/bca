import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDroitAide, DroitAide } from '../droit-aide.model';

import { DroitAideService } from './droit-aide.service';

describe('DroitAide Service', () => {
  let service: DroitAideService;
  let httpMock: HttpTestingController;
  let elemDefault: IDroitAide;
  let expectedResult: IDroitAide | IDroitAide[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DroitAideService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      isActif: false,
      anne: 0,
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

    it('should create a DroitAide', () => {
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

      service.create(new DroitAide()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DroitAide', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          anne: 1,
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

    it('should partial update a DroitAide', () => {
      const patchObject = Object.assign(
        {
          anne: 1,
          dateFermeture: currentDate.format(DATE_FORMAT),
        },
        new DroitAide()
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

    it('should return a list of DroitAide', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          anne: 1,
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

    it('should delete a DroitAide', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDroitAideToCollectionIfMissing', () => {
      it('should add a DroitAide to an empty array', () => {
        const droitAide: IDroitAide = { id: 123 };
        expectedResult = service.addDroitAideToCollectionIfMissing([], droitAide);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitAide);
      });

      it('should not add a DroitAide to an array that contains it', () => {
        const droitAide: IDroitAide = { id: 123 };
        const droitAideCollection: IDroitAide[] = [
          {
            ...droitAide,
          },
          { id: 456 },
        ];
        expectedResult = service.addDroitAideToCollectionIfMissing(droitAideCollection, droitAide);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DroitAide to an array that doesn't contain it", () => {
        const droitAide: IDroitAide = { id: 123 };
        const droitAideCollection: IDroitAide[] = [{ id: 456 }];
        expectedResult = service.addDroitAideToCollectionIfMissing(droitAideCollection, droitAide);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitAide);
      });

      it('should add only unique DroitAide to an array', () => {
        const droitAideArray: IDroitAide[] = [{ id: 123 }, { id: 456 }, { id: 82815 }];
        const droitAideCollection: IDroitAide[] = [{ id: 123 }];
        expectedResult = service.addDroitAideToCollectionIfMissing(droitAideCollection, ...droitAideArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const droitAide: IDroitAide = { id: 123 };
        const droitAide2: IDroitAide = { id: 456 };
        expectedResult = service.addDroitAideToCollectionIfMissing([], droitAide, droitAide2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitAide);
        expect(expectedResult).toContain(droitAide2);
      });

      it('should accept null and undefined values', () => {
        const droitAide: IDroitAide = { id: 123 };
        expectedResult = service.addDroitAideToCollectionIfMissing([], null, droitAide, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitAide);
      });

      it('should return initial array if no DroitAide is added', () => {
        const droitAideCollection: IDroitAide[] = [{ id: 123 }];
        expectedResult = service.addDroitAideToCollectionIfMissing(droitAideCollection, undefined, null);
        expect(expectedResult).toEqual(droitAideCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
