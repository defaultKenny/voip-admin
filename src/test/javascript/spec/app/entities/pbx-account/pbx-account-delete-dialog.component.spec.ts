import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VoipAdminTestModule } from '../../../test.module';
import { PbxAccountDeleteDialogComponent } from 'app/entities/pbx-account/pbx-account-delete-dialog.component';
import { PbxAccountService } from 'app/entities/pbx-account/pbx-account.service';

describe('Component Tests', () => {
  describe('PbxAccount Management Delete Component', () => {
    let comp: PbxAccountDeleteDialogComponent;
    let fixture: ComponentFixture<PbxAccountDeleteDialogComponent>;
    let service: PbxAccountService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [PbxAccountDeleteDialogComponent]
      })
        .overrideTemplate(PbxAccountDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PbxAccountDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PbxAccountService);
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
