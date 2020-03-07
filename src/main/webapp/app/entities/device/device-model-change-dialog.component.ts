import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-device-model-change-dialog',
  templateUrl: './device-model-change-dialog.component.html'
})
export class DeviceModelChangeDialogComponent implements OnInit {
  constructor(public modal: NgbActiveModal) {}

  ngOnInit() {}

  clear() {
    this.modal.dismiss('cancel');
  }
}
