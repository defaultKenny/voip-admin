import { IDevice } from 'app/shared/model/device.model';
import { IAdditionalOption } from 'app/shared/model/additional-option.model';

export interface IDeviceModel {
  id?: number;
  name?: string;
  isConfigurable?: boolean;
  linesCount?: number;
  configFileContentType?: string;
  configFile?: any;
  firmwareFileContentType?: string;
  firmwareFile?: any;
  devices?: IDevice[];
  additionalOptions?: IAdditionalOption[];
  deviceTypeName?: string;
  deviceTypeId?: number;
}

export class DeviceModel implements IDeviceModel {
  constructor(
    public id?: number,
    public name?: string,
    public isConfigurable?: boolean,
    public linesCount?: number,
    public configFileContentType?: string,
    public configFile?: any,
    public firmwareFileContentType?: string,
    public firmwareFile?: any,
    public devices?: IDevice[],
    public additionalOptions?: IAdditionalOption[],
    public deviceTypeName?: string,
    public deviceTypeId?: number
  ) {
    this.isConfigurable = this.isConfigurable || false;
  }
}
