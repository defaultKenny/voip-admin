import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceSetting } from 'app/shared/model/device-setting.model';
import { DeviceSettingService } from './device-setting.service';

@Component({
  selector: 'jhi-device-setting-delete-dialog',
  templateUrl: './device-setting-delete-dialog.component.html'
})
export class DeviceSettingDeleteDialogComponent {
  deviceSetting: IDeviceSetting;

  constructor(
    protected deviceSettingService: DeviceSettingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deviceSettingService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'deviceSettingListModification',
        content: 'Deleted an deviceSetting'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-device-setting-delete-popup',
  template: ''
})
export class DeviceSettingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deviceSetting }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeviceSettingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deviceSetting = deviceSetting;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/device-setting', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/device-setting', { outlets: { popup: null } }]);
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
