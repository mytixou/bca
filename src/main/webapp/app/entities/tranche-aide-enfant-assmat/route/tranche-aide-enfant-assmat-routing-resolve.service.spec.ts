jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITrancheAideEnfantAssmat, TrancheAideEnfantAssmat } from '../tranche-aide-enfant-assmat.model';
import { TrancheAideEnfantAssmatService } from '../service/tranche-aide-enfant-assmat.service';

import { TrancheAideEnfantAssmatRoutingResolveService } from './tranche-aide-enfant-assmat-routing-resolve.service';

describe('TrancheAideEnfantAssmat routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TrancheAideEnfantAssmatRoutingResolveService;
  let service: TrancheAideEnfantAssmatService;
  let resultTrancheAideEnfantAssmat: ITrancheAideEnfantAssmat | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TrancheAideEnfantAssmatRoutingResolveService);
    service = TestBed.inject(TrancheAideEnfantAssmatService);
    resultTrancheAideEnfantAssmat = undefined;
  });

  describe('resolve', () => {
    it('should return ITrancheAideEnfantAssmat returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrancheAideEnfantAssmat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTrancheAideEnfantAssmat).toEqual({ id: 123 });
    });

    it('should return new ITrancheAideEnfantAssmat if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrancheAideEnfantAssmat = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTrancheAideEnfantAssmat).toEqual(new TrancheAideEnfantAssmat());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TrancheAideEnfantAssmat })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrancheAideEnfantAssmat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTrancheAideEnfantAssmat).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
