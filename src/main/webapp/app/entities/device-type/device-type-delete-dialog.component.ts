import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';

@Component({
  selector: 'jhi-device-type-delete-dialog',
  templateUrl: './device-type-delete-dialog.component.html'
})
export class DeviceTypeDeleteDialogComponent {
  deviceType: IDeviceType;

  constructor(
    protected deviceTypeService: DeviceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deviceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'deviceTypeListModification',
        content: 'Deleted an deviceType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-device-type-delete-popup',
  template: ''
})
export class DeviceTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeviceTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deviceType = deviceType;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/device-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/device-type', { outlets: { popup: null } }]);
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
