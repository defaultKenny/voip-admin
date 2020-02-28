import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { DeviceTypeComponent } from './device-type.component';
import { DeviceTypeDetailComponent } from './device-type-detail.component';
import { DeviceTypeUpdateComponent } from './device-type-update.component';
import { DeviceTypeDeletePopupComponent, DeviceTypeDeleteDialogComponent } from './device-type-delete-dialog.component';
import { deviceTypeRoute, deviceTypePopupRoute } from './device-type.route';

const ENTITY_STATES = [...deviceTypeRoute, ...deviceTypePopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeviceTypeComponent,
    DeviceTypeDetailComponent,
    DeviceTypeUpdateComponent,
    DeviceTypeDeleteDialogComponent,
    DeviceTypeDeletePopupComponent
  ],
  entryComponents: [DeviceTypeDeleteDialogComponent]
})
export class VoipAdminDeviceTypeModule {}
