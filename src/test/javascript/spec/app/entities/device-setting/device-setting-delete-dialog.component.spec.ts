import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceSettingDeleteDialogComponent } from 'app/entities/device-setting/device-setting-delete-dialog.component';
import { DeviceSettingService } from 'app/entities/device-setting/device-setting.service';

describe('Component Tests', () => {
  describe('DeviceSetting Management Delete Component', () => {
    let comp: DeviceSettingDeleteDialogComponent;
    let fixture: ComponentFixture<DeviceSettingDeleteDialogComponent>;
    let service: DeviceSettingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceSettingDeleteDialogComponent]
      })
        .overrideTemplate(DeviceSettingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceSettingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceSettingService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
