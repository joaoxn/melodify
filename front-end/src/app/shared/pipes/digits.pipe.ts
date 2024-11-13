import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'digits',
  standalone: true
})
export class DigitsPipe implements PipeTransform {

  transform(value: number, length: number, paddingChar: string = '0', considerLengthOfLength: boolean = false): string {
    if (considerLengthOfLength) length = Math.log10(length) + 1;
    return String(value).padStart(length, paddingChar);
  }

}
