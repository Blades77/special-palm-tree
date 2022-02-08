import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Search } from 'src/app/model/search';
import { TileVIEW } from '../../model/tile-view';
import { HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class SearchService {
private baseUrl = environment.apiUrl;
search = new BehaviorSubject<Search>({isStringSearchActive: false,isTagSearchActive: false,scope: "",searchString: "",searchTags: []})
isClearSearch = new BehaviorSubject<boolean>(false);
  constructor( private http: HttpClient,) { }


setSearch(search: Search){
  this.search.next(search);
}

getSearch(): Observable<Search>{
  return this.search.asObservable();
}

clearSearchTrue(){
  this.isClearSearch.next(true);
  console.log("działaaaa true")
}

getSearchBoolean(): Observable<boolean>{
  return this.isClearSearch.asObservable();
}



getSearchHotNewForString(type: string,page: number,searchString: string){
  console.log(this.baseUrl+"tile/dashboard/"+type+"/"+page+"/s/"+searchString)
  return this.http.get<TileVIEW>(this.baseUrl+"tile/dashboard/"+type+"/"+page+"/s/"+searchString);
}

getSearchHotNewForTags(type: string,page: number,tags: number[]){
  console.log(this.baseUrl+"tile/dashboard/"+type+"/"+page+"/t/"+tags)
  return this.http.get<TileVIEW>(this.baseUrl+"tile/dashboard/"+type+"/"+page+"/t/"+tags);
}

getSearcLikesForString(term: string,order: string,page: number,searchString: string){
  console.log(this.baseUrl+"tile/dashboard/likes/"+term+"/"+order+"/"+page+"/s/"+searchString);
  return this.http.get<TileVIEW>(this.baseUrl+"tile/dashboard/likes/"+term+"/"+order+"/"+page+"/s/"+searchString);
}

getSearcLikesForTags(term: string,order: string,page: number,tags: number[]){
  console.log(this.baseUrl+"tile/dashboard/likes/"+term+"/"+order+"/"+page+"/t/"+tags);
  return this.http.get<TileVIEW>(this.baseUrl+"tile/dashboard/likes/"+term+"/"+order+"/"+page+"/t/"+tags);
}

}
