import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from './responsible-person.service';
import { ResponsiblePersonComponent } from './responsible-person.component';
import { ResponsiblePersonDetailComponent } from './responsible-person-detail.component';
import { ResponsiblePersonUpdateComponent } from './responsible-person-update.component';
import { ResponsiblePersonDeletePopupComponent } from './responsible-person-delete-dialog.component';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';

@Injectable({ providedIn: 'root' })
export class ResponsiblePersonResolve implements Resolve<IResponsiblePerson> {
  constructor(private service: ResponsiblePersonService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsiblePerson> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((responsiblePerson: HttpResponse<ResponsiblePerson>) => responsiblePerson.body));
    }
    return of(new ResponsiblePerson());
  }
}

export const responsiblePersonRoute: Routes = [
  {
    path: '',
    component: ResponsiblePersonComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'voipAdminApp.responsiblePerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResponsiblePersonDetailComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.responsiblePerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResponsiblePersonUpdateComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.responsiblePerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResponsiblePersonUpdateComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.responsiblePerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const responsiblePersonPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ResponsiblePersonDeletePopupComponent,
    resolve: {
      responsiblePerson: ResponsiblePersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'voipAdminApp.responsiblePerson.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
