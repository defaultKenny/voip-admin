import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { AdditionalOptionUpdateComponent } from 'app/entities/additional-option/additional-option-update.component';
import { AdditionalOptionService } from 'app/entities/additional-option/additional-option.service';
import { AdditionalOption } from 'app/shared/model/additional-option.model';

describe('Component Tests', () => {
  describe('AdditionalOption Management Update Component', () => {
    let comp: AdditionalOptionUpdateComponent;
    let fixture: ComponentFixture<AdditionalOptionUpdateComponent>;
    let service: AdditionalOptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [AdditionalOptionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdditionalOptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdditionalOptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdditionalOptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdditionalOption(123);
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
        const entity = new AdditionalOption();
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
