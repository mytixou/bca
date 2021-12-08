jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IStrategieCmgGed, StrategieCmgGed } from '../strategie-cmg-ged.model';
import { StrategieCmgGedService } from '../service/strategie-cmg-ged.service';

import { StrategieCmgGedRoutingResolveService } from './strategie-cmg-ged-routing-resolve.service';

describe('StrategieCmgGed routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StrategieCmgGedRoutingResolveService;
  let service: StrategieCmgGedService;
  let resultStrategieCmgGed: IStrategieCmgGed | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(StrategieCmgGedRoutingResolveService);
    service = TestBed.inject(StrategieCmgGedService);
    resultStrategieCmgGed = undefined;
  });

  describe('resolve', () => {
    it('should return IStrategieCmgGed returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStrategieCmgGed = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStrategieCmgGed).toEqual({ id: 123 });
    });

    it('should return new IStrategieCmgGed if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStrategieCmgGed = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStrategieCmgGed).toEqual(new StrategieCmgGed());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as StrategieCmgGed })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStrategieCmgGed = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStrategieCmgGed).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
