import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeviceModel } from 'app/shared/model/device-model.model';

type EntityResponseType = HttpResponse<IDeviceModel>;
type EntityArrayResponseType = HttpResponse<IDeviceModel[]>;

@Injectable({ providedIn: 'root' })
export class DeviceModelService {
  public resourceUrl = SERVER_API_URL + 'api/device-models';

  constructor(protected http: HttpClient) {}

  create(deviceModel: IDeviceModel): Observable<EntityResponseType> {
    return this.http.post<IDeviceModel>(this.resourceUrl, deviceModel, { observe: 'response' });
  }

  update(deviceModel: IDeviceModel): Observable<EntityResponseType> {
    return this.http.put<IDeviceModel>(this.resourceUrl, deviceModel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeviceModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeviceModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
