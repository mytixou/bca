import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldePch, getSoldePchIdentifier } from '../solde-pch.model';

export type EntityResponseType = HttpResponse<ISoldePch>;
export type EntityArrayResponseType = HttpResponse<ISoldePch[]>;

@Injectable({ providedIn: 'root' })
export class SoldePchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-pches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldePch: ISoldePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldePch);
    return this.http
      .post<ISoldePch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldePch: ISoldePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldePch);
    return this.http
      .put<ISoldePch>(`${this.resourceUrl}/${getSoldePchIdentifier(soldePch) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldePch: ISoldePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldePch);
    return this.http
      .patch<ISoldePch>(`${this.resourceUrl}/${getSoldePchIdentifier(soldePch) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldePch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldePch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldePchToCollectionIfMissing(soldePchCollection: ISoldePch[], ...soldePchesToCheck: (ISoldePch | null | undefined)[]): ISoldePch[] {
    const soldePches: ISoldePch[] = soldePchesToCheck.filter(isPresent);
    if (soldePches.length > 0) {
      const soldePchCollectionIdentifiers = soldePchCollection.map(soldePchItem => getSoldePchIdentifier(soldePchItem)!);
      const soldePchesToAdd = soldePches.filter(soldePchItem => {
        const soldePchIdentifier = getSoldePchIdentifier(soldePchItem);
        if (soldePchIdentifier == null || soldePchCollectionIdentifiers.includes(soldePchIdentifier)) {
          return false;
        }
        soldePchCollectionIdentifiers.push(soldePchIdentifier);
        return true;
      });
      return [...soldePchesToAdd, ...soldePchCollection];
    }
    return soldePchCollection;
  }

  protected convertDateFromClient(soldePch: ISoldePch): ISoldePch {
    return Object.assign({}, soldePch, {
      date: soldePch.date?.isValid() ? soldePch.date.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((soldePch: ISoldePch) => {
        soldePch.date = soldePch.date ? dayjs(soldePch.date) : undefined;
      });
    }
    return res;
  }
}
