jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDroitsStrategiePch, DroitsStrategiePch } from '../droits-strategie-pch.model';
import { DroitsStrategiePchService } from '../service/droits-strategie-pch.service';

import { DroitsStrategiePchRoutingResolveService } from './droits-strategie-pch-routing-resolve.service';

describe('DroitsStrategiePch routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DroitsStrategiePchRoutingResolveService;
  let service: DroitsStrategiePchService;
  let resultDroitsStrategiePch: IDroitsStrategiePch | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DroitsStrategiePchRoutingResolveService);
    service = TestBed.inject(DroitsStrategiePchService);
    resultDroitsStrategiePch = undefined;
  });

  describe('resolve', () => {
    it('should return IDroitsStrategiePch returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategiePch = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitsStrategiePch).toEqual({ id: 123 });
    });

    it('should return new IDroitsStrategiePch if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategiePch = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDroitsStrategiePch).toEqual(new DroitsStrategiePch());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DroitsStrategiePch })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategiePch = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitsStrategiePch).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
