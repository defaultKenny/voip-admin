import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { DeviceComponent } from './device.component';
import { DeviceDetailComponent } from './device-detail.component';
import { DeviceUpdateComponent } from './device-update.component';
import { DeviceDeletePopupComponent, DeviceDeleteDialogComponent } from './device-delete-dialog.component';
import { deviceRoute, devicePopupRoute } from './device.route';

const ENTITY_STATES = [...deviceRoute, ...devicePopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DeviceComponent, DeviceDetailComponent, DeviceUpdateComponent, DeviceDeleteDialogComponent, DeviceDeletePopupComponent],
  entryComponents: [DeviceDeleteDialogComponent]
})
export class VoipAdminDeviceModule {}