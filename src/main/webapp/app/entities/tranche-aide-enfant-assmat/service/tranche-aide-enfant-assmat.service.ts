import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrancheAideEnfantAssmat, getTrancheAideEnfantAssmatIdentifier } from '../tranche-aide-enfant-assmat.model';

export type EntityResponseType = HttpResponse<ITrancheAideEnfantAssmat>;
export type EntityArrayResponseType = HttpResponse<ITrancheAideEnfantAssmat[]>;

@Injectable({ providedIn: 'root' })
export class TrancheAideEnfantAssmatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tranche-aide-enfant-assmats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trancheAideEnfantAssmat);
    return this.http
      .post<ITrancheAideEnfantAssmat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trancheAideEnfantAssmat);
    return this.http
      .put<ITrancheAideEnfantAssmat>(
        `${this.resourceUrl}/${getTrancheAideEnfantAssmatIdentifier(trancheAideEnfantAssmat) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trancheAideEnfantAssmat);
    return this.http
      .patch<ITrancheAideEnfantAssmat>(
        `${this.resourceUrl}/${getTrancheAideEnfantAssmatIdentifier(trancheAideEnfantAssmat) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITrancheAideEnfantAssmat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITrancheAideEnfantAssmat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTrancheAideEnfantAssmatToCollectionIfMissing(
    trancheAideEnfantAssmatCollection: ITrancheAideEnfantAssmat[],
    ...trancheAideEnfantAssmatsToCheck: (ITrancheAideEnfantAssmat | null | undefined)[]
  ): ITrancheAideEnfantAssmat[] {
    const trancheAideEnfantAssmats: ITrancheAideEnfantAssmat[] = trancheAideEnfantAssmatsToCheck.filter(isPresent);
    if (trancheAideEnfantAssmats.length > 0) {
      const trancheAideEnfantAssmatCollectionIdentifiers = trancheAideEnfantAssmatCollection.map(
        trancheAideEnfantAssmatItem => getTrancheAideEnfantAssmatIdentifier(trancheAideEnfantAssmatItem)!
      );
      const trancheAideEnfantAssmatsToAdd = trancheAideEnfantAssmats.filter(trancheAideEnfantAssmatItem => {
        const trancheAideEnfantAssmatIdentifier = getTrancheAideEnfantAssmatIdentifier(trancheAideEnfantAssmatItem);
        if (
          trancheAideEnfantAssmatIdentifier == null ||
          trancheAideEnfantAssmatCollectionIdentifiers.includes(trancheAideEnfantAssmatIdentifier)
        ) {
          return false;
        }
        trancheAideEnfantAssmatCollectionIdentifiers.push(trancheAideEnfantAssmatIdentifier);
        return true;
      });
      return [...trancheAideEnfantAssmatsToAdd, ...trancheAideEnfantAssmatCollection];
    }
    return trancheAideEnfantAssmatCollection;
  }

  protected convertDateFromClient(trancheAideEnfantAssmat: ITrancheAideEnfantAssmat): ITrancheAideEnfantAssmat {
    return Object.assign({}, trancheAideEnfantAssmat, {
      dateCreated: trancheAideEnfantAssmat.dateCreated?.isValid() ? trancheAideEnfantAssmat.dateCreated.toJSON() : undefined,
      dateModified: trancheAideEnfantAssmat.dateModified?.isValid() ? trancheAideEnfantAssmat.dateModified.toJSON() : undefined,
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
      res.body.forEach((trancheAideEnfantAssmat: ITrancheAideEnfantAssmat) => {
        trancheAideEnfantAssmat.dateCreated = trancheAideEnfantAssmat.dateCreated ? dayjs(trancheAideEnfantAssmat.dateCreated) : undefined;
        trancheAideEnfantAssmat.dateModified = trancheAideEnfantAssmat.dateModified
          ? dayjs(trancheAideEnfantAssmat.dateModified)
          : undefined;
      });
    }
    return res;
  }
}
