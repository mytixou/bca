jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDroitsStrategiePchE, DroitsStrategiePchE } from '../droits-strategie-pch-e.model';
import { DroitsStrategiePchEService } from '../service/droits-strategie-pch-e.service';

import { DroitsStrategiePchERoutingResolveService } from './droits-strategie-pch-e-routing-resolve.service';

describe('DroitsStrategiePchE routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DroitsStrategiePchERoutingResolveService;
  let service: DroitsStrategiePchEService;
  let resultDroitsStrategiePchE: IDroitsStrategiePchE | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DroitsStrategiePchERoutingResolveService);
    service = TestBed.inject(DroitsStrategiePchEService);
    resultDroitsStrategiePchE = undefined;
  });

  describe('resolve', () => {
    it('should return IDroitsStrategiePchE returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategiePchE = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitsStrategiePchE).toEqual({ id: 123 });
    });

    it('should return new IDroitsStrategiePchE if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategiePchE = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDroitsStrategiePchE).toEqual(new DroitsStrategiePchE());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DroitsStrategiePchE })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategiePchE = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitsStrategiePchE).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
