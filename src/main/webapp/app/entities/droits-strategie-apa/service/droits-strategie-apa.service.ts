import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDroitsStrategieApa, getDroitsStrategieApaIdentifier } from '../droits-strategie-apa.model';

export type EntityResponseType = HttpResponse<IDroitsStrategieApa>;
export type EntityArrayResponseType = HttpResponse<IDroitsStrategieApa[]>;

@Injectable({ providedIn: 'root' })
export class DroitsStrategieApaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/droits-strategie-apas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(droitsStrategieApa: IDroitsStrategieApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategieApa);
    return this.http
      .post<IDroitsStrategieApa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(droitsStrategieApa: IDroitsStrategieApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategieApa);
    return this.http
      .put<IDroitsStrategieApa>(`${this.resourceUrl}/${getDroitsStrategieApaIdentifier(droitsStrategieApa) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(droitsStrategieApa: IDroitsStrategieApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(droitsStrategieApa);
    return this.http
      .patch<IDroitsStrategieApa>(`${this.resourceUrl}/${getDroitsStrategieApaIdentifier(droitsStrategieApa) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDroitsStrategieApa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDroitsStrategieApa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDroitsStrategieApaToCollectionIfMissing(
    droitsStrategieApaCollection: IDroitsStrategieApa[],
    ...droitsStrategieApasToCheck: (IDroitsStrategieApa | null | undefined)[]
  ): IDroitsStrategieApa[] {
    const droitsStrategieApas: IDroitsStrategieApa[] = droitsStrategieApasToCheck.filter(isPresent);
    if (droitsStrategieApas.length > 0) {
      const droitsStrategieApaCollectionIdentifiers = droitsStrategieApaCollection.map(
        droitsStrategieApaItem => getDroitsStrategieApaIdentifier(droitsStrategieApaItem)!
      );
      const droitsStrategieApasToAdd = droitsStrategieApas.filter(droitsStrategieApaItem => {
        const droitsStrategieApaIdentifier = getDroitsStrategieApaIdentifier(droitsStrategieApaItem);
        if (droitsStrategieApaIdentifier == null || droitsStrategieApaCollectionIdentifiers.includes(droitsStrategieApaIdentifier)) {
          return false;
        }
        droitsStrategieApaCollectionIdentifiers.push(droitsStrategieApaIdentifier);
        return true;
      });
      return [...droitsStrategieApasToAdd, ...droitsStrategieApaCollection];
    }
    return droitsStrategieApaCollection;
  }

  protected convertDateFromClient(droitsStrategieApa: IDroitsStrategieApa): IDroitsStrategieApa {
    return Object.assign({}, droitsStrategieApa, {
      dateOuverture: droitsStrategieApa.dateOuverture?.isValid() ? droitsStrategieApa.dateOuverture.format(DATE_FORMAT) : undefined,
      dateFermeture: droitsStrategieApa.dateFermeture?.isValid() ? droitsStrategieApa.dateFermeture.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((droitsStrategieApa: IDroitsStrategieApa) => {
        droitsStrategieApa.dateOuverture = droitsStrategieApa.dateOuverture ? dayjs(droitsStrategieApa.dateOuverture) : undefined;
        droitsStrategieApa.dateFermeture = droitsStrategieApa.dateFermeture ? dayjs(droitsStrategieApa.dateFermeture) : undefined;
      });
    }
    return res;
  }
}
