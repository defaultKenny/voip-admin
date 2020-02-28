import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';

@Component({
  selector: 'jhi-device-model-delete-dialog',
  templateUrl: './device-model-delete-dialog.component.html'
})
export class DeviceModelDeleteDialogComponent {
  deviceModel: IDeviceModel;

  constructor(
    protected deviceModelService: DeviceModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deviceModelService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'deviceModelListModification',
        content: 'Deleted an deviceModel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-device-model-delete-popup',
  template: ''
})
export class DeviceModelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeviceModelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deviceModel = deviceModel;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/device-model', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/device-model', { outlets: { popup: null } }]);
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
