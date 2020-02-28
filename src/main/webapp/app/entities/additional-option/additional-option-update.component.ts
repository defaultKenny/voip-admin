import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAdditionalOption, AdditionalOption } from 'app/shared/model/additional-option.model';
import { AdditionalOptionService } from './additional-option.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';

@Component({
  selector: 'jhi-additional-option-update',
  templateUrl: './additional-option-update.component.html'
})
export class AdditionalOptionUpdateComponent implements OnInit {
  isSaving: boolean;

  devicemodels: IDeviceModel[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    description: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected additionalOptionService: AdditionalOptionService,
    protected deviceModelService: DeviceModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ additionalOption }) => {
      this.updateForm(additionalOption);
    });
    this.deviceModelService
      .query()
      .subscribe(
        (res: HttpResponse<IDeviceModel[]>) => (this.devicemodels = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(additionalOption: IAdditionalOption) {
    this.editForm.patchValue({
      id: additionalOption.id,
      code: additionalOption.code,
      description: additionalOption.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const additionalOption = this.createFromForm();
    if (additionalOption.id !== undefined) {
      this.subscribeToSaveResponse(this.additionalOptionService.update(additionalOption));
    } else {
      this.subscribeToSaveResponse(this.additionalOptionService.create(additionalOption));
    }
  }

  private createFromForm(): IAdditionalOption {
    return {
      ...new AdditionalOption(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdditionalOption>>) {
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
