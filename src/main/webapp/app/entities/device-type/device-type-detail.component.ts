import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceType } from 'app/shared/model/device-type.model';

@Component({
  selector: 'jhi-device-type-detail',
  templateUrl: './device-type-detail.component.html'
})
export class DeviceTypeDetailComponent implements OnInit {
  deviceType: IDeviceType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceType }) => {
      this.deviceType = deviceType;
    });
  }

  previousState() {
    window.history.back();
  }
}
