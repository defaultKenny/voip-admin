import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SipAccount } from 'app/shared/model/sip-account.model';
import { SipAccountService } from './sip-account.service';
import { SipAccountComponent } from './sip-account.component';
import { SipAccountDetailComponent } from './sip-account-detail.component';
import { SipAccountUpdateComponent } from './sip-account-update.component';
import { SipAccountDeletePopupComponent } from './sip-account-delete-dialog.component';
import { ISipAccount } from 'app/shared/model/sip-account.model';

@Injectable({ providedIn: 'root' })
export class SipAccountResolve implements Resolve<ISipAccount> {
  constructor(private service: SipAccountService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISipAccount> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((sipAccount: HttpResponse<SipAccount>) => sipAccount.body));
    }
    return of(new SipAccount());
  }
}

export const sipAccountRoute: Routes = [
  {
    path: '',
    component: SipAccountComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.sipAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SipAccountDetailComponent,
    resolve: {
      sipAccount: SipAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.sipAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SipAccountUpdateComponent,
    resolve: {
      sipAccount: SipAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.sipAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SipAccountUpdateComponent,
    resolve: {
      sipAccount: SipAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.sipAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sipAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SipAccountDeletePopupComponent,
    resolve: {
      sipAccount: SipAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.sipAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
