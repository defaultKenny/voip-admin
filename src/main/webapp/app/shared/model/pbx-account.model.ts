export interface IPbxAccount {
  id?: number;
  username?: string;
  pbxId?: string;
  sipAccountId?: number;
}

export class PbxAccount implements IPbxAccount {
  constructor(public id?: number, public username?: string, public pbxId?: string, public sipAccountId?: number) {}
}
