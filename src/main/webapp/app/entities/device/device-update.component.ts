import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDevice, Device } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { AdditionalOptionService } from 'app/entities/additional-option/additional-option.service';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';
// import { DeviceSettingService } from 'app/entities/device-setting/device-setting.service';
import { IDeviceSetting, DeviceSetting } from 'app/shared/model/device-setting.model';

@Component({
  selector: 'jhi-device-update',
  styleUrls: ['./device-update.component.scss'],
  templateUrl: './device-update.component.html'
})
export class DeviceUpdateComponent implements OnInit {
  isSaving: boolean;

  devicemodels: IDeviceModel[];

  responsiblepeople: IResponsiblePerson[];

  additionalOptions: IAdditionalOption[];

  editForm = this.fb.group({
    id: [],
    mac: [null, [Validators.required]],
    inventory: [null, [Validators.required]],
    location: [],
    hostname: [],
    webAccessLogin: [],
    webAccessPassword: [],
    dhcpEnabled: [],
    ipAddress: [],
    subnetMask: [],
    defaultGateway: [],
    dns1: [],
    dns2: [],
    provUrl: [],
    provProtocol: [],
    deviceModelId: [null, Validators.required],
    responsiblePersonId: [],
    settings: new FormArray([])
  });

  get deviceSettingControls() {
    return this.editForm.controls.settings as FormArray;
  }

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected deviceService: DeviceService,
    protected deviceModelService: DeviceModelService,
    protected responsiblePersonService: ResponsiblePersonService,
    protected additionalOptionService: AdditionalOptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ device }) => {
      this.updateForm(device);
    });
    this.deviceModelService
      .query()
      .subscribe(
        (res: HttpResponse<IDeviceModel[]>) => (this.devicemodels = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.responsiblePersonService
      .query()
      .subscribe(
        (res: HttpResponse<IResponsiblePerson[]>) => (this.responsiblepeople = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.additionalOptionService
      .query({ 'deviceModelId.in': this.editForm.get(['deviceModelId']).value })
      .subscribe(
        (res: HttpResponse<IAdditionalOption[]>) => (this.additionalOptions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(device: IDevice) {
    console.log(device);
    this.editForm.patchValue({
      id: device.id,
      mac: device.mac,
      inventory: device.inventory,
      location: device.location,
      hostname: device.hostname,
      webAccessLogin: device.webAccessLogin,
      webAccessPassword: device.webAccessPassword,
      dhcpEnabled: device.dhcpEnabled,
      ipAddress: device.ipAddress,
      subnetMask: device.subnetMask,
      defaultGateway: device.defaultGateway,
      dns1: device.dns1,
      dns2: device.dns2,
      provUrl: device.provUrl,
      provProtocol: device.provProtocol,
      deviceModelId: device.deviceModelId,
      responsiblePersonId: device.responsiblePersonId
    });
    this.updateDeviceSettingsForm(device);
  }

  private updateDeviceSettingsForm(device: IDevice) {
    if (!!device && !!device.deviceSettingDTOs) {
      for (let index = 0; index < device.deviceSettingDTOs.length; index++) {
        this.addDeviceSetting();
        this.deviceSettingControls.controls[index].patchValue({
          settingId: device.deviceSettingDTOs[index].id,
          settingOption: device.deviceSettingDTOs[index].additionalOptionId,
          settingValue: device.deviceSettingDTOs[index].value
        });
      }
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const device = this.createFromForm();
    if (device.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceService.update(device));
    } else {
      this.subscribeToSaveResponse(this.deviceService.create(device));
    }
  }

  private createFromForm(): IDevice {
    return {
      ...new Device(),
      id: this.editForm.get(['id']).value,
      mac: this.editForm.get(['mac']).value,
      inventory: this.editForm.get(['inventory']).value,
      location: this.editForm.get(['location']).value,
      hostname: this.editForm.get(['hostname']).value,
      webAccessLogin: this.editForm.get(['webAccessLogin']).value,
      webAccessPassword: this.editForm.get(['webAccessPassword']).value,
      dhcpEnabled: this.editForm.get(['dhcpEnabled']).value,
      ipAddress: this.editForm.get(['ipAddress']).value,
      subnetMask: this.editForm.get(['subnetMask']).value,
      defaultGateway: this.editForm.get(['defaultGateway']).value,
      dns1: this.editForm.get(['dns1']).value,
      dns2: this.editForm.get(['dns2']).value,
      provUrl: this.editForm.get(['provUrl']).value,
      provProtocol: this.editForm.get(['provProtocol']).value,
      deviceModelId: this.editForm.get(['deviceModelId']).value,
      responsiblePersonId: this.editForm.get(['responsiblePersonId']).value,
      deviceSettingDTOs: this.createDeviceSettingsArrayFromForm()
    };
  }

  private createDeviceSettingsArrayFromForm(): IDeviceSetting[] {
    const result = [];
    for (const group of this.deviceSettingControls.controls) {
      result.push(this.createDeviceSettingFromForm(group));
    }
    return result;
  }

  private createDeviceSettingFromForm(group): IDeviceSetting {
    return {
      ...new DeviceSetting(),
      id: group.get(['settingId']).value,
      value: group.get(['settingValue']).value,
      additionalOptionId: group.get(['settingOption']).value,
      deviceId: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevice>>) {
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

  trackDeviceModelById(index: number, item: IDeviceModel) {
    return item.id;
  }

  trackResponsiblePersonById(index: number, item: IResponsiblePerson) {
    return item.id;
  }

  private addDeviceSetting() {
    this.deviceSettingControls.push(
      this.fb.group({
        settingId: [],
        settingOption: [],
        settingValue: []
      })
    );
  }

  private deleteDeviceSetting(index: number) {
    console.log(index);
    this.deviceSettingControls.removeAt(index);
  }
}
