import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldePchE, getSoldePchEIdentifier } from '../solde-pch-e.model';

export type EntityResponseType = HttpResponse<ISoldePchE>;
export type EntityArrayResponseType = HttpResponse<ISoldePchE[]>;

@Injectable({ providedIn: 'root' })
export class SoldePchEService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-pch-es');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldePchE: ISoldePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldePchE);
    return this.http
      .post<ISoldePchE>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldePchE: ISoldePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldePchE);
    return this.http
      .put<ISoldePchE>(`${this.resourceUrl}/${getSoldePchEIdentifier(soldePchE) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldePchE: ISoldePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldePchE);
    return this.http
      .patch<ISoldePchE>(`${this.resourceUrl}/${getSoldePchEIdentifier(soldePchE) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldePchE>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldePchE[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldePchEToCollectionIfMissing(
    soldePchECollection: ISoldePchE[],
    ...soldePchESToCheck: (ISoldePchE | null | undefined)[]
  ): ISoldePchE[] {
    const soldePchES: ISoldePchE[] = soldePchESToCheck.filter(isPresent);
    if (soldePchES.length > 0) {
      const soldePchECollectionIdentifiers = soldePchECollection.map(soldePchEItem => getSoldePchEIdentifier(soldePchEItem)!);
      const soldePchESToAdd = soldePchES.filter(soldePchEItem => {
        const soldePchEIdentifier = getSoldePchEIdentifier(soldePchEItem);
        if (soldePchEIdentifier == null || soldePchECollectionIdentifiers.includes(soldePchEIdentifier)) {
          return false;
        }
        soldePchECollectionIdentifiers.push(soldePchEIdentifier);
        return true;
      });
      return [...soldePchESToAdd, ...soldePchECollection];
    }
    return soldePchECollection;
  }

  protected convertDateFromClient(soldePchE: ISoldePchE): ISoldePchE {
    return Object.assign({}, soldePchE, {
      date: soldePchE.date?.isValid() ? soldePchE.date.toJSON() : undefined,
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
      res.body.forEach((soldePchE: ISoldePchE) => {
        soldePchE.date = soldePchE.date ? dayjs(soldePchE.date) : undefined;
      });
    }
    return res;
  }
}
