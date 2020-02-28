import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';

type EntityResponseType = HttpResponse<IAdditionalOption>;
type EntityArrayResponseType = HttpResponse<IAdditionalOption[]>;

@Injectable({ providedIn: 'root' })
export class AdditionalOptionService {
  public resourceUrl = SERVER_API_URL + 'api/additional-options';

  constructor(protected http: HttpClient) {}

  create(additionalOption: IAdditionalOption): Observable<EntityResponseType> {
    return this.http.post<IAdditionalOption>(this.resourceUrl, additionalOption, { observe: 'response' });
  }

  update(additionalOption: IAdditionalOption): Observable<EntityResponseType> {
    return this.http.put<IAdditionalOption>(this.resourceUrl, additionalOption, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdditionalOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdditionalOption[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
