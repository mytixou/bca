import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStrategiePch, getStrategiePchIdentifier } from '../strategie-pch.model';

export type EntityResponseType = HttpResponse<IStrategiePch>;
export type EntityArrayResponseType = HttpResponse<IStrategiePch[]>;

@Injectable({ providedIn: 'root' })
export class StrategiePchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/strategie-pches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(strategiePch: IStrategiePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategiePch);
    return this.http
      .post<IStrategiePch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(strategiePch: IStrategiePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategiePch);
    return this.http
      .put<IStrategiePch>(`${this.resourceUrl}/${getStrategiePchIdentifier(strategiePch) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(strategiePch: IStrategiePch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategiePch);
    return this.http
      .patch<IStrategiePch>(`${this.resourceUrl}/${getStrategiePchIdentifier(strategiePch) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStrategiePch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStrategiePch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStrategiePchToCollectionIfMissing(
    strategiePchCollection: IStrategiePch[],
    ...strategiePchesToCheck: (IStrategiePch | null | undefined)[]
  ): IStrategiePch[] {
    const strategiePches: IStrategiePch[] = strategiePchesToCheck.filter(isPresent);
    if (strategiePches.length > 0) {
      const strategiePchCollectionIdentifiers = strategiePchCollection.map(
        strategiePchItem => getStrategiePchIdentifier(strategiePchItem)!
      );
      const strategiePchesToAdd = strategiePches.filter(strategiePchItem => {
        const strategiePchIdentifier = getStrategiePchIdentifier(strategiePchItem);
        if (strategiePchIdentifier == null || strategiePchCollectionIdentifiers.includes(strategiePchIdentifier)) {
          return false;
        }
        strategiePchCollectionIdentifiers.push(strategiePchIdentifier);
        return true;
      });
      return [...strategiePchesToAdd, ...strategiePchCollection];
    }
    return strategiePchCollection;
  }

  protected convertDateFromClient(strategiePch: IStrategiePch): IStrategiePch {
    return Object.assign({}, strategiePch, {
      dateMensuelleDebutValidite: strategiePch.dateMensuelleDebutValidite?.isValid()
        ? strategiePch.dateMensuelleDebutValidite.format(DATE_FORMAT)
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
      res.body.forEach((strategiePch: IStrategiePch) => {
        strategiePch.dateMensuelleDebutValidite = strategiePch.dateMensuelleDebutValidite
          ? dayjs(strategiePch.dateMensuelleDebutValidite)
          : undefined;
      });
    }
    return res;
  }
}
