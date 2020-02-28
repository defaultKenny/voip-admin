import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { DeviceModelComponent } from './device-model.component';
import { DeviceModelDetailComponent } from './device-model-detail.component';
import { DeviceModelUpdateComponent } from './device-model-update.component';
import { DeviceModelDeletePopupComponent, DeviceModelDeleteDialogComponent } from './device-model-delete-dialog.component';
import { deviceModelRoute, deviceModelPopupRoute } from './device-model.route';

const ENTITY_STATES = [...deviceModelRoute, ...deviceModelPopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeviceModelComponent,
    DeviceModelDetailComponent,
    DeviceModelUpdateComponent,
    DeviceModelDeleteDialogComponent,
    DeviceModelDeletePopupComponent
  ],
  entryComponents: [DeviceModelDeleteDialogComponent]
})
export class VoipAdminDeviceModelModule {}
