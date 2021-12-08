import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrancheAideEnfantGed, getTrancheAideEnfantGedIdentifier } from '../tranche-aide-enfant-ged.model';

export type EntityResponseType = HttpResponse<ITrancheAideEnfantGed>;
export type EntityArrayResponseType = HttpResponse<ITrancheAideEnfantGed[]>;

@Injectable({ providedIn: 'root' })
export class TrancheAideEnfantGedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tranche-aide-enfant-geds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trancheAideEnfantGed: ITrancheAideEnfantGed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trancheAideEnfantGed);
    return this.http
      .post<ITrancheAideEnfantGed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(trancheAideEnfantGed: ITrancheAideEnfantGed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trancheAideEnfantGed);
    return this.http
      .put<ITrancheAideEnfantGed>(`${this.resourceUrl}/${getTrancheAideEnfantGedIdentifier(trancheAideEnfantGed) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(trancheAideEnfantGed: ITrancheAideEnfantGed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trancheAideEnfantGed);
    return this.http
      .patch<ITrancheAideEnfantGed>(`${this.resourceUrl}/${getTrancheAideEnfantGedIdentifier(trancheAideEnfantGed) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITrancheAideEnfantGed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITrancheAideEnfantGed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTrancheAideEnfantGedToCollectionIfMissing(
    trancheAideEnfantGedCollection: ITrancheAideEnfantGed[],
    ...trancheAideEnfantGedsToCheck: (ITrancheAideEnfantGed | null | undefined)[]
  ): ITrancheAideEnfantGed[] {
    const trancheAideEnfantGeds: ITrancheAideEnfantGed[] = trancheAideEnfantGedsToCheck.filter(isPresent);
    if (trancheAideEnfantGeds.length > 0) {
      const trancheAideEnfantGedCollectionIdentifiers = trancheAideEnfantGedCollection.map(
        trancheAideEnfantGedItem => getTrancheAideEnfantGedIdentifier(trancheAideEnfantGedItem)!
      );
      const trancheAideEnfantGedsToAdd = trancheAideEnfantGeds.filter(trancheAideEnfantGedItem => {
        const trancheAideEnfantGedIdentifier = getTrancheAideEnfantGedIdentifier(trancheAideEnfantGedItem);
        if (trancheAideEnfantGedIdentifier == null || trancheAideEnfantGedCollectionIdentifiers.includes(trancheAideEnfantGedIdentifier)) {
          return false;
        }
        trancheAideEnfantGedCollectionIdentifiers.push(trancheAideEnfantGedIdentifier);
        return true;
      });
      return [...trancheAideEnfantGedsToAdd, ...trancheAideEnfantGedCollection];
    }
    return trancheAideEnfantGedCollection;
  }

  protected convertDateFromClient(trancheAideEnfantGed: ITrancheAideEnfantGed): ITrancheAideEnfantGed {
    return Object.assign({}, trancheAideEnfantGed, {
      dateCreated: trancheAideEnfantGed.dateCreated?.isValid() ? trancheAideEnfantGed.dateCreated.toJSON() : undefined,
      dateModified: trancheAideEnfantGed.dateModified?.isValid() ? trancheAideEnfantGed.dateModified.toJSON() : undefined,
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
      res.body.forEach((trancheAideEnfantGed: ITrancheAideEnfantGed) => {
        trancheAideEnfantGed.dateCreated = trancheAideEnfantGed.dateCreated ? dayjs(trancheAideEnfantGed.dateCreated) : undefined;
        trancheAideEnfantGed.dateModified = trancheAideEnfantGed.dateModified ? dayjs(trancheAideEnfantGed.dateModified) : undefined;
      });
    }
    return res;
  }
}
