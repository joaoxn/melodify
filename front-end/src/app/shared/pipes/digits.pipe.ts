import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'digits',
  standalone: true
})
export class DigitsPipe implements PipeTransform {

  transform(value: number, length: number, paddingChar: string = '0'): string {
    return String(value).padStart(length, paddingChar);
  }

}
