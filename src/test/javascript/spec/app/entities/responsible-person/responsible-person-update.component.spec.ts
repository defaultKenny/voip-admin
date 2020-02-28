import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { ResponsiblePersonUpdateComponent } from 'app/entities/responsible-person/responsible-person-update.component';
import { ResponsiblePersonService } from 'app/entities/responsible-person/responsible-person.service';
import { ResponsiblePerson } from 'app/shared/model/responsible-person.model';

describe('Component Tests', () => {
  describe('ResponsiblePerson Management Update Component', () => {
    let comp: ResponsiblePersonUpdateComponent;
    let fixture: ComponentFixture<ResponsiblePersonUpdateComponent>;
    let service: ResponsiblePersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [ResponsiblePersonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResponsiblePersonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResponsiblePersonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResponsiblePersonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResponsiblePerson(123);
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
        const entity = new ResponsiblePerson();
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
