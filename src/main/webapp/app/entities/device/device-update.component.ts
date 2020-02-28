import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDevice, Device } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html'
})
export class DeviceUpdateComponent implements OnInit {
  isSaving: boolean;

  devicemodels: IDeviceModel[];

  responsiblepeople: IResponsiblePerson[];

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
    responsiblePersonId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected deviceService: DeviceService,
    protected deviceModelService: DeviceModelService,
    protected responsiblePersonService: ResponsiblePersonService,
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
  }

  updateForm(device: IDevice) {
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
      responsiblePersonId: this.editForm.get(['responsiblePersonId']).value
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
}
