import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStrategiePchE, getStrategiePchEIdentifier } from '../strategie-pch-e.model';

export type EntityResponseType = HttpResponse<IStrategiePchE>;
export type EntityArrayResponseType = HttpResponse<IStrategiePchE[]>;

@Injectable({ providedIn: 'root' })
export class StrategiePchEService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/strategie-pch-es');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(strategiePchE: IStrategiePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategiePchE);
    return this.http
      .post<IStrategiePchE>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(strategiePchE: IStrategiePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategiePchE);
    return this.http
      .put<IStrategiePchE>(`${this.resourceUrl}/${getStrategiePchEIdentifier(strategiePchE) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(strategiePchE: IStrategiePchE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategiePchE);
    return this.http
      .patch<IStrategiePchE>(`${this.resourceUrl}/${getStrategiePchEIdentifier(strategiePchE) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStrategiePchE>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStrategiePchE[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStrategiePchEToCollectionIfMissing(
    strategiePchECollection: IStrategiePchE[],
    ...strategiePchESToCheck: (IStrategiePchE | null | undefined)[]
  ): IStrategiePchE[] {
    const strategiePchES: IStrategiePchE[] = strategiePchESToCheck.filter(isPresent);
    if (strategiePchES.length > 0) {
      const strategiePchECollectionIdentifiers = strategiePchECollection.map(
        strategiePchEItem => getStrategiePchEIdentifier(strategiePchEItem)!
      );
      const strategiePchESToAdd = strategiePchES.filter(strategiePchEItem => {
        const strategiePchEIdentifier = getStrategiePchEIdentifier(strategiePchEItem);
        if (strategiePchEIdentifier == null || strategiePchECollectionIdentifiers.includes(strategiePchEIdentifier)) {
          return false;
        }
        strategiePchECollectionIdentifiers.push(strategiePchEIdentifier);
        return true;
      });
      return [...strategiePchESToAdd, ...strategiePchECollection];
    }
    return strategiePchECollection;
  }

  protected convertDateFromClient(strategiePchE: IStrategiePchE): IStrategiePchE {
    return Object.assign({}, strategiePchE, {
      dateMensuelleDebutValidite: strategiePchE.dateMensuelleDebutValidite?.isValid()
        ? strategiePchE.dateMensuelleDebutValidite.format(DATE_FORMAT)
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
      res.body.forEach((strategiePchE: IStrategiePchE) => {
        strategiePchE.dateMensuelleDebutValidite = strategiePchE.dateMensuelleDebutValidite
          ? dayjs(strategiePchE.dateMensuelleDebutValidite)
          : undefined;
      });
    }
    return res;
  }
}
