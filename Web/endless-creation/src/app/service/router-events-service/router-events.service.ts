import { Injectable } from '@angular/core';
import {Event, RouterEvent, Router, NavigationEnd} from '@angular/router';
import { filter } from "rxjs/internal/operators";
import { BehaviorSubject, Observable } from 'rxjs';
import { RouteState } from 'src/app/model/routeState';


@Injectable({
  providedIn: 'root'
})
export class RouterEventsService {

  isTilesActive = new BehaviorSubject<boolean>(true);
  routeState  = new BehaviorSubject<RouteState>({isTilesActive: true,isBooksActive: false,currentURL: ""});
  currentURL = new BehaviorSubject<string>("");
  public routeState2!: Observable<RouteState>;

  constructor(public router: Router) {
    router.events.pipe(
       filter((e: Event): e is RouterEvent => e instanceof NavigationEnd)
    ).subscribe((e: RouterEvent) => {
      this.checkRoute(e.url);
      this.currentURL.next(e.url);
    });

  }


  private checkRoute(url: string){
  
    switch (url) {
      case "/dashboard":
          this.routeState.next({isTilesActive: true,isBooksActive: false,currentURL: url});
          break;
      case "/book":
        this.routeState.next({isTilesActive: false,isBooksActive: true,currentURL: url});
          break;
      default:
          console.log("No such day exists!");
          break;
  }

  }
  getRouteStatus(): Observable<RouteState> {
    return this.routeState.asObservable();
  }

  getCurrentURL(): Observable<string>{
    return this.currentURL.asObservable();
  }
}

