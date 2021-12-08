import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStrategieCmgGed, getStrategieCmgGedIdentifier } from '../strategie-cmg-ged.model';

export type EntityResponseType = HttpResponse<IStrategieCmgGed>;
export type EntityArrayResponseType = HttpResponse<IStrategieCmgGed[]>;

@Injectable({ providedIn: 'root' })
export class StrategieCmgGedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/strategie-cmg-geds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(strategieCmgGed: IStrategieCmgGed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCmgGed);
    return this.http
      .post<IStrategieCmgGed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(strategieCmgGed: IStrategieCmgGed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCmgGed);
    return this.http
      .put<IStrategieCmgGed>(`${this.resourceUrl}/${getStrategieCmgGedIdentifier(strategieCmgGed) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(strategieCmgGed: IStrategieCmgGed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCmgGed);
    return this.http
      .patch<IStrategieCmgGed>(`${this.resourceUrl}/${getStrategieCmgGedIdentifier(strategieCmgGed) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStrategieCmgGed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStrategieCmgGed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStrategieCmgGedToCollectionIfMissing(
    strategieCmgGedCollection: IStrategieCmgGed[],
    ...strategieCmgGedsToCheck: (IStrategieCmgGed | null | undefined)[]
  ): IStrategieCmgGed[] {
    const strategieCmgGeds: IStrategieCmgGed[] = strategieCmgGedsToCheck.filter(isPresent);
    if (strategieCmgGeds.length > 0) {
      const strategieCmgGedCollectionIdentifiers = strategieCmgGedCollection.map(
        strategieCmgGedItem => getStrategieCmgGedIdentifier(strategieCmgGedItem)!
      );
      const strategieCmgGedsToAdd = strategieCmgGeds.filter(strategieCmgGedItem => {
        const strategieCmgGedIdentifier = getStrategieCmgGedIdentifier(strategieCmgGedItem);
        if (strategieCmgGedIdentifier == null || strategieCmgGedCollectionIdentifiers.includes(strategieCmgGedIdentifier)) {
          return false;
        }
        strategieCmgGedCollectionIdentifiers.push(strategieCmgGedIdentifier);
        return true;
      });
      return [...strategieCmgGedsToAdd, ...strategieCmgGedCollection];
    }
    return strategieCmgGedCollection;
  }

  protected convertDateFromClient(strategieCmgGed: IStrategieCmgGed): IStrategieCmgGed {
    return Object.assign({}, strategieCmgGed, {
      dateCreated: strategieCmgGed.dateCreated?.isValid() ? strategieCmgGed.dateCreated.toJSON() : undefined,
      dateModified: strategieCmgGed.dateModified?.isValid() ? strategieCmgGed.dateModified.toJSON() : undefined,
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
      res.body.forEach((strategieCmgGed: IStrategieCmgGed) => {
        strategieCmgGed.dateCreated = strategieCmgGed.dateCreated ? dayjs(strategieCmgGed.dateCreated) : undefined;
        strategieCmgGed.dateModified = strategieCmgGed.dateModified ? dayjs(strategieCmgGed.dateModified) : undefined;
      });
    }
    return res;
  }
}
