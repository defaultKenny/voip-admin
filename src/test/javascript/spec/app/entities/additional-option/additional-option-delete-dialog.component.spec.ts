import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VoipAdminTestModule } from '../../../test.module';
import { AdditionalOptionDeleteDialogComponent } from 'app/entities/additional-option/additional-option-delete-dialog.component';
import { AdditionalOptionService } from 'app/entities/additional-option/additional-option.service';

describe('Component Tests', () => {
  describe('AdditionalOption Management Delete Component', () => {
    let comp: AdditionalOptionDeleteDialogComponent;
    let fixture: ComponentFixture<AdditionalOptionDeleteDialogComponent>;
    let service: AdditionalOptionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [AdditionalOptionDeleteDialogComponent]
      })
        .overrideTemplate(AdditionalOptionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdditionalOptionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdditionalOptionService);
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
