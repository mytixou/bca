import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStrategieCmgAssmat, getStrategieCmgAssmatIdentifier } from '../strategie-cmg-assmat.model';

export type EntityResponseType = HttpResponse<IStrategieCmgAssmat>;
export type EntityArrayResponseType = HttpResponse<IStrategieCmgAssmat[]>;

@Injectable({ providedIn: 'root' })
export class StrategieCmgAssmatService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/strategie-cmg-assmats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(strategieCmgAssmat: IStrategieCmgAssmat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCmgAssmat);
    return this.http
      .post<IStrategieCmgAssmat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(strategieCmgAssmat: IStrategieCmgAssmat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCmgAssmat);
    return this.http
      .put<IStrategieCmgAssmat>(`${this.resourceUrl}/${getStrategieCmgAssmatIdentifier(strategieCmgAssmat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(strategieCmgAssmat: IStrategieCmgAssmat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCmgAssmat);
    return this.http
      .patch<IStrategieCmgAssmat>(`${this.resourceUrl}/${getStrategieCmgAssmatIdentifier(strategieCmgAssmat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStrategieCmgAssmat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStrategieCmgAssmat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStrategieCmgAssmatToCollectionIfMissing(
    strategieCmgAssmatCollection: IStrategieCmgAssmat[],
    ...strategieCmgAssmatsToCheck: (IStrategieCmgAssmat | null | undefined)[]
  ): IStrategieCmgAssmat[] {
    const strategieCmgAssmats: IStrategieCmgAssmat[] = strategieCmgAssmatsToCheck.filter(isPresent);
    if (strategieCmgAssmats.length > 0) {
      const strategieCmgAssmatCollectionIdentifiers = strategieCmgAssmatCollection.map(
        strategieCmgAssmatItem => getStrategieCmgAssmatIdentifier(strategieCmgAssmatItem)!
      );
      const strategieCmgAssmatsToAdd = strategieCmgAssmats.filter(strategieCmgAssmatItem => {
        const strategieCmgAssmatIdentifier = getStrategieCmgAssmatIdentifier(strategieCmgAssmatItem);
        if (strategieCmgAssmatIdentifier == null || strategieCmgAssmatCollectionIdentifiers.includes(strategieCmgAssmatIdentifier)) {
          return false;
        }
        strategieCmgAssmatCollectionIdentifiers.push(strategieCmgAssmatIdentifier);
        return true;
      });
      return [...strategieCmgAssmatsToAdd, ...strategieCmgAssmatCollection];
    }
    return strategieCmgAssmatCollection;
  }

  protected convertDateFromClient(strategieCmgAssmat: IStrategieCmgAssmat): IStrategieCmgAssmat {
    return Object.assign({}, strategieCmgAssmat, {
      dateCreated: strategieCmgAssmat.dateCreated?.isValid() ? strategieCmgAssmat.dateCreated.toJSON() : undefined,
      dateModified: strategieCmgAssmat.dateModified?.isValid() ? strategieCmgAssmat.dateModified.toJSON() : undefined,
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
      res.body.forEach((strategieCmgAssmat: IStrategieCmgAssmat) => {
        strategieCmgAssmat.dateCreated = strategieCmgAssmat.dateCreated ? dayjs(strategieCmgAssmat.dateCreated) : undefined;
        strategieCmgAssmat.dateModified = strategieCmgAssmat.dateModified ? dayjs(strategieCmgAssmat.dateModified) : undefined;
      });
    }
    return res;
  }
}
