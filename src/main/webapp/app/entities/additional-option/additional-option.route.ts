import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdditionalOption } from 'app/shared/model/additional-option.model';
import { AdditionalOptionService } from './additional-option.service';
import { AdditionalOptionComponent } from './additional-option.component';
import { AdditionalOptionDetailComponent } from './additional-option-detail.component';
import { AdditionalOptionUpdateComponent } from './additional-option-update.component';
import { AdditionalOptionDeletePopupComponent } from './additional-option-delete-dialog.component';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';

@Injectable({ providedIn: 'root' })
export class AdditionalOptionResolve implements Resolve<IAdditionalOption> {
  constructor(private service: AdditionalOptionService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdditionalOption> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((additionalOption: HttpResponse<AdditionalOption>) => additionalOption.body));
    }
    return of(new AdditionalOption());
  }
}

export const additionalOptionRoute: Routes = [
  {
    path: '',
    component: AdditionalOptionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.additionalOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdditionalOptionDetailComponent,
    resolve: {
      additionalOption: AdditionalOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.additionalOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdditionalOptionUpdateComponent,
    resolve: {
      additionalOption: AdditionalOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.additionalOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdditionalOptionUpdateComponent,
    resolve: {
      additionalOption: AdditionalOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.additionalOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const additionalOptionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AdditionalOptionDeletePopupComponent,
    resolve: {
      additionalOption: AdditionalOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.additionalOption.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
