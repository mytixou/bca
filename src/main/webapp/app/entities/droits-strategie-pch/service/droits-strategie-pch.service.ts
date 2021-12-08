import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDroitsStrategiePch, getDroitsStrategiePchIdentifier } from '../droits-strategie-pch.model';

export type EntityResponseType = HttpResponse<IDroitsStrategiePch>;
export type EntityArrayResponseType = HttpResponse<IDroitsStrategiePch[]>;

@Injectable({ providedIn: 'root' })
export class DroitsStrategiePchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/droits-strategie-pches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(droitsStrategiePch: IDroitsStrategiePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategiePch);
    return this.http
      .post<IDroitsStrategiePch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(droitsStrategiePch: IDroitsStrategiePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategiePch);
    return this.http
      .put<IDroitsStrategiePch>(`${this.resourceUrl}/${getDroitsStrategiePchIdentifier(droitsStrategiePch) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(droitsStrategiePch: IDroitsStrategiePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategiePch);
    return this.http
      .patch<IDroitsStrategiePch>(`${this.resourceUrl}/${getDroitsStrategiePchIdentifier(droitsStrategiePch) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDroitsStrategiePch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDroitsStrategiePch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDroitsStrategiePchToCollectionIfMissing(
    droitsStrategiePchCollection: IDroitsStrategiePch[],
    ...droitsStrategiePchesToCheck: (IDroitsStrategiePch | null | undefined)[]
  ): IDroitsStrategiePch[] {
    const droitsStrategiePches: IDroitsStrategiePch[] = droitsStrategiePchesToCheck.filter(isPresent);
    if (droitsStrategiePches.length > 0) {
      const droitsStrategiePchCollectionIdentifiers = droitsStrategiePchCollection.map(
        droitsStrategiePchItem => getDroitsStrategiePchIdentifier(droitsStrategiePchItem)!
      );
      const droitsStrategiePchesToAdd = droitsStrategiePches.filter(droitsStrategiePchItem => {
        const droitsStrategiePchIdentifier = getDroitsStrategiePchIdentifier(droitsStrategiePchItem);
        if (droitsStrategiePchIdentifier == null || droitsStrategiePchCollectionIdentifiers.includes(droitsStrategiePchIdentifier)) {
          return false;
        }
        droitsStrategiePchCollectionIdentifiers.push(droitsStrategiePchIdentifier);
        return true;
      });
      return [...droitsStrategiePchesToAdd, ...droitsStrategiePchCollection];
    }
    return droitsStrategiePchCollection;
  }

  protected convertDateFromClient(droitsStrategiePch: IDroitsStrategiePch): IDroitsStrategiePch {
    return Object.assign({}, droitsStrategiePch, {
      dateOuverture: droitsStrategiePch.dateOuverture?.isValid() ? droitsStrategiePch.dateOuverture.format(DATE_FORMAT) : undefined,
      dateFermeture: droitsStrategiePch.dateFermeture?.isValid() ? droitsStrategiePch.dateFermeture.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((droitsStrategiePch: IDroitsStrategiePch) => {
        droitsStrategiePch.dateOuverture = droitsStrategiePch.dateOuverture ? dayjs(droitsStrategiePch.dateOuverture) : undefined;
        droitsStrategiePch.dateFermeture = droitsStrategiePch.dateFermeture ? dayjs(droitsStrategiePch.dateFermeture) : undefined;
      });
    }
    return res;
  }
}
