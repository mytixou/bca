import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldeApa, getSoldeApaIdentifier } from '../solde-apa.model';

export type EntityResponseType = HttpResponse<ISoldeApa>;
export type EntityArrayResponseType = HttpResponse<ISoldeApa[]>;

@Injectable({ providedIn: 'root' })
export class SoldeApaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-apas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldeApa: ISoldeApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeApa);
    return this.http
      .post<ISoldeApa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldeApa: ISoldeApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeApa);
    return this.http
      .put<ISoldeApa>(`${this.resourceUrl}/${getSoldeApaIdentifier(soldeApa) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldeApa: ISoldeApa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeApa);
    return this.http
      .patch<ISoldeApa>(`${this.resourceUrl}/${getSoldeApaIdentifier(soldeApa) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldeApa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldeApa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldeApaToCollectionIfMissing(soldeApaCollection: ISoldeApa[], ...soldeApasToCheck: (ISoldeApa | null | undefined)[]): ISoldeApa[] {
    const soldeApas: ISoldeApa[] = soldeApasToCheck.filter(isPresent);
    if (soldeApas.length > 0) {
      const soldeApaCollectionIdentifiers = soldeApaCollection.map(soldeApaItem => getSoldeApaIdentifier(soldeApaItem)!);
      const soldeApasToAdd = soldeApas.filter(soldeApaItem => {
        const soldeApaIdentifier = getSoldeApaIdentifier(soldeApaItem);
        if (soldeApaIdentifier == null || soldeApaCollectionIdentifiers.includes(soldeApaIdentifier)) {
          return false;
        }
        soldeApaCollectionIdentifiers.push(soldeApaIdentifier);
        return true;
      });
      return [...soldeApasToAdd, ...soldeApaCollection];
    }
    return soldeApaCollection;
  }

  protected convertDateFromClient(soldeApa: ISoldeApa): ISoldeApa {
    return Object.assign({}, soldeApa, {
      date: soldeApa.date?.isValid() ? soldeApa.date.toJSON() : undefined,
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
      res.body.forEach((soldeApa: ISoldeApa) => {
        soldeApa.date = soldeApa.date ? dayjs(soldeApa.date) : undefined;
      });
    }
    return res;
  }
}
