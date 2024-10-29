import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'limit',
  standalone: true
})
export class LimitPipe implements PipeTransform {

  transform(value: string, length: number, suffix: string = '...', suffixOverlap: boolean = true): string {
    return value.length <= length ? value : value.substring(0, suffixOverlap ? length - suffix.length : length) + suffix;
  }

}
