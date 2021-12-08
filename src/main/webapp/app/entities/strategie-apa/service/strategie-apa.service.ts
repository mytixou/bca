import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStrategieApa, getStrategieApaIdentifier } from '../strategie-apa.model';

export type EntityResponseType = HttpResponse<IStrategieApa>;
export type EntityArrayResponseType = HttpResponse<IStrategieApa[]>;

@Injectable({ providedIn: 'root' })
export class StrategieApaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/strategie-apas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(strategieApa: IStrategieApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieApa);
    return this.http
      .post<IStrategieApa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(strategieApa: IStrategieApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieApa);
    return this.http
      .put<IStrategieApa>(`${this.resourceUrl}/${getStrategieApaIdentifier(strategieApa) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(strategieApa: IStrategieApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieApa);
    return this.http
      .patch<IStrategieApa>(`${this.resourceUrl}/${getStrategieApaIdentifier(strategieApa) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStrategieApa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStrategieApa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStrategieApaToCollectionIfMissing(
    strategieApaCollection: IStrategieApa[],
    ...strategieApasToCheck: (IStrategieApa | null | undefined)[]
  ): IStrategieApa[] {
    const strategieApas: IStrategieApa[] = strategieApasToCheck.filter(isPresent);
    if (strategieApas.length > 0) {
      const strategieApaCollectionIdentifiers = strategieApaCollection.map(
        strategieApaItem => getStrategieApaIdentifier(strategieApaItem)!
      );
      const strategieApasToAdd = strategieApas.filter(strategieApaItem => {
        const strategieApaIdentifier = getStrategieApaIdentifier(strategieApaItem);
        if (strategieApaIdentifier == null || strategieApaCollectionIdentifiers.includes(strategieApaIdentifier)) {
          return false;
        }
        strategieApaCollectionIdentifiers.push(strategieApaIdentifier);
        return true;
      });
      return [...strategieApasToAdd, ...strategieApaCollection];
    }
    return strategieApaCollection;
  }

  protected convertDateFromClient(strategieApa: IStrategieApa): IStrategieApa {
    return Object.assign({}, strategieApa, {
      dateMensuelleDebutValidite: strategieApa.dateMensuelleDebutValidite?.isValid()
        ? strategieApa.dateMensuelleDebutValidite.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateMensuelleDebutValidite = res.body.dateMensuelleDebutValidite ? dayjs(res.body.dateMensuelleDebutValidite) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((strategieApa: IStrategieApa) => {
        strategieApa.dateMensuelleDebutValidite = strategieApa.dateMensuelleDebutValidite
          ? dayjs(strategieApa.dateMensuelleDebutValidite)
          : undefined;
      });
    }
    return res;
  }
}
