import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPec, getPecIdentifier } from '../pec.model';

export type EntityResponseType = HttpResponse<IPec>;
export type EntityArrayResponseType = HttpResponse<IPec[]>;

@Injectable({ providedIn: 'root' })
export class PecService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pecs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pec: IPec): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pec);
    return this.http
      .post<IPec>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pec: IPec): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pec);
    return this.http
      .put<IPec>(`${this.resourceUrl}/${getPecIdentifier(pec) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pec: IPec): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pec);
    return this.http
      .patch<IPec>(`${this.resourceUrl}/${getPecIdentifier(pec) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IPec>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPec[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPecToCollectionIfMissing(pecCollection: IPec[], ...pecsToCheck: (IPec | null | undefined)[]): IPec[] {
    const pecs: IPec[] = pecsToCheck.filter(isPresent);
    if (pecs.length > 0) {
      const pecCollectionIdentifiers = pecCollection.map(pecItem => getPecIdentifier(pecItem)!);
      const pecsToAdd = pecs.filter(pecItem => {
        const pecIdentifier = getPecIdentifier(pecItem);
        if (pecIdentifier == null || pecCollectionIdentifiers.includes(pecIdentifier)) {
          return false;
        }
        pecCollectionIdentifiers.push(pecIdentifier);
        return true;
      });
      return [...pecsToAdd, ...pecCollection];
    }
    return pecCollection;
  }

  protected convertDateFromClient(pec: IPec): IPec {
    return Object.assign({}, pec, {
      dateCreated: pec.dateCreated?.isValid() ? pec.dateCreated.toJSON() : undefined,
      dateModified: pec.dateModified?.isValid() ? pec.dateModified.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? dayjs(res.body.dateCreated) : undefined;
      res.body.dateModified = res.body.dateModified ? dayjs(res.body.dateModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pec: IPec) => {
        pec.dateCreated = pec.dateCreated ? dayjs(pec.dateCreated) : undefined;
        pec.dateModified = pec.dateModified ? dayjs(pec.dateModified) : undefined;
      });
    }
    return res;
  }
}
