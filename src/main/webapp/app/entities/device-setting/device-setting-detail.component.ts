import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceSetting } from 'app/shared/model/device-setting.model';

@Component({
  selector: 'jhi-device-setting-detail',
  templateUrl: './device-setting-detail.component.html'
})
export class DeviceSettingDetailComponent implements OnInit {
  deviceSetting: IDeviceSetting;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceSetting }) => {
      this.deviceSetting = deviceSetting;
    });
  }

  previousState() {
    window.history.back();
  }
}
