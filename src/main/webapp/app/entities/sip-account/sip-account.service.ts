import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISipAccount } from 'app/shared/model/sip-account.model';

type EntityResponseType = HttpResponse<ISipAccount>;
type EntityArrayResponseType = HttpResponse<ISipAccount[]>;

@Injectable({ providedIn: 'root' })
export class SipAccountService {
  public resourceUrl = SERVER_API_URL + 'api/sip-accounts';

  constructor(protected http: HttpClient) {}

  create(sipAccount: ISipAccount): Observable<EntityResponseType> {
    return this.http.post<ISipAccount>(this.resourceUrl, sipAccount, { observe: 'response' });
  }

  update(sipAccount: ISipAccount): Observable<EntityResponseType> {
    return this.http.put<ISipAccount>(this.resourceUrl, sipAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISipAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISipAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
