import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDroitsStrategiePchE, getDroitsStrategiePchEIdentifier } from '../droits-strategie-pch-e.model';

export type EntityResponseType = HttpResponse<IDroitsStrategiePchE>;
export type EntityArrayResponseType = HttpResponse<IDroitsStrategiePchE[]>;

@Injectable({ providedIn: 'root' })
export class DroitsStrategiePchEService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/droits-strategie-pch-es');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(droitsStrategiePchE: IDroitsStrategiePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategiePchE);
    return this.http
      .post<IDroitsStrategiePchE>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(droitsStrategiePchE: IDroitsStrategiePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategiePchE);
    return this.http
      .put<IDroitsStrategiePchE>(`${this.resourceUrl}/${getDroitsStrategiePchEIdentifier(droitsStrategiePchE) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(droitsStrategiePchE: IDroitsStrategiePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategiePchE);
    return this.http
      .patch<IDroitsStrategiePchE>(`${this.resourceUrl}/${getDroitsStrategiePchEIdentifier(droitsStrategiePchE) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDroitsStrategiePchE>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDroitsStrategiePchE[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDroitsStrategiePchEToCollectionIfMissing(
    droitsStrategiePchECollection: IDroitsStrategiePchE[],
    ...droitsStrategiePchESToCheck: (IDroitsStrategiePchE | null | undefined)[]
  ): IDroitsStrategiePchE[] {
    const droitsStrategiePchES: IDroitsStrategiePchE[] = droitsStrategiePchESToCheck.filter(isPresent);
    if (droitsStrategiePchES.length > 0) {
      const droitsStrategiePchECollectionIdentifiers = droitsStrategiePchECollection.map(
        droitsStrategiePchEItem => getDroitsStrategiePchEIdentifier(droitsStrategiePchEItem)!
      );
      const droitsStrategiePchESToAdd = droitsStrategiePchES.filter(droitsStrategiePchEItem => {
        const droitsStrategiePchEIdentifier = getDroitsStrategiePchEIdentifier(droitsStrategiePchEItem);
        if (droitsStrategiePchEIdentifier == null || droitsStrategiePchECollectionIdentifiers.includes(droitsStrategiePchEIdentifier)) {
          return false;
        }
        droitsStrategiePchECollectionIdentifiers.push(droitsStrategiePchEIdentifier);
        return true;
      });
      return [...droitsStrategiePchESToAdd, ...droitsStrategiePchECollection];
    }
    return droitsStrategiePchECollection;
  }

  protected convertDateFromClient(droitsStrategiePchE: IDroitsStrategiePchE): IDroitsStrategiePchE {
    return Object.assign({}, droitsStrategiePchE, {
      dateOuverture: droitsStrategiePchE.dateOuverture?.isValid() ? droitsStrategiePchE.dateOuverture.format(DATE_FORMAT) : undefined,
      dateFermeture: droitsStrategiePchE.dateFermeture?.isValid() ? droitsStrategiePchE.dateFermeture.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOuverture = res.body.dateOuverture ? dayjs(res.body.dateOuverture) : undefined;
      res.body.dateFermeture = res.body.dateFermeture ? dayjs(res.body.dateFermeture) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((droitsStrategiePchE: IDroitsStrategiePchE) => {
        droitsStrategiePchE.dateOuverture = droitsStrategiePchE.dateOuverture ? dayjs(droitsStrategiePchE.dateOuverture) : undefined;
        droitsStrategiePchE.dateFermeture = droitsStrategiePchE.dateFermeture ? dayjs(droitsStrategiePchE.dateFermeture) : undefined;
      });
    }
    return res;
  }
}
