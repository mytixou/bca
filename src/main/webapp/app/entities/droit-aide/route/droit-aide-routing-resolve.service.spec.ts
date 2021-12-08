jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDroitAide, DroitAide } from '../droit-aide.model';
import { DroitAideService } from '../service/droit-aide.service';

import { DroitAideRoutingResolveService } from './droit-aide-routing-resolve.service';

describe('DroitAide routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DroitAideRoutingResolveService;
  let service: DroitAideService;
  let resultDroitAide: IDroitAide | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DroitAideRoutingResolveService);
    service = TestBed.inject(DroitAideService);
    resultDroitAide = undefined;
  });

  describe('resolve', () => {
    it('should return IDroitAide returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitAide = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitAide).toEqual({ id: 123 });
    });

    it('should return new IDroitAide if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitAide = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDroitAide).toEqual(new DroitAide());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DroitAide })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitAide = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitAide).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
