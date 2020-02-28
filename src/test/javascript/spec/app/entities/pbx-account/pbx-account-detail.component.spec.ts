import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { PbxAccountDetailComponent } from 'app/entities/pbx-account/pbx-account-detail.component';
import { PbxAccount } from 'app/shared/model/pbx-account.model';

describe('Component Tests', () => {
  describe('PbxAccount Management Detail Component', () => {
    let comp: PbxAccountDetailComponent;
    let fixture: ComponentFixture<PbxAccountDetailComponent>;
    const route = ({ data: of({ pbxAccount: new PbxAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [PbxAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PbxAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PbxAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pbxAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
