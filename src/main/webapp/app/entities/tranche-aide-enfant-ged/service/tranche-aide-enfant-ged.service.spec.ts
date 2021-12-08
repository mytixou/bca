import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITrancheAideEnfantGed, TrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';

import { TrancheAideEnfantGedService } from './tranche-aide-enfant-ged.service';

describe('TrancheAideEnfantGed Service', () => {
  let service: TrancheAideEnfantGedService;
  let httpMock: HttpTestingController;
  let elemDefault: ITrancheAideEnfantGed;
  let expectedResult: ITrancheAideEnfantGed | ITrancheAideEnfantGed[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrancheAideEnfantGedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      anne: 0,
      mois: 0,
      ageEnfantRevoluSurPeriode: 0,
      montantPlafondSalaire: 0,
      isActif: false,
      dateCreated: currentDate,
      isUpdated: false,
      dateModified: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TrancheAideEnfantGed', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.create(new TrancheAideEnfantGed()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrancheAideEnfantGed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          anne: 1,
          mois: 1,
          ageEnfantRevoluSurPeriode: 1,
          montantPlafondSalaire: 1,
          isActif: true,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          isUpdated: true,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrancheAideEnfantGed', () => {
      const patchObject = Object.assign(
        {
          anne: 1,
          mois: 1,
          ageEnfantRevoluSurPeriode: 1,
          isActif: true,
          isUpdated: true,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new TrancheAideEnfantGed()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrancheAideEnfantGed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          anne: 1,
          mois: 1,
          ageEnfantRevoluSurPeriode: 1,
          montantPlafondSalaire: 1,
          isActif: true,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          isUpdated: true,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TrancheAideEnfantGed', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTrancheAideEnfantGedToCollectionIfMissing', () => {
      it('should add a TrancheAideEnfantGed to an empty array', () => {
        const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 123 };
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing([], trancheAideEnfantGed);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trancheAideEnfantGed);
      });

      it('should not add a TrancheAideEnfantGed to an array that contains it', () => {
        const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 123 };
        const trancheAideEnfantGedCollection: ITrancheAideEnfantGed[] = [
          {
            ...trancheAideEnfantGed,
          },
          { id: 456 },
        ];
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing(trancheAideEnfantGedCollection, trancheAideEnfantGed);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrancheAideEnfantGed to an array that doesn't contain it", () => {
        const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 123 };
        const trancheAideEnfantGedCollection: ITrancheAideEnfantGed[] = [{ id: 456 }];
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing(trancheAideEnfantGedCollection, trancheAideEnfantGed);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trancheAideEnfantGed);
      });

      it('should add only unique TrancheAideEnfantGed to an array', () => {
        const trancheAideEnfantGedArray: ITrancheAideEnfantGed[] = [{ id: 123 }, { id: 456 }, { id: 9187 }];
        const trancheAideEnfantGedCollection: ITrancheAideEnfantGed[] = [{ id: 123 }];
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing(trancheAideEnfantGedCollection, ...trancheAideEnfantGedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 123 };
        const trancheAideEnfantGed2: ITrancheAideEnfantGed = { id: 456 };
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing([], trancheAideEnfantGed, trancheAideEnfantGed2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trancheAideEnfantGed);
        expect(expectedResult).toContain(trancheAideEnfantGed2);
      });

      it('should accept null and undefined values', () => {
        const trancheAideEnfantGed: ITrancheAideEnfantGed = { id: 123 };
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing([], null, trancheAideEnfantGed, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trancheAideEnfantGed);
      });

      it('should return initial array if no TrancheAideEnfantGed is added', () => {
        const trancheAideEnfantGedCollection: ITrancheAideEnfantGed[] = [{ id: 123 }];
        expectedResult = service.addTrancheAideEnfantGedToCollectionIfMissing(trancheAideEnfantGedCollection, undefined, null);
        expect(expectedResult).toEqual(trancheAideEnfantGedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
