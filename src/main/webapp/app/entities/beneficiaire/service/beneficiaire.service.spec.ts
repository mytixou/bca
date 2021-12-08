import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IBeneficiaire, Beneficiaire } from '../beneficiaire.model';

import { BeneficiaireService } from './beneficiaire.service';

describe('Beneficiaire Service', () => {
  let service: BeneficiaireService;
  let httpMock: HttpTestingController;
  let elemDefault: IBeneficiaire;
  let expectedResult: IBeneficiaire | IBeneficiaire[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BeneficiaireService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      externeId: 'AAAAAAA',
      isActif: false,
      dateDesactivation: currentDate,
      isInscrit: false,
      dateInscription: currentDate,
      dateResiliation: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateDesactivation: currentDate.format(DATE_FORMAT),
          dateInscription: currentDate.format(DATE_FORMAT),
          dateResiliation: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Beneficiaire', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateDesactivation: currentDate.format(DATE_FORMAT),
          dateInscription: currentDate.format(DATE_FORMAT),
          dateResiliation: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDesactivation: currentDate,
          dateInscription: currentDate,
          dateResiliation: currentDate,
        },
        returnedFromService
      );

      service.create(new Beneficiaire()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Beneficiaire', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          externeId: 'BBBBBB',
          isActif: true,
          dateDesactivation: currentDate.format(DATE_FORMAT),
          isInscrit: true,
          dateInscription: currentDate.format(DATE_FORMAT),
          dateResiliation: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDesactivation: currentDate,
          dateInscription: currentDate,
          dateResiliation: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Beneficiaire', () => {
      const patchObject = Object.assign(
        {
          dateInscription: currentDate.format(DATE_FORMAT),
        },
        new Beneficiaire()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateDesactivation: currentDate,
          dateInscription: currentDate,
          dateResiliation: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Beneficiaire', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          externeId: 'BBBBBB',
          isActif: true,
          dateDesactivation: currentDate.format(DATE_FORMAT),
          isInscrit: true,
          dateInscription: currentDate.format(DATE_FORMAT),
          dateResiliation: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateDesactivation: currentDate,
          dateInscription: currentDate,
          dateResiliation: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Beneficiaire', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBeneficiaireToCollectionIfMissing', () => {
      it('should add a Beneficiaire to an empty array', () => {
        const beneficiaire: IBeneficiaire = { id: 123 };
        expectedResult = service.addBeneficiaireToCollectionIfMissing([], beneficiaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(beneficiaire);
      });

      it('should not add a Beneficiaire to an array that contains it', () => {
        const beneficiaire: IBeneficiaire = { id: 123 };
        const beneficiaireCollection: IBeneficiaire[] = [
          {
            ...beneficiaire,
          },
          { id: 456 },
        ];
        expectedResult = service.addBeneficiaireToCollectionIfMissing(beneficiaireCollection, beneficiaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Beneficiaire to an array that doesn't contain it", () => {
        const beneficiaire: IBeneficiaire = { id: 123 };
        const beneficiaireCollection: IBeneficiaire[] = [{ id: 456 }];
        expectedResult = service.addBeneficiaireToCollectionIfMissing(beneficiaireCollection, beneficiaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(beneficiaire);
      });

      it('should add only unique Beneficiaire to an array', () => {
        const beneficiaireArray: IBeneficiaire[] = [{ id: 123 }, { id: 456 }, { id: 69547 }];
        const beneficiaireCollection: IBeneficiaire[] = [{ id: 123 }];
        expectedResult = service.addBeneficiaireToCollectionIfMissing(beneficiaireCollection, ...beneficiaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const beneficiaire: IBeneficiaire = { id: 123 };
        const beneficiaire2: IBeneficiaire = { id: 456 };
        expectedResult = service.addBeneficiaireToCollectionIfMissing([], beneficiaire, beneficiaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(beneficiaire);
        expect(expectedResult).toContain(beneficiaire2);
      });

      it('should accept null and undefined values', () => {
        const beneficiaire: IBeneficiaire = { id: 123 };
        expectedResult = service.addBeneficiaireToCollectionIfMissing([], null, beneficiaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(beneficiaire);
      });

      it('should return initial array if no Beneficiaire is added', () => {
        const beneficiaireCollection: IBeneficiaire[] = [{ id: 123 }];
        expectedResult = service.addBeneficiaireToCollectionIfMissing(beneficiaireCollection, undefined, null);
        expect(expectedResult).toEqual(beneficiaireCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
