import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'mac'
})
export class MacPipe implements PipeTransform {
  transform(value: any): any {
    if (value) {
      return value
        .match(/.{2}/g)
        .join('-')
        .toUpperCase();
    }
  }
}
