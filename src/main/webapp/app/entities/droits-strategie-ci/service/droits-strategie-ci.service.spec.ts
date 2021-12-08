import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDroitsStrategieCi, DroitsStrategieCi } from '../droits-strategie-ci.model';

import { DroitsStrategieCiService } from './droits-strategie-ci.service';

describe('DroitsStrategieCi Service', () => {
  let service: DroitsStrategieCiService;
  let httpMock: HttpTestingController;
  let elemDefault: IDroitsStrategieCi;
  let expectedResult: IDroitsStrategieCi | IDroitsStrategieCi[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DroitsStrategieCiService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      isActif: false,
      anne: 0,
      montantPlafondDefaut: 0,
      montantPlafondHandicape: 0,
      montantPlafondDefautPlus: 0,
      montantPlafondHandicapePlus: 0,
      tauxSalaire: 0,
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

    it('should create a DroitsStrategieCi', () => {
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

      service.create(new DroitsStrategieCi()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DroitsStrategieCi', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          anne: 1,
          montantPlafondDefaut: 1,
          montantPlafondHandicape: 1,
          montantPlafondDefautPlus: 1,
          montantPlafondHandicapePlus: 1,
          tauxSalaire: 1,
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

    it('should partial update a DroitsStrategieCi', () => {
      const patchObject = Object.assign(
        {
          isActif: true,
          anne: 1,
          montantPlafondDefaut: 1,
          montantPlafondHandicape: 1,
          montantPlafondHandicapePlus: 1,
          tauxSalaire: 1,
        },
        new DroitsStrategieCi()
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

    it('should return a list of DroitsStrategieCi', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          anne: 1,
          montantPlafondDefaut: 1,
          montantPlafondHandicape: 1,
          montantPlafondDefautPlus: 1,
          montantPlafondHandicapePlus: 1,
          tauxSalaire: 1,
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

    it('should delete a DroitsStrategieCi', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDroitsStrategieCiToCollectionIfMissing', () => {
      it('should add a DroitsStrategieCi to an empty array', () => {
        const droitsStrategieCi: IDroitsStrategieCi = { id: 123 };
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing([], droitsStrategieCi);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategieCi);
      });

      it('should not add a DroitsStrategieCi to an array that contains it', () => {
        const droitsStrategieCi: IDroitsStrategieCi = { id: 123 };
        const droitsStrategieCiCollection: IDroitsStrategieCi[] = [
          {
            ...droitsStrategieCi,
          },
          { id: 456 },
        ];
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing(droitsStrategieCiCollection, droitsStrategieCi);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DroitsStrategieCi to an array that doesn't contain it", () => {
        const droitsStrategieCi: IDroitsStrategieCi = { id: 123 };
        const droitsStrategieCiCollection: IDroitsStrategieCi[] = [{ id: 456 }];
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing(droitsStrategieCiCollection, droitsStrategieCi);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategieCi);
      });

      it('should add only unique DroitsStrategieCi to an array', () => {
        const droitsStrategieCiArray: IDroitsStrategieCi[] = [{ id: 123 }, { id: 456 }, { id: 18239 }];
        const droitsStrategieCiCollection: IDroitsStrategieCi[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing(droitsStrategieCiCollection, ...droitsStrategieCiArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const droitsStrategieCi: IDroitsStrategieCi = { id: 123 };
        const droitsStrategieCi2: IDroitsStrategieCi = { id: 456 };
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing([], droitsStrategieCi, droitsStrategieCi2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(droitsStrategieCi);
        expect(expectedResult).toContain(droitsStrategieCi2);
      });

      it('should accept null and undefined values', () => {
        const droitsStrategieCi: IDroitsStrategieCi = { id: 123 };
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing([], null, droitsStrategieCi, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(droitsStrategieCi);
      });

      it('should return initial array if no DroitsStrategieCi is added', () => {
        const droitsStrategieCiCollection: IDroitsStrategieCi[] = [{ id: 123 }];
        expectedResult = service.addDroitsStrategieCiToCollectionIfMissing(droitsStrategieCiCollection, undefined, null);
        expect(expectedResult).toEqual(droitsStrategieCiCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
