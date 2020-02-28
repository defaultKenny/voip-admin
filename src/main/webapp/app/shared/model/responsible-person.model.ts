import { IDevice } from 'app/shared/model/device.model';

export interface IResponsiblePerson {
  id?: number;
  code?: string;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  position?: string;
  department?: string;
  location?: string;
  devices?: IDevice[];
}

export class ResponsiblePerson implements IResponsiblePerson {
  constructor(
    public id?: number,
    public code?: string,
    public firstName?: string,
    public middleName?: string,
    public lastName?: string,
    public position?: string,
    public department?: string,
    public location?: string,
    public devices?: IDevice[]
  ) {}
}
