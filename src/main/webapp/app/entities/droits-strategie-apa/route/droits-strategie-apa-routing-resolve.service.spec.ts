jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDroitsStrategieApa, DroitsStrategieApa } from '../droits-strategie-apa.model';
import { DroitsStrategieApaService } from '../service/droits-strategie-apa.service';

import { DroitsStrategieApaRoutingResolveService } from './droits-strategie-apa-routing-resolve.service';

describe('DroitsStrategieApa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DroitsStrategieApaRoutingResolveService;
  let service: DroitsStrategieApaService;
  let resultDroitsStrategieApa: IDroitsStrategieApa | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(DroitsStrategieApaRoutingResolveService);
    service = TestBed.inject(DroitsStrategieApaService);
    resultDroitsStrategieApa = undefined;
  });

  describe('resolve', () => {
    it('should return IDroitsStrategieApa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategieApa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitsStrategieApa).toEqual({ id: 123 });
    });

    it('should return new IDroitsStrategieApa if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategieApa = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDroitsStrategieApa).toEqual(new DroitsStrategieApa());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DroitsStrategieApa })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDroitsStrategieApa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDroitsStrategieApa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
