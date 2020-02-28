import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDeviceModel } from 'app/shared/model/device-model.model';

@Component({
  selector: 'jhi-device-model-detail',
  templateUrl: './device-model-detail.component.html'
})
export class DeviceModelDetailComponent implements OnInit {
  deviceModel: IDeviceModel;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      this.deviceModel = deviceModel;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
