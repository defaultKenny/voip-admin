import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';

type EntityResponseType = HttpResponse<IResponsiblePerson>;
type EntityArrayResponseType = HttpResponse<IResponsiblePerson[]>;

@Injectable({ providedIn: 'root' })
export class ResponsiblePersonService {
  public resourceUrl = SERVER_API_URL + 'api/responsible-people';

  constructor(protected http: HttpClient) {}

  create(responsiblePerson: IResponsiblePerson): Observable<EntityResponseType> {
    return this.http.post<IResponsiblePerson>(this.resourceUrl, responsiblePerson, { observe: 'response' });
  }

  update(responsiblePerson: IResponsiblePerson): Observable<EntityResponseType> {
    return this.http.put<IResponsiblePerson>(this.resourceUrl, responsiblePerson, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResponsiblePerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResponsiblePerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
