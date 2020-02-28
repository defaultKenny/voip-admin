import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDeviceSetting, DeviceSetting } from 'app/shared/model/device-setting.model';
import { DeviceSettingService } from './device-setting.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';
import { AdditionalOptionService } from 'app/entities/additional-option/additional-option.service';

@Component({
  selector: 'jhi-device-setting-update',
  templateUrl: './device-setting-update.component.html'
})
export class DeviceSettingUpdateComponent implements OnInit {
  isSaving: boolean;

  devices: IDevice[];

  additionaloptions: IAdditionalOption[];

  editForm = this.fb.group({
    id: [],
    value: [],
    deviceId: [null, Validators.required],
    additionalOptionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected deviceSettingService: DeviceSettingService,
    protected deviceService: DeviceService,
    protected additionalOptionService: AdditionalOptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deviceSetting }) => {
      this.updateForm(deviceSetting);
    });
    this.deviceService
      .query()
      .subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.additionalOptionService
      .query()
      .subscribe(
        (res: HttpResponse<IAdditionalOption[]>) => (this.additionaloptions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(deviceSetting: IDeviceSetting) {
    this.editForm.patchValue({
      id: deviceSetting.id,
      value: deviceSetting.value,
      deviceId: deviceSetting.deviceId,
      additionalOptionId: deviceSetting.additionalOptionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deviceSetting = this.createFromForm();
    if (deviceSetting.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceSettingService.update(deviceSetting));
    } else {
      this.subscribeToSaveResponse(this.deviceSettingService.create(deviceSetting));
    }
  }

  private createFromForm(): IDeviceSetting {
    return {
      ...new DeviceSetting(),
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value,
      deviceId: this.editForm.get(['deviceId']).value,
      additionalOptionId: this.editForm.get(['additionalOptionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceSetting>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDeviceById(index: number, item: IDevice) {
    return item.id;
  }

  trackAdditionalOptionById(index: number, item: IAdditionalOption) {
    return item.id;
  }
}
