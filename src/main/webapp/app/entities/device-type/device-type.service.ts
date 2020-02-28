import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeviceType } from 'app/shared/model/device-type.model';

type EntityResponseType = HttpResponse<IDeviceType>;
type EntityArrayResponseType = HttpResponse<IDeviceType[]>;

@Injectable({ providedIn: 'root' })
export class DeviceTypeService {
  public resourceUrl = SERVER_API_URL + 'api/device-types';

  constructor(protected http: HttpClient) {}

  create(deviceType: IDeviceType): Observable<EntityResponseType> {
    return this.http.post<IDeviceType>(this.resourceUrl, deviceType, { observe: 'response' });
  }

  update(deviceType: IDeviceType): Observable<EntityResponseType> {
    return this.http.put<IDeviceType>(this.resourceUrl, deviceType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeviceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeviceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
