import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStrategieCmgGed, StrategieCmgGed } from '../strategie-cmg-ged.model';

import { StrategieCmgGedService } from './strategie-cmg-ged.service';

describe('StrategieCmgGed Service', () => {
  let service: StrategieCmgGedService;
  let httpMock: HttpTestingController;
  let elemDefault: IStrategieCmgGed;
  let expectedResult: IStrategieCmgGed | IStrategieCmgGed[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StrategieCmgGedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      anne: 0,
      mois: 0,
      nbHeureSeuilPlafond: 0,
      tauxSalaire: 0,
      tauxCotisations: 0,
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

    it('should create a StrategieCmgGed', () => {
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

      service.create(new StrategieCmgGed()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StrategieCmgGed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          anne: 1,
          mois: 1,
          nbHeureSeuilPlafond: 1,
          tauxSalaire: 1,
          tauxCotisations: 1,
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

    it('should partial update a StrategieCmgGed', () => {
      const patchObject = Object.assign(
        {
          mois: 1,
          tauxCotisations: 1,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new StrategieCmgGed()
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

    it('should return a list of StrategieCmgGed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          anne: 1,
          mois: 1,
          nbHeureSeuilPlafond: 1,
          tauxSalaire: 1,
          tauxCotisations: 1,
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

    it('should delete a StrategieCmgGed', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStrategieCmgGedToCollectionIfMissing', () => {
      it('should add a StrategieCmgGed to an empty array', () => {
        const strategieCmgGed: IStrategieCmgGed = { id: 123 };
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing([], strategieCmgGed);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(strategieCmgGed);
      });

      it('should not add a StrategieCmgGed to an array that contains it', () => {
        const strategieCmgGed: IStrategieCmgGed = { id: 123 };
        const strategieCmgGedCollection: IStrategieCmgGed[] = [
          {
            ...strategieCmgGed,
          },
          { id: 456 },
        ];
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing(strategieCmgGedCollection, strategieCmgGed);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StrategieCmgGed to an array that doesn't contain it", () => {
        const strategieCmgGed: IStrategieCmgGed = { id: 123 };
        const strategieCmgGedCollection: IStrategieCmgGed[] = [{ id: 456 }];
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing(strategieCmgGedCollection, strategieCmgGed);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(strategieCmgGed);
      });

      it('should add only unique StrategieCmgGed to an array', () => {
        const strategieCmgGedArray: IStrategieCmgGed[] = [{ id: 123 }, { id: 456 }, { id: 58129 }];
        const strategieCmgGedCollection: IStrategieCmgGed[] = [{ id: 123 }];
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing(strategieCmgGedCollection, ...strategieCmgGedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const strategieCmgGed: IStrategieCmgGed = { id: 123 };
        const strategieCmgGed2: IStrategieCmgGed = { id: 456 };
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing([], strategieCmgGed, strategieCmgGed2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(strategieCmgGed);
        expect(expectedResult).toContain(strategieCmgGed2);
      });

      it('should accept null and undefined values', () => {
        const strategieCmgGed: IStrategieCmgGed = { id: 123 };
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing([], null, strategieCmgGed, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(strategieCmgGed);
      });

      it('should return initial array if no StrategieCmgGed is added', () => {
        const strategieCmgGedCollection: IStrategieCmgGed[] = [{ id: 123 }];
        expectedResult = service.addStrategieCmgGedToCollectionIfMissing(strategieCmgGedCollection, undefined, null);
        expect(expectedResult).toEqual(strategieCmgGedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
