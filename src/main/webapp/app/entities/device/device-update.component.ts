import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IDevice, Device } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { AdditionalOptionService } from 'app/entities/additional-option/additional-option.service';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';
import { IDeviceSetting, DeviceSetting } from 'app/shared/model/device-setting.model';
import { IPbxAccount } from 'app/shared/model/pbx-account.model';
import { PbxAccountService } from 'app/entities/pbx-account/pbx-account.service';
import { ISipAccount, SipAccount } from 'app/shared/model/sip-account.model';
// import { StandardModalComponent } from 'app/shared/standard-modal/standard-modal.component';
import { DeviceModelChangeDialogComponent } from './device-model-change-dialog.component';

@Component({
  selector: 'jhi-device-update',
  styleUrls: ['./device-update.component.scss'],
  templateUrl: './device-update.component.html'
})
export class DeviceUpdateComponent implements OnInit {
  isSaving: boolean;

  linesCount = 0;

  devicemodels: IDeviceModel[] = [];

  responsiblepeople: IResponsiblePerson[] = [];

  additionalOptions: IAdditionalOption[] = [];

  pbxAccounts: IPbxAccount[] = [];

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
    settings: new FormArray([]),
    accounts: new FormArray([])
  });

  get deviceSettingControls() {
    return this.editForm.controls.settings as FormArray;
  }

  set deviceSettingControls(value) {
    this.editForm.controls.settings = value;
  }

  get sipAccountControls() {
    return this.editForm.controls.accounts as FormArray;
  }

  set sipAccountControls(value) {
    this.editForm.controls.accounts = value;
  }

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected deviceService: DeviceService,
    protected deviceModelService: DeviceModelService,
    protected responsiblePersonService: ResponsiblePersonService,
    protected additionalOptionService: AdditionalOptionService,
    protected pbxAccountService: PbxAccountService,
    protected activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ device }) => {
      this.updateForm(device);
      this.updateLinesCount();
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
    this.pbxAccountService
      .query()
      .subscribe(
        (res: HttpResponse<IPbxAccount[]>) => (this.pbxAccounts = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.reloadModelOptions();

    this.editForm.get('deviceModelId').valueChanges.subscribe(() => {
      console.log('1234');
      const oldValue = this.editForm.value['deviceModelId'];
      //       this.onDeviceModelChange();

      this.modalService.open(DeviceModelChangeDialogComponent).result.then(
        () => {
          console.log('true');
          this.clearForeignSettingsAndLines();
          this.reloadModelOptions();
          this.updateLinesCount();
        },
        () => {
          console.log('cancel');
          this.editForm.get(['deviceModelId']).setValue(oldValue);
        }
      );
    });
  }

  updateForm(device: IDevice) {
    this.editForm.patchValue({
      id: device.id,
      mac: this.formatMacAfterLoad(device.mac),
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
    this.updateSipAccountsForm(device);
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

  private updateSipAccountsForm(device: IDevice) {
    if (!!device && !!device.sipAccounts) {
      const maxLineNumber = Math.max(...device.sipAccounts.map(account => account.lineNumber));
      for (let index = 0; index < maxLineNumber; index++) {
        this.addSipAccount();
        const currentAccount = device.sipAccounts.find(account => account.lineNumber === index + 1);
        if (currentAccount) {
          this.sipAccountControls.controls[index].patchValue({
            sipAccountId: currentAccount.id,
            sipAccountUsername: currentAccount.username,
            sipAccountPassword: currentAccount.password,
            sipAccountLineEnabled: currentAccount.lineEnabled,
            sipAccountLineNumber: currentAccount.lineNumber,
            sipAccountIsManuallyCreated: currentAccount.isManuallyCreated,
            sipAccountPbxAccountId: currentAccount.pbxAccountId
          });
        }
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
      mac: this.formatMacBeforeSave(this.editForm.get(['mac']).value),
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
      deviceSettingDTOs: this.createDeviceSettingsArrayFromForm(),
      sipAccounts: this.createSipAccountsArrayFromForm()
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

  private createSipAccountsArrayFromForm(): ISipAccount[] {
    const result = [];
    for (const group of this.sipAccountControls.controls) {
      console.log(group.get(['sipAccountLineNumber']));
      result.push(this.createSipAccountFromForm(group));
    }
    return result;
  }

  private createSipAccountFromForm(group): ISipAccount {
    return {
      ...new SipAccount(),
      id: group.get(['sipAccountId']).value,
      username: group.get(['sipAccountUsername']).value,
      password: group.get(['sipAccountPassword']).value,
      lineEnabled: group.get(['sipAccountLineEnabled']).value,
      lineNumber: group.get(['sipAccountLineNumber']).value,
      isManuallyCreated: group.get(['sipAccountIsManuallyCreated']).value,
      pbxAccountId: group.get(['sipAccountPbxAccountId']).value,
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

  trackpbxAccountById(index: number, item: IPbxAccount) {
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
    this.deviceSettingControls.removeAt(index);
  }

  private addSipAccount() {
    this.sipAccountControls.push(
      this.fb.group({
        sipAccountId: [],
        sipAccountUsername: [],
        sipAccountPassword: [],
        sipAccountLineEnabled: [],
        sipAccountLineNumber: this.sipAccountControls.length + 1,
        sipAccountIsManuallyCreated: [],
        sipAccountPbxAccountId: []
      })
    );
  }

  private deleteSipAccount(index: number) {
    this.sipAccountControls.removeAt(index);
  }

  private reloadModelOptions() {
    if (this.editForm.get(['deviceModelId']).value) {
      this.additionalOptionService
        .query({ 'deviceModelsId.equals': this.editForm.get(['deviceModelId']).value })
        .subscribe(
          (res: HttpResponse<IAdditionalOption[]>) => (this.additionalOptions = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
  }

  private clearForeignSettingsAndLines() {
    let deviceModelOptions;
    let deviceLinesCount;
    const deviceModelId = this.editForm.get(['deviceModelId']).value;
    this.deviceModelService.find(deviceModelId).subscribe(
      (res: HttpResponse<IDeviceModel>) => {
        deviceModelOptions = res.body.additionalOptions.map(option => option.id);
        this.deviceSettingControls.controls = this.deviceSettingControls.controls.filter(control => {
          if (deviceModelOptions.indexOf(control.value.settingOption) !== -1) {
            return true;
          }
        });

        deviceLinesCount = res.body.linesCount;
        this.sipAccountControls.controls = this.sipAccountControls.controls.slice(0, deviceLinesCount);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private updateLinesCount() {
    const deviceModelId = this.editForm.get(['deviceModelId']).value;
    if (deviceModelId) {
      this.deviceModelService.find(deviceModelId).subscribe(
        (res: HttpResponse<IDeviceModel>) => {
          this.linesCount = res.body.linesCount;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  private onDeviceModelChange() {
    // this.modalService.open(content, {}).result.then(
    //   () => {
    //     this.clearForeignSettingsAndLines();
    //     this.reloadModelOptions();
    //     this.updateLinesCount();
    //   },
    //   () => {
    //     this.editForm.get(['deviceModelId']).setValue(this.editForm.value['deviceModelId']);
    //   }
    // );

    this.modalService.open(DeviceModelChangeDialogComponent).result.then(
      () => {
        //         this.clearForeignSettingsAndLines();
        //         this.reloadModelOptions();
        //         this.updateLinesCount();
      },
      () => {
        this.editForm.get(['deviceModelId']).setValue(this.editForm.value['deviceModelId']);
      }
    );
  }

  private formatMacBeforeSave(mac) {
    return mac.replace(/[:.-]/g, '').toLowerCase();
  }

  private formatMacAfterLoad(mac) {
    if (mac) {
      return mac
        .match(/.{2}/g)
        .join('-')
        .toUpperCase();
    }
  }
}
