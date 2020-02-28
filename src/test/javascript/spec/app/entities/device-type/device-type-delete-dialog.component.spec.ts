import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceTypeDeleteDialogComponent } from 'app/entities/device-type/device-type-delete-dialog.component';
import { DeviceTypeService } from 'app/entities/device-type/device-type.service';

describe('Component Tests', () => {
  describe('DeviceType Management Delete Component', () => {
    let comp: DeviceTypeDeleteDialogComponent;
    let fixture: ComponentFixture<DeviceTypeDeleteDialogComponent>;
    let service: DeviceTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceTypeDeleteDialogComponent]
      })
        .overrideTemplate(DeviceTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceTypeService);
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
