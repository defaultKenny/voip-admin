import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { DeviceSettingComponent } from './device-setting.component';
import { DeviceSettingDetailComponent } from './device-setting-detail.component';
import { DeviceSettingUpdateComponent } from './device-setting-update.component';
import { DeviceSettingDeletePopupComponent, DeviceSettingDeleteDialogComponent } from './device-setting-delete-dialog.component';
import { deviceSettingRoute, deviceSettingPopupRoute } from './device-setting.route';

const ENTITY_STATES = [...deviceSettingRoute, ...deviceSettingPopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeviceSettingComponent,
    DeviceSettingDetailComponent,
    DeviceSettingUpdateComponent,
    DeviceSettingDeleteDialogComponent,
    DeviceSettingDeletePopupComponent
  ],
  entryComponents: [DeviceSettingDeleteDialogComponent]
})
export class VoipAdminDeviceSettingModule {}
