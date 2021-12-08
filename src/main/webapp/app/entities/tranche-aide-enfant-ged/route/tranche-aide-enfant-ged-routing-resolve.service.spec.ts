jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITrancheAideEnfantGed, TrancheAideEnfantGed } from '../tranche-aide-enfant-ged.model';
import { TrancheAideEnfantGedService } from '../service/tranche-aide-enfant-ged.service';

import { TrancheAideEnfantGedRoutingResolveService } from './tranche-aide-enfant-ged-routing-resolve.service';

describe('TrancheAideEnfantGed routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TrancheAideEnfantGedRoutingResolveService;
  let service: TrancheAideEnfantGedService;
  let resultTrancheAideEnfantGed: ITrancheAideEnfantGed | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TrancheAideEnfantGedRoutingResolveService);
    service = TestBed.inject(TrancheAideEnfantGedService);
    resultTrancheAideEnfantGed = undefined;
  });

  describe('resolve', () => {
    it('should return ITrancheAideEnfantGed returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrancheAideEnfantGed = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTrancheAideEnfantGed).toEqual({ id: 123 });
    });

    it('should return new ITrancheAideEnfantGed if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrancheAideEnfantGed = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTrancheAideEnfantGed).toEqual(new TrancheAideEnfantGed());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TrancheAideEnfantGed })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrancheAideEnfantGed = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTrancheAideEnfantGed).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
