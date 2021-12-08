import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITrancheAideEnfantAssmat, TrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';

import { TrancheAideEnfantAssmatService } from './tranche-aide-enfant-assmat.service';

describe('TrancheAideEnfantAssmat Service', () => {
  let service: TrancheAideEnfantAssmatService;
  let httpMock: HttpTestingController;
  let elemDefault: ITrancheAideEnfantAssmat;
  let expectedResult: ITrancheAideEnfantAssmat | ITrancheAideEnfantAssmat[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrancheAideEnfantAssmatService);
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

    it('should create a TrancheAideEnfantAssmat', () => {
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

      service.create(new TrancheAideEnfantAssmat()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrancheAideEnfantAssmat', () => {
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

    it('should partial update a TrancheAideEnfantAssmat', () => {
      const patchObject = Object.assign(
        {
          anne: 1,
          mois: 1,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          isUpdated: true,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new TrancheAideEnfantAssmat()
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

    it('should return a list of TrancheAideEnfantAssmat', () => {
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

    it('should delete a TrancheAideEnfantAssmat', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTrancheAideEnfantAssmatToCollectionIfMissing', () => {
      it('should add a TrancheAideEnfantAssmat to an empty array', () => {
        const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 123 };
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing([], trancheAideEnfantAssmat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trancheAideEnfantAssmat);
      });

      it('should not add a TrancheAideEnfantAssmat to an array that contains it', () => {
        const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 123 };
        const trancheAideEnfantAssmatCollection: ITrancheAideEnfantAssmat[] = [
          {
            ...trancheAideEnfantAssmat,
          },
          { id: 456 },
        ];
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing(
          trancheAideEnfantAssmatCollection,
          trancheAideEnfantAssmat
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrancheAideEnfantAssmat to an array that doesn't contain it", () => {
        const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 123 };
        const trancheAideEnfantAssmatCollection: ITrancheAideEnfantAssmat[] = [{ id: 456 }];
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing(
          trancheAideEnfantAssmatCollection,
          trancheAideEnfantAssmat
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trancheAideEnfantAssmat);
      });

      it('should add only unique TrancheAideEnfantAssmat to an array', () => {
        const trancheAideEnfantAssmatArray: ITrancheAideEnfantAssmat[] = [{ id: 123 }, { id: 456 }, { id: 72436 }];
        const trancheAideEnfantAssmatCollection: ITrancheAideEnfantAssmat[] = [{ id: 123 }];
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing(
          trancheAideEnfantAssmatCollection,
          ...trancheAideEnfantAssmatArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 123 };
        const trancheAideEnfantAssmat2: ITrancheAideEnfantAssmat = { id: 456 };
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing([], trancheAideEnfantAssmat, trancheAideEnfantAssmat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trancheAideEnfantAssmat);
        expect(expectedResult).toContain(trancheAideEnfantAssmat2);
      });

      it('should accept null and undefined values', () => {
        const trancheAideEnfantAssmat: ITrancheAideEnfantAssmat = { id: 123 };
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing([], null, trancheAideEnfantAssmat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trancheAideEnfantAssmat);
      });

      it('should return initial array if no TrancheAideEnfantAssmat is added', () => {
        const trancheAideEnfantAssmatCollection: ITrancheAideEnfantAssmat[] = [{ id: 123 }];
        expectedResult = service.addTrancheAideEnfantAssmatToCollectionIfMissing(trancheAideEnfantAssmatCollection, undefined, null);
        expect(expectedResult).toEqual(trancheAideEnfantAssmatCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
