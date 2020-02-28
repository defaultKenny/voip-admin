import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IResponsiblePerson, ResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from './responsible-person.service';

@Component({
  selector: 'jhi-responsible-person-update',
  templateUrl: './responsible-person-update.component.html'
})
export class ResponsiblePersonUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    firstName: [],
    middleName: [],
    lastName: [null, [Validators.required]],
    position: [],
    department: [],
    location: []
  });

  constructor(
    protected responsiblePersonService: ResponsiblePersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ responsiblePerson }) => {
      this.updateForm(responsiblePerson);
    });
  }

  updateForm(responsiblePerson: IResponsiblePerson) {
    this.editForm.patchValue({
      id: responsiblePerson.id,
      code: responsiblePerson.code,
      firstName: responsiblePerson.firstName,
      middleName: responsiblePerson.middleName,
      lastName: responsiblePerson.lastName,
      position: responsiblePerson.position,
      department: responsiblePerson.department,
      location: responsiblePerson.location
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const responsiblePerson = this.createFromForm();
    if (responsiblePerson.id !== undefined) {
      this.subscribeToSaveResponse(this.responsiblePersonService.update(responsiblePerson));
    } else {
      this.subscribeToSaveResponse(this.responsiblePersonService.create(responsiblePerson));
    }
  }

  private createFromForm(): IResponsiblePerson {
    return {
      ...new ResponsiblePerson(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      firstName: this.editForm.get(['firstName']).value,
      middleName: this.editForm.get(['middleName']).value,
      lastName: this.editForm.get(['lastName']).value,
      position: this.editForm.get(['position']).value,
      department: this.editForm.get(['department']).value,
      location: this.editForm.get(['location']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsiblePerson>>) {
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
