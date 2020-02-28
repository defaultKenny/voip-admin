import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceTypeUpdateComponent } from 'app/entities/device-type/device-type-update.component';
import { DeviceTypeService } from 'app/entities/device-type/device-type.service';
import { DeviceType } from 'app/shared/model/device-type.model';

describe('Component Tests', () => {
  describe('DeviceType Management Update Component', () => {
    let comp: DeviceTypeUpdateComponent;
    let fixture: ComponentFixture<DeviceTypeUpdateComponent>;
    let service: DeviceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeviceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceType(123);
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
        const entity = new DeviceType();
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
