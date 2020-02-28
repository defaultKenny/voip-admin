import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'device',
        loadChildren: () => import('./device/device.module').then(m => m.VoipAdminDeviceModule)
      },
      {
        path: 'device-model',
        loadChildren: () => import('./device-model/device-model.module').then(m => m.VoipAdminDeviceModelModule)
      },
      {
        path: 'device-type',
        loadChildren: () => import('./device-type/device-type.module').then(m => m.VoipAdminDeviceTypeModule)
      },
      {
        path: 'sip-account',
        loadChildren: () => import('./sip-account/sip-account.module').then(m => m.VoipAdminSipAccountModule)
      },
      {
        path: 'pbx-account',
        loadChildren: () => import('./pbx-account/pbx-account.module').then(m => m.VoipAdminPbxAccountModule)
      },
      {
        path: 'responsible-person',
        loadChildren: () => import('./responsible-person/responsible-person.module').then(m => m.VoipAdminResponsiblePersonModule)
      },
      {
        path: 'additional-option',
        loadChildren: () => import('./additional-option/additional-option.module').then(m => m.VoipAdminAdditionalOptionModule)
      },
      {
        path: 'device-setting',
        loadChildren: () => import('./device-setting/device-setting.module').then(m => m.VoipAdminDeviceSettingModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class VoipAdminEntityModule {}
