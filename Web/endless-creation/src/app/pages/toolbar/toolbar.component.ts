import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';
import { StrintgSearch } from 'src/app/model/string-search';
import { RouterEventsService } from 'src/app/service/router-events-service/router-events.service';
import { RouteState } from 'src/app/model/routeState';
import { GroupService } from 'src/app/service/group-service/group.service';
import { GroupVIEW } from 'src/app/model/group-view';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { SearchService } from 'src/app/service/search-service/search.service';
import { Search } from 'src/app/model/search';
import { expand } from 'rxjs/operators';

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
  groups!: GroupVIEW[];
  isAriaExpanded = false;

  searchParams = {
    isStringSearchActive: false,
    isTagSearchActive: false,
    scope: "Everywhere",
    searchString: "",
    searchTags: [1]
  };
  constructor(private authService: AuthenticationService,
              private routerEventService: RouterEventsService,
              private groupService: GroupService,
              private errorHandler: ErrorHandlerService,
              private searchService: SearchService
              ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);
    this.authService.loggedUser().subscribe(loggedUser => this.loggedUser = loggedUser);
    this.routerEventService.getRouteStatus().subscribe(routeState => this.routeState = routeState);
    this.searchService.getSearchBoolean().subscribe(isClearSearch =>{
      if(isClearSearch){
        this.clearSearchParams();
        this.stringSearch = "";
        console.log("działaaaa")
      }
      // this.searchService.clearSearchFalse();
    })
  }

  clearAllToolbar(){
    this.searchService.setClearToolbarFromHome();
  }

  clearSearchParams(){
    this.searchParams.isStringSearchActive = false,
    this.searchParams.isTagSearchActive = false,
    this.searchParams.scope ="Everywhere",
    this.searchParams.searchString = "",
    this.searchParams.searchTags = [1]
  }

  changeStringSearch(){
    this.searchParams.isStringSearchActive = true;
    this.searchParams.searchString = this.stringSearch;
    this.searchService.setSearch(this.searchParams);
  
  }

  

  changeScope(){
    if(this.searchParams.scope == "Everywhere"){
      this.searchParams.scope = "This Group";
    }else{
      this.searchParams.scope = "Everywhere";
    }
    this.searchService.setSearch(this.searchParams);
  }

  logoutUser(): void {
    this.authService.logout().subscribe();
  }

  getGroupsForUser(){
    this.groupService.getGroupsForUser()
    .subscribe(
      (response: any) => {
        this.groups = response;
        console.log(this.groups);
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }

  readAriaExpanded(){
      this.isAriaExpanded = !this.isAriaExpanded;
    }
  
  
}
