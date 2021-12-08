import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDroitAide, getDroitAideIdentifier } from '../droit-aide.model';

export type EntityResponseType = HttpResponse<IDroitAide>;
export type EntityArrayResponseType = HttpResponse<IDroitAide[]>;

@Injectable({ providedIn: 'root' })
export class DroitAideService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/droit-aides');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(droitAide: IDroitAide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitAide);
    return this.http
      .post<IDroitAide>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(droitAide: IDroitAide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitAide);
    return this.http
      .put<IDroitAide>(`${this.resourceUrl}/${getDroitAideIdentifier(droitAide) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(droitAide: IDroitAide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitAide);
    return this.http
      .patch<IDroitAide>(`${this.resourceUrl}/${getDroitAideIdentifier(droitAide) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDroitAide>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDroitAide[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDroitAideToCollectionIfMissing(
    droitAideCollection: IDroitAide[],
    ...droitAidesToCheck: (IDroitAide | null | undefined)[]
  ): IDroitAide[] {
    const droitAides: IDroitAide[] = droitAidesToCheck.filter(isPresent);
    if (droitAides.length > 0) {
      const droitAideCollectionIdentifiers = droitAideCollection.map(droitAideItem => getDroitAideIdentifier(droitAideItem)!);
      const droitAidesToAdd = droitAides.filter(droitAideItem => {
        const droitAideIdentifier = getDroitAideIdentifier(droitAideItem);
        if (droitAideIdentifier == null || droitAideCollectionIdentifiers.includes(droitAideIdentifier)) {
          return false;
        }
        droitAideCollectionIdentifiers.push(droitAideIdentifier);
        return true;
      });
      return [...droitAidesToAdd, ...droitAideCollection];
    }
    return droitAideCollection;
  }

  protected convertDateFromClient(droitAide: IDroitAide): IDroitAide {
    return Object.assign({}, droitAide, {
      dateOuverture: droitAide.dateOuverture?.isValid() ? droitAide.dateOuverture.format(DATE_FORMAT) : undefined,
      dateFermeture: droitAide.dateFermeture?.isValid() ? droitAide.dateFermeture.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((droitAide: IDroitAide) => {
        droitAide.dateOuverture = droitAide.dateOuverture ? dayjs(droitAide.dateOuverture) : undefined;
        droitAide.dateFermeture = droitAide.dateFermeture ? dayjs(droitAide.dateFermeture) : undefined;
      });
    }
    return res;
  }
}
