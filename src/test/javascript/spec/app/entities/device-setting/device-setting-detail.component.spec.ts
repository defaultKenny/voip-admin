import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceSettingDetailComponent } from 'app/entities/device-setting/device-setting-detail.component';
import { DeviceSetting } from 'app/shared/model/device-setting.model';

describe('Component Tests', () => {
  describe('DeviceSetting Management Detail Component', () => {
    let comp: DeviceSettingDetailComponent;
    let fixture: ComponentFixture<DeviceSettingDetailComponent>;
    const route = ({ data: of({ deviceSetting: new DeviceSetting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceSettingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeviceSettingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceSettingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceSetting).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
