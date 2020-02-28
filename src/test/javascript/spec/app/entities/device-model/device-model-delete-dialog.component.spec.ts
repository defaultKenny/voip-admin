import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceModelDeleteDialogComponent } from 'app/entities/device-model/device-model-delete-dialog.component';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';

describe('Component Tests', () => {
  describe('DeviceModel Management Delete Component', () => {
    let comp: DeviceModelDeleteDialogComponent;
    let fixture: ComponentFixture<DeviceModelDeleteDialogComponent>;
    let service: DeviceModelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceModelDeleteDialogComponent]
      })
        .overrideTemplate(DeviceModelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceModelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceModelService);
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
