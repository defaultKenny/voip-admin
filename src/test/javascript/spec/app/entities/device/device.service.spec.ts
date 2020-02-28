import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DeviceService } from 'app/entities/device/device.service';
import { IDevice, Device } from 'app/shared/model/device.model';
import { ProvProtocol } from 'app/shared/model/enumerations/prov-protocol.model';

describe('Service Tests', () => {
  describe('Device Service', () => {
    let injector: TestBed;
    let service: DeviceService;
    let httpMock: HttpTestingController;
    let elemDefault: IDevice;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DeviceService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Device(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        ProvProtocol.FTP
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Device', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Device(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Device', () => {
        const returnedFromService = Object.assign(
          {
            mac: 'BBBBBB',
            inventory: 'BBBBBB',
            location: 'BBBBBB',
            hostname: 'BBBBBB',
            webAccessLogin: 'BBBBBB',
            webAccessPassword: 'BBBBBB',
            dhcpEnabled: true,
            ipAddress: 'BBBBBB',
            subnetMask: 'BBBBBB',
            defaultGateway: 'BBBBBB',
            dns1: 'BBBBBB',
            dns2: 'BBBBBB',
            provUrl: 'BBBBBB',
            provProtocol: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Device', () => {
        const returnedFromService = Object.assign(
          {
            mac: 'BBBBBB',
            inventory: 'BBBBBB',
            location: 'BBBBBB',
            hostname: 'BBBBBB',
            webAccessLogin: 'BBBBBB',
            webAccessPassword: 'BBBBBB',
            dhcpEnabled: true,
            ipAddress: 'BBBBBB',
            subnetMask: 'BBBBBB',
            defaultGateway: 'BBBBBB',
            dns1: 'BBBBBB',
            dns2: 'BBBBBB',
            provUrl: 'BBBBBB',
            provProtocol: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Device', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
