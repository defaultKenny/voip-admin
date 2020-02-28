import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDeviceType, DeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';

@Component({
  selector: 'jhi-device-type-update',
  templateUrl: './device-type-update.component.html'
})
export class DeviceTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected deviceTypeService: DeviceTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deviceType }) => {
      this.updateForm(deviceType);
    });
  }

  updateForm(deviceType: IDeviceType) {
    this.editForm.patchValue({
      id: deviceType.id,
      name: deviceType.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deviceType = this.createFromForm();
    if (deviceType.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceTypeService.update(deviceType));
    } else {
      this.subscribeToSaveResponse(this.deviceTypeService.create(deviceType));
    }
  }

  private createFromForm(): IDeviceType {
    return {
      ...new DeviceType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
