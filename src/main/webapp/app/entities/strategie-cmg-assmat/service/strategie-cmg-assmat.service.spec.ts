import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStrategieCmgAssmat, StrategieCmgAssmat } from '../strategie-cmg-assmat.model';

import { StrategieCmgAssmatService } from './strategie-cmg-assmat.service';

describe('StrategieCmgAssmat Service', () => {
  let service: StrategieCmgAssmatService;
  let httpMock: HttpTestingController;
  let elemDefault: IStrategieCmgAssmat;
  let expectedResult: IStrategieCmgAssmat | IStrategieCmgAssmat[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StrategieCmgAssmatService);
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

    it('should create a StrategieCmgAssmat', () => {
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

      service.create(new StrategieCmgAssmat()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StrategieCmgAssmat', () => {
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

    it('should partial update a StrategieCmgAssmat', () => {
      const patchObject = Object.assign(
        {
          anne: 1,
          mois: 1,
          isActif: true,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new StrategieCmgAssmat()
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

    it('should return a list of StrategieCmgAssmat', () => {
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

    it('should delete a StrategieCmgAssmat', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStrategieCmgAssmatToCollectionIfMissing', () => {
      it('should add a StrategieCmgAssmat to an empty array', () => {
        const strategieCmgAssmat: IStrategieCmgAssmat = { id: 123 };
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing([], strategieCmgAssmat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(strategieCmgAssmat);
      });

      it('should not add a StrategieCmgAssmat to an array that contains it', () => {
        const strategieCmgAssmat: IStrategieCmgAssmat = { id: 123 };
        const strategieCmgAssmatCollection: IStrategieCmgAssmat[] = [
          {
            ...strategieCmgAssmat,
          },
          { id: 456 },
        ];
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing(strategieCmgAssmatCollection, strategieCmgAssmat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StrategieCmgAssmat to an array that doesn't contain it", () => {
        const strategieCmgAssmat: IStrategieCmgAssmat = { id: 123 };
        const strategieCmgAssmatCollection: IStrategieCmgAssmat[] = [{ id: 456 }];
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing(strategieCmgAssmatCollection, strategieCmgAssmat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(strategieCmgAssmat);
      });

      it('should add only unique StrategieCmgAssmat to an array', () => {
        const strategieCmgAssmatArray: IStrategieCmgAssmat[] = [{ id: 123 }, { id: 456 }, { id: 57375 }];
        const strategieCmgAssmatCollection: IStrategieCmgAssmat[] = [{ id: 123 }];
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing(strategieCmgAssmatCollection, ...strategieCmgAssmatArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const strategieCmgAssmat: IStrategieCmgAssmat = { id: 123 };
        const strategieCmgAssmat2: IStrategieCmgAssmat = { id: 456 };
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing([], strategieCmgAssmat, strategieCmgAssmat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(strategieCmgAssmat);
        expect(expectedResult).toContain(strategieCmgAssmat2);
      });

      it('should accept null and undefined values', () => {
        const strategieCmgAssmat: IStrategieCmgAssmat = { id: 123 };
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing([], null, strategieCmgAssmat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(strategieCmgAssmat);
      });

      it('should return initial array if no StrategieCmgAssmat is added', () => {
        const strategieCmgAssmatCollection: IStrategieCmgAssmat[] = [{ id: 123 }];
        expectedResult = service.addStrategieCmgAssmatToCollectionIfMissing(strategieCmgAssmatCollection, undefined, null);
        expect(expectedResult).toEqual(strategieCmgAssmatCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
