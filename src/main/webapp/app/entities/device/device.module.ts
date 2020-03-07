import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { DeviceComponent } from './device.component';
import { DeviceDetailComponent } from './device-detail.component';
import { DeviceUpdateComponent } from './device-update.component';
import { DeviceDeletePopupComponent, DeviceDeleteDialogComponent } from './device-delete-dialog.component';
import { DeviceModelChangeDialogComponent } from './device-model-change-dialog.component';
import { deviceRoute, devicePopupRoute } from './device.route';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

const ENTITY_STATES = [...deviceRoute, ...devicePopupRoute];

@NgModule({
  imports: [MatSlideToggleModule, VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeviceComponent,
    DeviceDetailComponent,
    DeviceUpdateComponent,
    DeviceDeleteDialogComponent,
    DeviceDeletePopupComponent,
    DeviceModelChangeDialogComponent
  ],
  entryComponents: [DeviceDeleteDialogComponent, DeviceModelChangeDialogComponent]
})
export class VoipAdminDeviceModule {}
