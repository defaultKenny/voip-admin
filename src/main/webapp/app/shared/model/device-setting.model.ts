export interface IDeviceSetting {
  id?: number;
  value?: string;
  deviceMac?: string;
  deviceId?: number;
  additionalOptionCode?: string;
  additionalOptionId?: number;
}

export class DeviceSetting implements IDeviceSetting {
  constructor(
    public id?: number,
    public value?: string,
    public deviceMac?: string,
    public deviceId?: number,
    public additionalOptionCode?: string,
    public additionalOptionId?: number
  ) {}
}
