import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { AdditionalOptionDetailComponent } from 'app/entities/additional-option/additional-option-detail.component';
import { AdditionalOption } from 'app/shared/model/additional-option.model';

describe('Component Tests', () => {
  describe('AdditionalOption Management Detail Component', () => {
    let comp: AdditionalOptionDetailComponent;
    let fixture: ComponentFixture<AdditionalOptionDetailComponent>;
    const route = ({ data: of({ additionalOption: new AdditionalOption(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [AdditionalOptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AdditionalOptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdditionalOptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.additionalOption).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
