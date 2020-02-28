import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceSetting } from 'app/shared/model/device-setting.model';
import { DeviceSettingService } from './device-setting.service';
import { DeviceSettingComponent } from './device-setting.component';
import { DeviceSettingDetailComponent } from './device-setting-detail.component';
import { DeviceSettingUpdateComponent } from './device-setting-update.component';
import { DeviceSettingDeletePopupComponent } from './device-setting-delete-dialog.component';
import { IDeviceSetting } from 'app/shared/model/device-setting.model';

@Injectable({ providedIn: 'root' })
export class DeviceSettingResolve implements Resolve<IDeviceSetting> {
  constructor(private service: DeviceSettingService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceSetting> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((deviceSetting: HttpResponse<DeviceSetting>) => deviceSetting.body));
    }
    return of(new DeviceSetting());
  }
}

export const deviceSettingRoute: Routes = [
  {
    path: '',
    component: DeviceSettingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.deviceSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeviceSettingDetailComponent,
    resolve: {
      deviceSetting: DeviceSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeviceSettingUpdateComponent,
    resolve: {
      deviceSetting: DeviceSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeviceSettingUpdateComponent,
    resolve: {
      deviceSetting: DeviceSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceSetting.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deviceSettingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeviceSettingDeletePopupComponent,
    resolve: {
      deviceSetting: DeviceSettingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.deviceSetting.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
