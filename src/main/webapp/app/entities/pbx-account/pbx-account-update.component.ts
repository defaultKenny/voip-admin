import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPbxAccount, PbxAccount } from 'app/shared/model/pbx-account.model';
import { PbxAccountService } from './pbx-account.service';
import { ISipAccount } from 'app/shared/model/sip-account.model';
import { SipAccountService } from 'app/entities/sip-account/sip-account.service';

@Component({
  selector: 'jhi-pbx-account-update',
  templateUrl: './pbx-account-update.component.html'
})
export class PbxAccountUpdateComponent implements OnInit {
  isSaving: boolean;

  sipaccounts: ISipAccount[];

  editForm = this.fb.group({
    id: [],
    username: [],
    pbxId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pbxAccountService: PbxAccountService,
    protected sipAccountService: SipAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pbxAccount }) => {
      this.updateForm(pbxAccount);
    });
    this.sipAccountService
      .query()
      .subscribe(
        (res: HttpResponse<ISipAccount[]>) => (this.sipaccounts = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(pbxAccount: IPbxAccount) {
    this.editForm.patchValue({
      id: pbxAccount.id,
      username: pbxAccount.username,
      pbxId: pbxAccount.pbxId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pbxAccount = this.createFromForm();
    if (pbxAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.pbxAccountService.update(pbxAccount));
    } else {
      this.subscribeToSaveResponse(this.pbxAccountService.create(pbxAccount));
    }
  }

  private createFromForm(): IPbxAccount {
    return {
      ...new PbxAccount(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      pbxId: this.editForm.get(['pbxId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPbxAccount>>) {
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

  trackSipAccountById(index: number, item: ISipAccount) {
    return item.id;
  }
}
