import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceTypeDetailComponent } from 'app/entities/device-type/device-type-detail.component';
import { DeviceType } from 'app/shared/model/device-type.model';

describe('Component Tests', () => {
  describe('DeviceType Management Detail Component', () => {
    let comp: DeviceTypeDetailComponent;
    let fixture: ComponentFixture<DeviceTypeDetailComponent>;
    const route = ({ data: of({ deviceType: new DeviceType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeviceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
