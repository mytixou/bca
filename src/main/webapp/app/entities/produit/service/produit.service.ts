import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProduit, getProduitIdentifier } from '../produit.model';

export type EntityResponseType = HttpResponse<IProduit>;
export type EntityArrayResponseType = HttpResponse<IProduit[]>;

@Injectable({ providedIn: 'root' })
export class ProduitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/produits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(produit: IProduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produit);
    return this.http
      .post<IProduit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(produit: IProduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produit);
    return this.http
      .put<IProduit>(`${this.resourceUrl}/${getProduitIdentifier(produit) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(produit: IProduit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produit);
    return this.http
      .patch<IProduit>(`${this.resourceUrl}/${getProduitIdentifier(produit) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProduit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProduitToCollectionIfMissing(produitCollection: IProduit[], ...produitsToCheck: (IProduit | null | undefined)[]): IProduit[] {
    const produits: IProduit[] = produitsToCheck.filter(isPresent);
    if (produits.length > 0) {
      const produitCollectionIdentifiers = produitCollection.map(produitItem => getProduitIdentifier(produitItem)!);
      const produitsToAdd = produits.filter(produitItem => {
        const produitIdentifier = getProduitIdentifier(produitItem);
        if (produitIdentifier == null || produitCollectionIdentifiers.includes(produitIdentifier)) {
          return false;
        }
        produitCollectionIdentifiers.push(produitIdentifier);
        return true;
      });
      return [...produitsToAdd, ...produitCollection];
    }
    return produitCollection;
  }

  protected convertDateFromClient(produit: IProduit): IProduit {
    return Object.assign({}, produit, {
      dateLancement: produit.dateLancement?.isValid() ? produit.dateLancement.format(DATE_FORMAT) : undefined,
      dateResiliation: produit.dateResiliation?.isValid() ? produit.dateResiliation.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateLancement = res.body.dateLancement ? dayjs(res.body.dateLancement) : undefined;
      res.body.dateResiliation = res.body.dateResiliation ? dayjs(res.body.dateResiliation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((produit: IProduit) => {
        produit.dateLancement = produit.dateLancement ? dayjs(produit.dateLancement) : undefined;
        produit.dateResiliation = produit.dateResiliation ? dayjs(produit.dateResiliation) : undefined;
      });
    }
    return res;
  }
}
