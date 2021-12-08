import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IStrategieApa, StrategieApa } from '../strategie-apa.model';

import { StrategieApaService } from './strategie-apa.service';

describe('StrategieApa Service', () => {
  let service: StrategieApaService;
  let httpMock: HttpTestingController;
  let elemDefault: IStrategieApa;
  let expectedResult: IStrategieApa | IStrategieApa[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StrategieApaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      isActif: false,
      dateMensuelleDebutValidite: currentDate,
      anne: 0,
      mois: 0,
      montantPlafondSalaire: 0,
      montantPlafondCotisations: 0,
      montantPlafondSalairePlus: 0,
      montantPlafondCotisationsPlus: 0,
      nbHeureSalairePlafond: 0,
      tauxSalaire: 0,
      tauxCotisations: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateMensuelleDebutValidite: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a StrategieApa', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateMensuelleDebutValidite: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateMensuelleDebutValidite: currentDate,
        },
        returnedFromService
      );

      service.create(new StrategieApa()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StrategieApa', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          dateMensuelleDebutValidite: currentDate.format(DATE_FORMAT),
          anne: 1,
          mois: 1,
          montantPlafondSalaire: 1,
          montantPlafondCotisations: 1,
          montantPlafondSalairePlus: 1,
          montantPlafondCotisationsPlus: 1,
          nbHeureSalairePlafond: 1,
          tauxSalaire: 1,
          tauxCotisations: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateMensuelleDebutValidite: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StrategieApa', () => {
      const patchObject = Object.assign(
        {
          anne: 1,
          mois: 1,
          montantPlafondCotisations: 1,
          montantPlafondCotisationsPlus: 1,
          tauxSalaire: 1,
        },
        new StrategieApa()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateMensuelleDebutValidite: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StrategieApa', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isActif: true,
          dateMensuelleDebutValidite: currentDate.format(DATE_FORMAT),
          anne: 1,
          mois: 1,
          montantPlafondSalaire: 1,
          montantPlafondCotisations: 1,
          montantPlafondSalairePlus: 1,
          montantPlafondCotisationsPlus: 1,
          nbHeureSalairePlafond: 1,
          tauxSalaire: 1,
          tauxCotisations: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateMensuelleDebutValidite: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a StrategieApa', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStrategieApaToCollectionIfMissing', () => {
      it('should add a StrategieApa to an empty array', () => {
        const strategieApa: IStrategieApa = { id: 123 };
        expectedResult = service.addStrategieApaToCollectionIfMissing([], strategieApa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(strategieApa);
      });

      it('should not add a StrategieApa to an array that contains it', () => {
        const strategieApa: IStrategieApa = { id: 123 };
        const strategieApaCollection: IStrategieApa[] = [
          {
            ...strategieApa,
          },
          { id: 456 },
        ];
        expectedResult = service.addStrategieApaToCollectionIfMissing(strategieApaCollection, strategieApa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StrategieApa to an array that doesn't contain it", () => {
        const strategieApa: IStrategieApa = { id: 123 };
        const strategieApaCollection: IStrategieApa[] = [{ id: 456 }];
        expectedResult = service.addStrategieApaToCollectionIfMissing(strategieApaCollection, strategieApa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(strategieApa);
      });

      it('should add only unique StrategieApa to an array', () => {
        const strategieApaArray: IStrategieApa[] = [{ id: 123 }, { id: 456 }, { id: 44422 }];
        const strategieApaCollection: IStrategieApa[] = [{ id: 123 }];
        expectedResult = service.addStrategieApaToCollectionIfMissing(strategieApaCollection, ...strategieApaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const strategieApa: IStrategieApa = { id: 123 };
        const strategieApa2: IStrategieApa = { id: 456 };
        expectedResult = service.addStrategieApaToCollectionIfMissing([], strategieApa, strategieApa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(strategieApa);
        expect(expectedResult).toContain(strategieApa2);
      });

      it('should accept null and undefined values', () => {
        const strategieApa: IStrategieApa = { id: 123 };
        expectedResult = service.addStrategieApaToCollectionIfMissing([], null, strategieApa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(strategieApa);
      });

      it('should return initial array if no StrategieApa is added', () => {
        const strategieApaCollection: IStrategieApa[] = [{ id: 123 }];
        expectedResult = service.addStrategieApaToCollectionIfMissing(strategieApaCollection, undefined, null);
        expect(expectedResult).toEqual(strategieApaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
