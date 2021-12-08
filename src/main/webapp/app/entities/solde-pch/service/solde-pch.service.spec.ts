import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISoldePch, SoldePch } from '../solde-pch.model';

import { SoldePchService } from './solde-pch.service';

describe('SoldePch Service', () => {
  let service: SoldePchService;
  let httpMock: HttpTestingController;
  let elemDefault: ISoldePch;
  let expectedResult: ISoldePch | ISoldePch[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SoldePchService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      date: currentDate,
      isActif: false,
      isDernier: false,
      annee: 0,
      mois: 0,
      consoMontantPchCotisations: 0,
      consoMontantPchSalaire: 0,
      soldeMontantPch: 0,
      consoHeurePch: 0,
      soldeHeurePch: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SoldePch', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new SoldePch()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SoldePch', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          isActif: true,
          isDernier: true,
          annee: 1,
          mois: 1,
          consoMontantPchCotisations: 1,
          consoMontantPchSalaire: 1,
          soldeMontantPch: 1,
          consoHeurePch: 1,
          soldeHeurePch: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SoldePch', () => {
      const patchObject = Object.assign(
        {
          date: currentDate.format(DATE_TIME_FORMAT),
          mois: 1,
          consoHeurePch: 1,
        },
        new SoldePch()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SoldePch', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          date: currentDate.format(DATE_TIME_FORMAT),
          isActif: true,
          isDernier: true,
          annee: 1,
          mois: 1,
          consoMontantPchCotisations: 1,
          consoMontantPchSalaire: 1,
          soldeMontantPch: 1,
          consoHeurePch: 1,
          soldeHeurePch: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SoldePch', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSoldePchToCollectionIfMissing', () => {
      it('should add a SoldePch to an empty array', () => {
        const soldePch: ISoldePch = { id: 123 };
        expectedResult = service.addSoldePchToCollectionIfMissing([], soldePch);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldePch);
      });

      it('should not add a SoldePch to an array that contains it', () => {
        const soldePch: ISoldePch = { id: 123 };
        const soldePchCollection: ISoldePch[] = [
          {
            ...soldePch,
          },
          { id: 456 },
        ];
        expectedResult = service.addSoldePchToCollectionIfMissing(soldePchCollection, soldePch);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SoldePch to an array that doesn't contain it", () => {
        const soldePch: ISoldePch = { id: 123 };
        const soldePchCollection: ISoldePch[] = [{ id: 456 }];
        expectedResult = service.addSoldePchToCollectionIfMissing(soldePchCollection, soldePch);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldePch);
      });

      it('should add only unique SoldePch to an array', () => {
        const soldePchArray: ISoldePch[] = [{ id: 123 }, { id: 456 }, { id: 74793 }];
        const soldePchCollection: ISoldePch[] = [{ id: 123 }];
        expectedResult = service.addSoldePchToCollectionIfMissing(soldePchCollection, ...soldePchArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const soldePch: ISoldePch = { id: 123 };
        const soldePch2: ISoldePch = { id: 456 };
        expectedResult = service.addSoldePchToCollectionIfMissing([], soldePch, soldePch2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldePch);
        expect(expectedResult).toContain(soldePch2);
      });

      it('should accept null and undefined values', () => {
        const soldePch: ISoldePch = { id: 123 };
        expectedResult = service.addSoldePchToCollectionIfMissing([], null, soldePch, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldePch);
      });

      it('should return initial array if no SoldePch is added', () => {
        const soldePchCollection: ISoldePch[] = [{ id: 123 }];
        expectedResult = service.addSoldePchToCollectionIfMissing(soldePchCollection, undefined, null);
        expect(expectedResult).toEqual(soldePchCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
