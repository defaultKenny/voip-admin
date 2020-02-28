import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdditionalOption } from 'app/shared/model/additional-option.model';
import { AdditionalOptionService } from './additional-option.service';

@Component({
  selector: 'jhi-additional-option-delete-dialog',
  templateUrl: './additional-option-delete-dialog.component.html'
})
export class AdditionalOptionDeleteDialogComponent {
  additionalOption: IAdditionalOption;

  constructor(
    protected additionalOptionService: AdditionalOptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.additionalOptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'additionalOptionListModification',
        content: 'Deleted an additionalOption'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-additional-option-delete-popup',
  template: ''
})
export class AdditionalOptionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ additionalOption }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdditionalOptionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.additionalOption = additionalOption;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/additional-option', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/additional-option', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
