import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceSettingUpdateComponent } from 'app/entities/device-setting/device-setting-update.component';
import { DeviceSettingService } from 'app/entities/device-setting/device-setting.service';
import { DeviceSetting } from 'app/shared/model/device-setting.model';

describe('Component Tests', () => {
  describe('DeviceSetting Management Update Component', () => {
    let comp: DeviceSettingUpdateComponent;
    let fixture: ComponentFixture<DeviceSettingUpdateComponent>;
    let service: DeviceSettingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceSettingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeviceSettingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceSettingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceSettingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceSetting(123);
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
        const entity = new DeviceSetting();
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
