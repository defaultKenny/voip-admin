export interface ISipAccount {
  id?: number;
  username?: string;
  password?: string;
  lineEnabled?: boolean;
  lineNumber?: number;
  isManuallyCreated?: boolean;
  pbxAccountId?: number;
  deviceId?: number;
}

export class SipAccount implements ISipAccount {
  constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public lineEnabled?: boolean,
    public lineNumber?: number,
    public isManuallyCreated?: boolean,
    public pbxAccountId?: number,
    public deviceId?: number
  ) {
    this.lineEnabled = this.lineEnabled || false;
    this.isManuallyCreated = this.isManuallyCreated || false;
  }
}
