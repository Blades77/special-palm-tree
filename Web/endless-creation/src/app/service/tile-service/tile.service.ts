import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { TileVIEW } from '../../model/tile-view';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TileService {
  private baseUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
  ) { }

  getTileFromApi(tileId: string): Observable<TileVIEW>{
    return this.http.get<TileVIEW>(this.baseUrl+'tile/'+ tileId);
  }

  getOrderedTilesFromApi(groupId :string,order :string): Observable<TileVIEW[]>{
    return this.http.get<TileVIEW[]>(this.baseUrl+'tiles/group/'+groupId+'/'+order);
  }

  doLike(tileId: number){
    console.log("jestem tu")
    return this.http.post(this.baseUrl+'tile/like/'+tileId,null);
  }
}
