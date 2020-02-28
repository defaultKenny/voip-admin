import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VoipAdminTestModule } from '../../../test.module';
import { SipAccountDeleteDialogComponent } from 'app/entities/sip-account/sip-account-delete-dialog.component';
import { SipAccountService } from 'app/entities/sip-account/sip-account.service';

describe('Component Tests', () => {
  describe('SipAccount Management Delete Component', () => {
    let comp: SipAccountDeleteDialogComponent;
    let fixture: ComponentFixture<SipAccountDeleteDialogComponent>;
    let service: SipAccountService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [SipAccountDeleteDialogComponent]
      })
        .overrideTemplate(SipAccountDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SipAccountDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SipAccountService);
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
