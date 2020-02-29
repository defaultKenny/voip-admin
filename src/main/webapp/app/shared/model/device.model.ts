import { ISipAccount } from 'app/shared/model/sip-account.model';
import { IDeviceSetting } from 'app/shared/model/device-setting.model';
import { ProvProtocol } from 'app/shared/model/enumerations/prov-protocol.model';

export interface IDevice {
  id?: number;
  mac?: string;
  inventory?: string;
  location?: string;
  hostname?: string;
  webAccessLogin?: string;
  webAccessPassword?: string;
  dhcpEnabled?: boolean;
  ipAddress?: string;
  subnetMask?: string;
  defaultGateway?: string;
  dns1?: string;
  dns2?: string;
  provUrl?: string;
  provProtocol?: ProvProtocol;
  sipAccounts?: ISipAccount[];
  deviceSettingDTOs?: IDeviceSetting[];
  deviceModelName?: string;
  deviceModelId?: number;
  responsiblePersonId?: number;
}

export class Device implements IDevice {
  constructor(
    public id?: number,
    public mac?: string,
    public inventory?: string,
    public location?: string,
    public hostname?: string,
    public webAccessLogin?: string,
    public webAccessPassword?: string,
    public dhcpEnabled?: boolean,
    public ipAddress?: string,
    public subnetMask?: string,
    public defaultGateway?: string,
    public dns1?: string,
    public dns2?: string,
    public provUrl?: string,
    public provProtocol?: ProvProtocol,
    public sipAccounts?: ISipAccount[],
    public deviceSettingDTOs?: IDeviceSetting[],
    public deviceModelName?: string,
    public deviceModelId?: number,
    public responsiblePersonId?: number
  ) {
    this.dhcpEnabled = this.dhcpEnabled || false;
  }
}
