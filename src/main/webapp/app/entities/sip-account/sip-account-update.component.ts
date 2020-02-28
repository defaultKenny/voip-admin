import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISipAccount, SipAccount } from 'app/shared/model/sip-account.model';
import { SipAccountService } from './sip-account.service';
import { IPbxAccount } from 'app/shared/model/pbx-account.model';
import { PbxAccountService } from 'app/entities/pbx-account/pbx-account.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';

@Component({
  selector: 'jhi-sip-account-update',
  templateUrl: './sip-account-update.component.html'
})
export class SipAccountUpdateComponent implements OnInit {
  isSaving: boolean;

  pbxaccounts: IPbxAccount[];

  devices: IDevice[];

  editForm = this.fb.group({
    id: [],
    username: [],
    password: [],
    lineEnabled: [],
    lineNumber: [],
    isManuallyCreated: [],
    pbxAccountId: [],
    deviceId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sipAccountService: SipAccountService,
    protected pbxAccountService: PbxAccountService,
    protected deviceService: DeviceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sipAccount }) => {
      this.updateForm(sipAccount);
    });
    this.pbxAccountService.query({ 'sipAccountId.specified': 'false' }).subscribe(
      (res: HttpResponse<IPbxAccount[]>) => {
        if (!this.editForm.get('pbxAccountId').value) {
          this.pbxaccounts = res.body;
        } else {
          this.pbxAccountService
            .find(this.editForm.get('pbxAccountId').value)
            .subscribe(
              (subRes: HttpResponse<IPbxAccount>) => (this.pbxaccounts = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.deviceService
      .query()
      .subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sipAccount: ISipAccount) {
    this.editForm.patchValue({
      id: sipAccount.id,
      username: sipAccount.username,
      password: sipAccount.password,
      lineEnabled: sipAccount.lineEnabled,
      lineNumber: sipAccount.lineNumber,
      isManuallyCreated: sipAccount.isManuallyCreated,
      pbxAccountId: sipAccount.pbxAccountId,
      deviceId: sipAccount.deviceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sipAccount = this.createFromForm();
    if (sipAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.sipAccountService.update(sipAccount));
    } else {
      this.subscribeToSaveResponse(this.sipAccountService.create(sipAccount));
    }
  }

  private createFromForm(): ISipAccount {
    return {
      ...new SipAccount(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      password: this.editForm.get(['password']).value,
      lineEnabled: this.editForm.get(['lineEnabled']).value,
      lineNumber: this.editForm.get(['lineNumber']).value,
      isManuallyCreated: this.editForm.get(['isManuallyCreated']).value,
      pbxAccountId: this.editForm.get(['pbxAccountId']).value,
      deviceId: this.editForm.get(['deviceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISipAccount>>) {
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

  trackPbxAccountById(index: number, item: IPbxAccount) {
    return item.id;
  }

  trackDeviceById(index: number, item: IDevice) {
    return item.id;
  }
}
