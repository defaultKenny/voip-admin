import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { SipAccountDetailComponent } from 'app/entities/sip-account/sip-account-detail.component';
import { SipAccount } from 'app/shared/model/sip-account.model';

describe('Component Tests', () => {
  describe('SipAccount Management Detail Component', () => {
    let comp: SipAccountDetailComponent;
    let fixture: ComponentFixture<SipAccountDetailComponent>;
    const route = ({ data: of({ sipAccount: new SipAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [SipAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SipAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SipAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sipAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
