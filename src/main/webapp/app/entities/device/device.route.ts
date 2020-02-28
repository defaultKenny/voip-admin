import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Device } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { DeviceComponent } from './device.component';
import { DeviceDetailComponent } from './device-detail.component';
import { DeviceUpdateComponent } from './device-update.component';
import { DeviceDeletePopupComponent } from './device-delete-dialog.component';
import { IDevice } from 'app/shared/model/device.model';

@Injectable({ providedIn: 'root' })
export class DeviceResolve implements Resolve<IDevice> {
  constructor(private service: DeviceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevice> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((device: HttpResponse<Device>) => device.body));
    }
    return of(new Device());
  }
}

export const deviceRoute: Routes = [
  {
    path: '',
    component: DeviceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.device.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeviceDetailComponent,
    resolve: {
      device: DeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.device.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeviceUpdateComponent,
    resolve: {
      device: DeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.device.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeviceUpdateComponent,
    resolve: {
      device: DeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.device.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const devicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeviceDeletePopupComponent,
    resolve: {
      device: DeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.device.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
