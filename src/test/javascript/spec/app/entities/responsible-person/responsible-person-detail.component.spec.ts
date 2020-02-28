import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { ResponsiblePersonDetailComponent } from 'app/entities/responsible-person/responsible-person-detail.component';
import { ResponsiblePerson } from 'app/shared/model/responsible-person.model';

describe('Component Tests', () => {
  describe('ResponsiblePerson Management Detail Component', () => {
    let comp: ResponsiblePersonDetailComponent;
    let fixture: ComponentFixture<ResponsiblePersonDetailComponent>;
    const route = ({ data: of({ responsiblePerson: new ResponsiblePerson(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [ResponsiblePersonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResponsiblePersonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResponsiblePersonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.responsiblePerson).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
