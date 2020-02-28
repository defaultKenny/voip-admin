import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';
import { DeviceTypeComponent } from './device-type.component';
import { DeviceTypeDetailComponent } from './device-type-detail.component';
import { DeviceTypeUpdateComponent } from './device-type-update.component';
import { DeviceTypeDeletePopupComponent } from './device-type-delete-dialog.component';
import { IDeviceType } from 'app/shared/model/device-type.model';

@Injectable({ providedIn: 'root' })
export class DeviceTypeResolve implements Resolve<IDeviceType> {
  constructor(private service: DeviceTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((deviceType: HttpResponse<DeviceType>) => deviceType.body));
    }
    return of(new DeviceType());
  }
}

export const deviceTypeRoute: Routes = [
  {
    path: '',
    component: DeviceTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.deviceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeviceTypeDetailComponent,
    resolve: {
      deviceType: DeviceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeviceTypeUpdateComponent,
    resolve: {
      deviceType: DeviceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeviceTypeUpdateComponent,
    resolve: {
      deviceType: DeviceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deviceTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeviceTypeDeletePopupComponent,
    resolve: {
      deviceType: DeviceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
