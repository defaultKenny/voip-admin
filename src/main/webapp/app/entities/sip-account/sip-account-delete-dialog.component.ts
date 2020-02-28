import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISipAccount } from 'app/shared/model/sip-account.model';
import { SipAccountService } from './sip-account.service';

@Component({
  selector: 'jhi-sip-account-delete-dialog',
  templateUrl: './sip-account-delete-dialog.component.html'
})
export class SipAccountDeleteDialogComponent {
  sipAccount: ISipAccount;

  constructor(
    protected sipAccountService: SipAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sipAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'sipAccountListModification',
        content: 'Deleted an sipAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sip-account-delete-popup',
  template: ''
})
export class SipAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sipAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SipAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sipAccount = sipAccount;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/sip-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/sip-account', { outlets: { popup: null } }]);
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
