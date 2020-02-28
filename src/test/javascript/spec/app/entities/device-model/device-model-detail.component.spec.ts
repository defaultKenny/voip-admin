import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoipAdminTestModule } from '../../../test.module';
import { DeviceModelDetailComponent } from 'app/entities/device-model/device-model-detail.component';
import { DeviceModel } from 'app/shared/model/device-model.model';

describe('Component Tests', () => {
  describe('DeviceModel Management Detail Component', () => {
    let comp: DeviceModelDetailComponent;
    let fixture: ComponentFixture<DeviceModelDetailComponent>;
    const route = ({ data: of({ deviceModel: new DeviceModel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VoipAdminTestModule],
        declarations: [DeviceModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeviceModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceModel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
