import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { ResponsiblePersonComponent } from './responsible-person.component';
import { ResponsiblePersonDetailComponent } from './responsible-person-detail.component';
import { ResponsiblePersonUpdateComponent } from './responsible-person-update.component';
import {
  ResponsiblePersonDeletePopupComponent,
  ResponsiblePersonDeleteDialogComponent
} from './responsible-person-delete-dialog.component';
import { responsiblePersonRoute, responsiblePersonPopupRoute } from './responsible-person.route';

const ENTITY_STATES = [...responsiblePersonRoute, ...responsiblePersonPopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResponsiblePersonComponent,
    ResponsiblePersonDetailComponent,
    ResponsiblePersonUpdateComponent,
    ResponsiblePersonDeleteDialogComponent,
    ResponsiblePersonDeletePopupComponent
  ],
  entryComponents: [ResponsiblePersonDeleteDialogComponent]
})
export class VoipAdminResponsiblePersonModule {}
