import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceModelUpdateComponent } from 'app/entities/device-model/device-model-update.component';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { DeviceModel } from 'app/shared/model/device-model.model';

describe('Component Tests', () => {
  describe('DeviceModel Management Update Component', () => {
    let comp: DeviceModelUpdateComponent;
    let fixture: ComponentFixture<DeviceModelUpdateComponent>;
    let service: DeviceModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceModelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeviceModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceModelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceModel(123);
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
        const entity = new DeviceModel();
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
