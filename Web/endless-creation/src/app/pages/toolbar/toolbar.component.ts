import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';
import { StrintgSearch } from 'src/app/model/string-search';
import { RouterEventsService } from 'src/app/service/router-events-service/router-events.service';
import { RouteState } from 'src/app/model/routeState';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  search: StrintgSearch = {search:"asdasdasd"};
  stringSearch ="";
  isLogged!: boolean;
  loggedUser!: String;
  routeState!: RouteState;
  constructor(private authService: AuthenticationService,
              private routerEventService: RouterEventsService
              ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);
    this.authService.loggedUser().subscribe(loggedUser => this.loggedUser = loggedUser);
    this.routerEventService.getRouteStatus().subscribe(routeState => this.routeState = routeState);
  }

  logoutUser(): void {
    this.authService.logout().subscribe();
  }

}
