import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeProduit } from 'app/entities/enumerations/type-produit.model';
import { IPec, Pec } from '../pec.model';

import { PecService } from './pec.service';

describe('Pec Service', () => {
  let service: PecService;
  let httpMock: HttpTestingController;
  let elemDefault: IPec;
  let expectedResult: IPec | IPec[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PecService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      idProduit: 'AAAAAAA',
      produit: TypeProduit.CESU,
      isPlus: false,
      dateCreated: currentDate,
      isUpdated: false,
      dateModified: currentDate,
      isActif: false,
      pecDetails: 'AAAAAAA',
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

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Pec', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
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

      service.create(new Pec()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pec', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          idProduit: 'BBBBBB',
          produit: 'BBBBBB',
          isPlus: true,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          isUpdated: true,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
          isActif: true,
          pecDetails: 'BBBBBB',
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

    it('should partial update a Pec', () => {
      const patchObject = Object.assign(
        {
          idProduit: 'BBBBBB',
          isActif: true,
          pecDetails: 'BBBBBB',
        },
        new Pec()
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

    it('should return a list of Pec', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          idProduit: 'BBBBBB',
          produit: 'BBBBBB',
          isPlus: true,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          isUpdated: true,
          dateModified: currentDate.format(DATE_TIME_FORMAT),
          isActif: true,
          pecDetails: 'BBBBBB',
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

    it('should delete a Pec', () => {
      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPecToCollectionIfMissing', () => {
      it('should add a Pec to an empty array', () => {
        const pec: IPec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        expectedResult = service.addPecToCollectionIfMissing([], pec);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pec);
      });

      it('should not add a Pec to an array that contains it', () => {
        const pec: IPec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const pecCollection: IPec[] = [
          {
            ...pec,
          },
          { id: '1361f429-3817-4123-8ee3-fdf8943310b2' },
        ];
        expectedResult = service.addPecToCollectionIfMissing(pecCollection, pec);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pec to an array that doesn't contain it", () => {
        const pec: IPec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const pecCollection: IPec[] = [{ id: '1361f429-3817-4123-8ee3-fdf8943310b2' }];
        expectedResult = service.addPecToCollectionIfMissing(pecCollection, pec);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pec);
      });

      it('should add only unique Pec to an array', () => {
        const pecArray: IPec[] = [
          { id: '9fec3727-3421-4967-b213-ba36557ca194' },
          { id: '1361f429-3817-4123-8ee3-fdf8943310b2' },
          { id: '5ad1a00f-0562-48f9-8b3d-21cb227fb9db' },
        ];
        const pecCollection: IPec[] = [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }];
        expectedResult = service.addPecToCollectionIfMissing(pecCollection, ...pecArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pec: IPec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const pec2: IPec = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        expectedResult = service.addPecToCollectionIfMissing([], pec, pec2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pec);
        expect(expectedResult).toContain(pec2);
      });

      it('should accept null and undefined values', () => {
        const pec: IPec = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        expectedResult = service.addPecToCollectionIfMissing([], null, pec, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pec);
      });

      it('should return initial array if no Pec is added', () => {
        const pecCollection: IPec[] = [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }];
        expectedResult = service.addPecToCollectionIfMissing(pecCollection, undefined, null);
        expect(expectedResult).toEqual(pecCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
