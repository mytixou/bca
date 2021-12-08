jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IStrategieCmgAssmat, StrategieCmgAssmat } from '../strategie-cmg-assmat.model';
import { StrategieCmgAssmatService } from '../service/strategie-cmg-assmat.service';

import { StrategieCmgAssmatRoutingResolveService } from './strategie-cmg-assmat-routing-resolve.service';

describe('StrategieCmgAssmat routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StrategieCmgAssmatRoutingResolveService;
  let service: StrategieCmgAssmatService;
  let resultStrategieCmgAssmat: IStrategieCmgAssmat | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(StrategieCmgAssmatRoutingResolveService);
    service = TestBed.inject(StrategieCmgAssmatService);
    resultStrategieCmgAssmat = undefined;
  });

  describe('resolve', () => {
    it('should return IStrategieCmgAssmat returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStrategieCmgAssmat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStrategieCmgAssmat).toEqual({ id: 123 });
    });

    it('should return new IStrategieCmgAssmat if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStrategieCmgAssmat = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStrategieCmgAssmat).toEqual(new StrategieCmgAssmat());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as StrategieCmgAssmat })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStrategieCmgAssmat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStrategieCmgAssmat).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
