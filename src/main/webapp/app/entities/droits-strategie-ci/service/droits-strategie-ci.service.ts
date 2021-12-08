import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDroitsStrategieCi, getDroitsStrategieCiIdentifier } from '../droits-strategie-ci.model';

export type EntityResponseType = HttpResponse<IDroitsStrategieCi>;
export type EntityArrayResponseType = HttpResponse<IDroitsStrategieCi[]>;

@Injectable({ providedIn: 'root' })
export class DroitsStrategieCiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/droits-strategie-cis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(droitsStrategieCi: IDroitsStrategieCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategieCi);
    return this.http
      .post<IDroitsStrategieCi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(droitsStrategieCi: IDroitsStrategieCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategieCi);
    return this.http
      .put<IDroitsStrategieCi>(`${this.resourceUrl}/${getDroitsStrategieCiIdentifier(droitsStrategieCi) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(droitsStrategieCi: IDroitsStrategieCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategieCi);
    return this.http
      .patch<IDroitsStrategieCi>(`${this.resourceUrl}/${getDroitsStrategieCiIdentifier(droitsStrategieCi) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDroitsStrategieCi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDroitsStrategieCi[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDroitsStrategieCiToCollectionIfMissing(
    droitsStrategieCiCollection: IDroitsStrategieCi[],
    ...droitsStrategieCisToCheck: (IDroitsStrategieCi | null | undefined)[]
  ): IDroitsStrategieCi[] {
    const droitsStrategieCis: IDroitsStrategieCi[] = droitsStrategieCisToCheck.filter(isPresent);
    if (droitsStrategieCis.length > 0) {
      const droitsStrategieCiCollectionIdentifiers = droitsStrategieCiCollection.map(
        droitsStrategieCiItem => getDroitsStrategieCiIdentifier(droitsStrategieCiItem)!
      );
      const droitsStrategieCisToAdd = droitsStrategieCis.filter(droitsStrategieCiItem => {
        const droitsStrategieCiIdentifier = getDroitsStrategieCiIdentifier(droitsStrategieCiItem);
        if (droitsStrategieCiIdentifier == null || droitsStrategieCiCollectionIdentifiers.includes(droitsStrategieCiIdentifier)) {
          return false;
        }
        droitsStrategieCiCollectionIdentifiers.push(droitsStrategieCiIdentifier);
        return true;
      });
      return [...droitsStrategieCisToAdd, ...droitsStrategieCiCollection];
    }
    return droitsStrategieCiCollection;
  }

  protected convertDateFromClient(droitsStrategieCi: IDroitsStrategieCi): IDroitsStrategieCi {
    return Object.assign({}, droitsStrategieCi, {
      dateOuverture: droitsStrategieCi.dateOuverture?.isValid() ? droitsStrategieCi.dateOuverture.format(DATE_FORMAT) : undefined,
      dateFermeture: droitsStrategieCi.dateFermeture?.isValid() ? droitsStrategieCi.dateFermeture.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((droitsStrategieCi: IDroitsStrategieCi) => {
        droitsStrategieCi.dateOuverture = droitsStrategieCi.dateOuverture ? dayjs(droitsStrategieCi.dateOuverture) : undefined;
        droitsStrategieCi.dateFermeture = droitsStrategieCi.dateFermeture ? dayjs(droitsStrategieCi.dateFermeture) : undefined;
      });
    }
    return res;
  }
}
