import { IDeviceSetting } from 'app/shared/model/device-setting.model';
import { IDeviceModel } from 'app/shared/model/device-model.model';

export interface IAdditionalOption {
  id?: number;
  code?: string;
  description?: string;
  deviceSettings?: IDeviceSetting[];
  deviceModels?: IDeviceModel[];
}

export class AdditionalOption implements IAdditionalOption {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public deviceSettings?: IDeviceSetting[],
    public deviceModels?: IDeviceModel[]
  ) {}
}
