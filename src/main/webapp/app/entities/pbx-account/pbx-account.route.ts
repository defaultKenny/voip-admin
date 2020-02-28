import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PbxAccount } from 'app/shared/model/pbx-account.model';
import { PbxAccountService } from './pbx-account.service';
import { PbxAccountComponent } from './pbx-account.component';
import { PbxAccountDetailComponent } from './pbx-account-detail.component';
import { PbxAccountUpdateComponent } from './pbx-account-update.component';
import { PbxAccountDeletePopupComponent } from './pbx-account-delete-dialog.component';
import { IPbxAccount } from 'app/shared/model/pbx-account.model';

@Injectable({ providedIn: 'root' })
export class PbxAccountResolve implements Resolve<IPbxAccount> {
  constructor(private service: PbxAccountService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPbxAccount> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((pbxAccount: HttpResponse<PbxAccount>) => pbxAccount.body));
    }
    return of(new PbxAccount());
  }
}

export const pbxAccountRoute: Routes = [
  {
    path: '',
    component: PbxAccountComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.pbxAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PbxAccountDetailComponent,
    resolve: {
      pbxAccount: PbxAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.pbxAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PbxAccountUpdateComponent,
    resolve: {
      pbxAccount: PbxAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.pbxAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PbxAccountUpdateComponent,
    resolve: {
      pbxAccount: PbxAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.pbxAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pbxAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PbxAccountDeletePopupComponent,
    resolve: {
      pbxAccount: PbxAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.pbxAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
