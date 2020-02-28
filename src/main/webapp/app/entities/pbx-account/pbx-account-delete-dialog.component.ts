import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPbxAccount } from 'app/shared/model/pbx-account.model';
import { PbxAccountService } from './pbx-account.service';

@Component({
  selector: 'jhi-pbx-account-delete-dialog',
  templateUrl: './pbx-account-delete-dialog.component.html'
})
export class PbxAccountDeleteDialogComponent {
  pbxAccount: IPbxAccount;

  constructor(
    protected pbxAccountService: PbxAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pbxAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'pbxAccountListModification',
        content: 'Deleted an pbxAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pbx-account-delete-popup',
  template: ''
})
export class PbxAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pbxAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PbxAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pbxAccount = pbxAccount;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/pbx-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/pbx-account', { outlets: { popup: null } }]);
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
