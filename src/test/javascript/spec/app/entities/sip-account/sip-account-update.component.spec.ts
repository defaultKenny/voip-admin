import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { SipAccountUpdateComponent } from 'app/entities/sip-account/sip-account-update.component';
import { SipAccountService } from 'app/entities/sip-account/sip-account.service';
import { SipAccount } from 'app/shared/model/sip-account.model';

describe('Component Tests', () => {
  describe('SipAccount Management Update Component', () => {
    let comp: SipAccountUpdateComponent;
    let fixture: ComponentFixture<SipAccountUpdateComponent>;
    let service: SipAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [SipAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SipAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SipAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SipAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SipAccount(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SipAccount();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
