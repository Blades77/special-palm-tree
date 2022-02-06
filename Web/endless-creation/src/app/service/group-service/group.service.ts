import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { GroupVIEW } from 'src/app/model/group-view';

@Injectable({
  providedIn: 'root'
})
export class GroupService {
  private baseUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
  ) { }

  getGroups(): Observable<GroupVIEW>{
    return this.http.get<GroupVIEW>(this.baseUrl+'groups');
  }

  getGroupsForUser(): Observable<GroupVIEW>{
    return this.http.get<GroupVIEW>(this.baseUrl+'groups/user');
  }
  

}
