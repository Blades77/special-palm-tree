import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'matchingGroupsFilter'
})
export class MatchesCategoryPipe implements PipeTransform {
    transform(groups: Array<any>, filterValue: string): Array<any> {
        return groups.filter(group => group.groupName.includes(filterValue));
    }
}