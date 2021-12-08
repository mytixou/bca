import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldeCi, getSoldeCiIdentifier } from '../solde-ci.model';

export type EntityResponseType = HttpResponse<ISoldeCi>;
export type EntityArrayResponseType = HttpResponse<ISoldeCi[]>;

@Injectable({ providedIn: 'root' })
export class SoldeCiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-cis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldeCi: ISoldeCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeCi);
    return this.http
      .post<ISoldeCi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldeCi: ISoldeCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeCi);
    return this.http
      .put<ISoldeCi>(`${this.resourceUrl}/${getSoldeCiIdentifier(soldeCi) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldeCi: ISoldeCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeCi);
    return this.http
      .patch<ISoldeCi>(`${this.resourceUrl}/${getSoldeCiIdentifier(soldeCi) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldeCi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldeCi[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldeCiToCollectionIfMissing(soldeCiCollection: ISoldeCi[], ...soldeCisToCheck: (ISoldeCi | null | undefined)[]): ISoldeCi[] {
    const soldeCis: ISoldeCi[] = soldeCisToCheck.filter(isPresent);
    if (soldeCis.length > 0) {
      const soldeCiCollectionIdentifiers = soldeCiCollection.map(soldeCiItem => getSoldeCiIdentifier(soldeCiItem)!);
      const soldeCisToAdd = soldeCis.filter(soldeCiItem => {
        const soldeCiIdentifier = getSoldeCiIdentifier(soldeCiItem);
        if (soldeCiIdentifier == null || soldeCiCollectionIdentifiers.includes(soldeCiIdentifier)) {
          return false;
        }
        soldeCiCollectionIdentifiers.push(soldeCiIdentifier);
        return true;
      });
      return [...soldeCisToAdd, ...soldeCiCollection];
    }
    return soldeCiCollection;
  }

  protected convertDateFromClient(soldeCi: ISoldeCi): ISoldeCi {
    return Object.assign({}, soldeCi, {
      date: soldeCi.date?.isValid() ? soldeCi.date.toJSON() : undefined,
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
      res.body.forEach((soldeCi: ISoldeCi) => {
        soldeCi.date = soldeCi.date ? dayjs(soldeCi.date) : undefined;
      });
    }
    return res;
  }
}
