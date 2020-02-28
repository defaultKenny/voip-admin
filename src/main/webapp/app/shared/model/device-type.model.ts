import { IDeviceModel } from 'app/shared/model/device-model.model';

export interface IDeviceType {
  id?: number;
  name?: string;
  deviceModels?: IDeviceModel[];
}

export class DeviceType implements IDeviceType {
  constructor(public id?: number, public name?: string, public deviceModels?: IDeviceModel[]) {}
}
