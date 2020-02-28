import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';
import { DeviceModelComponent } from './device-model.component';
import { DeviceModelDetailComponent } from './device-model-detail.component';
import { DeviceModelUpdateComponent } from './device-model-update.component';
import { DeviceModelDeletePopupComponent } from './device-model-delete-dialog.component';
import { IDeviceModel } from 'app/shared/model/device-model.model';

@Injectable({ providedIn: 'root' })
export class DeviceModelResolve implements Resolve<IDeviceModel> {
  constructor(private service: DeviceModelService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceModel> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((deviceModel: HttpResponse<DeviceModel>) => deviceModel.body));
    }
    return of(new DeviceModel());
  }
}

export const deviceModelRoute: Routes = [
  {
    path: '',
    component: DeviceModelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.deviceModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeviceModelDetailComponent,
    resolve: {
      deviceModel: DeviceModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeviceModelUpdateComponent,
    resolve: {
      deviceModel: DeviceModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeviceModelUpdateComponent,
    resolve: {
      deviceModel: DeviceModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deviceModelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeviceModelDeletePopupComponent,
    resolve: {
      deviceModel: DeviceModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceModel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
