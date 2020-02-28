import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDeviceModel, DeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';
import { AdditionalOptionService } from 'app/entities/additional-option/additional-option.service';
import { IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from 'app/entities/device-type/device-type.service';

@Component({
  selector: 'jhi-device-model-update',
  templateUrl: './device-model-update.component.html'
})
export class DeviceModelUpdateComponent implements OnInit {
  isSaving: boolean;

  additionaloptions: IAdditionalOption[];

  devicetypes: IDeviceType[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    isConfigurable: [],
    linesCount: [],
    configFile: [],
    configFileContentType: [],
    firmwareFile: [],
    firmwareFileContentType: [],
    additionalOptions: [],
    deviceTypeId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected deviceModelService: DeviceModelService,
    protected additionalOptionService: AdditionalOptionService,
    protected deviceTypeService: DeviceTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      this.updateForm(deviceModel);
    });
    this.additionalOptionService
      .query()
      .subscribe(
        (res: HttpResponse<IAdditionalOption[]>) => (this.additionaloptions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.deviceTypeService
      .query()
      .subscribe(
        (res: HttpResponse<IDeviceType[]>) => (this.devicetypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(deviceModel: IDeviceModel) {
    this.editForm.patchValue({
      id: deviceModel.id,
      name: deviceModel.name,
      isConfigurable: deviceModel.isConfigurable,
      linesCount: deviceModel.linesCount,
      configFile: deviceModel.configFile,
      configFileContentType: deviceModel.configFileContentType,
      firmwareFile: deviceModel.firmwareFile,
      firmwareFileContentType: deviceModel.firmwareFileContentType,
      additionalOptions: deviceModel.additionalOptions,
      deviceTypeId: deviceModel.deviceTypeId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deviceModel = this.createFromForm();
    if (deviceModel.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceModelService.update(deviceModel));
    } else {
      this.subscribeToSaveResponse(this.deviceModelService.create(deviceModel));
    }
  }

  private createFromForm(): IDeviceModel {
    return {
      ...new DeviceModel(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      isConfigurable: this.editForm.get(['isConfigurable']).value,
      linesCount: this.editForm.get(['linesCount']).value,
      configFileContentType: this.editForm.get(['configFileContentType']).value,
      configFile: this.editForm.get(['configFile']).value,
      firmwareFileContentType: this.editForm.get(['firmwareFileContentType']).value,
      firmwareFile: this.editForm.get(['firmwareFile']).value,
      additionalOptions: this.editForm.get(['additionalOptions']).value,
      deviceTypeId: this.editForm.get(['deviceTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceModel>>) {
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

  trackAdditionalOptionById(index: number, item: IAdditionalOption) {
    return item.id;
  }

  trackDeviceTypeById(index: number, item: IDeviceType) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
