import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStrategieCi, getStrategieCiIdentifier } from '../strategie-ci.model';

export type EntityResponseType = HttpResponse<IStrategieCi>;
export type EntityArrayResponseType = HttpResponse<IStrategieCi[]>;

@Injectable({ providedIn: 'root' })
export class StrategieCiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/strategie-cis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(strategieCi: IStrategieCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCi);
    return this.http
      .post<IStrategieCi>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(strategieCi: IStrategieCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCi);
    return this.http
      .put<IStrategieCi>(`${this.resourceUrl}/${getStrategieCiIdentifier(strategieCi) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(strategieCi: IStrategieCi): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(strategieCi);
    return this.http
      .patch<IStrategieCi>(`${this.resourceUrl}/${getStrategieCiIdentifier(strategieCi) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStrategieCi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStrategieCi[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStrategieCiToCollectionIfMissing(
    strategieCiCollection: IStrategieCi[],
    ...strategieCisToCheck: (IStrategieCi | null | undefined)[]
  ): IStrategieCi[] {
    const strategieCis: IStrategieCi[] = strategieCisToCheck.filter(isPresent);
    if (strategieCis.length > 0) {
      const strategieCiCollectionIdentifiers = strategieCiCollection.map(strategieCiItem => getStrategieCiIdentifier(strategieCiItem)!);
      const strategieCisToAdd = strategieCis.filter(strategieCiItem => {
        const strategieCiIdentifier = getStrategieCiIdentifier(strategieCiItem);
        if (strategieCiIdentifier == null || strategieCiCollectionIdentifiers.includes(strategieCiIdentifier)) {
          return false;
        }
        strategieCiCollectionIdentifiers.push(strategieCiIdentifier);
        return true;
      });
      return [...strategieCisToAdd, ...strategieCiCollection];
    }
    return strategieCiCollection;
  }

  protected convertDateFromClient(strategieCi: IStrategieCi): IStrategieCi {
    return Object.assign({}, strategieCi, {
      dateAnnuelleDebutValidite: strategieCi.dateAnnuelleDebutValidite?.isValid()
        ? strategieCi.dateAnnuelleDebutValidite.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAnnuelleDebutValidite = res.body.dateAnnuelleDebutValidite ? dayjs(res.body.dateAnnuelleDebutValidite) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((strategieCi: IStrategieCi) => {
        strategieCi.dateAnnuelleDebutValidite = strategieCi.dateAnnuelleDebutValidite
          ? dayjs(strategieCi.dateAnnuelleDebutValidite)
          : undefined;
      });
    }
    return res;
  }
}
