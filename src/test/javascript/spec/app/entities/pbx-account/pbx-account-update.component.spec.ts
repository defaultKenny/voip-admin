import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { PbxAccountUpdateComponent } from 'app/entities/pbx-account/pbx-account-update.component';
import { PbxAccountService } from 'app/entities/pbx-account/pbx-account.service';
import { PbxAccount } from 'app/shared/model/pbx-account.model';

describe('Component Tests', () => {
  describe('PbxAccount Management Update Component', () => {
    let comp: PbxAccountUpdateComponent;
    let fixture: ComponentFixture<PbxAccountUpdateComponent>;
    let service: PbxAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [PbxAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PbxAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PbxAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PbxAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PbxAccount(123);
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
        const entity = new PbxAccount();
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
