import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponsiblePerson } from 'app/shared/model/responsible-person.model';
import { ResponsiblePersonService } from './responsible-person.service';

@Component({
  selector: 'jhi-responsible-person-delete-dialog',
  templateUrl: './responsible-person-delete-dialog.component.html'
})
export class ResponsiblePersonDeleteDialogComponent {
  responsiblePerson: IResponsiblePerson;

  constructor(
    protected responsiblePersonService: ResponsiblePersonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.responsiblePersonService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'responsiblePersonListModification',
        content: 'Deleted an responsiblePerson'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-responsible-person-delete-popup',
  template: ''
})
export class ResponsiblePersonDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ responsiblePerson }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ResponsiblePersonDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.responsiblePerson = responsiblePerson;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/responsible-person', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/responsible-person', { outlets: { popup: null } }]);
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
