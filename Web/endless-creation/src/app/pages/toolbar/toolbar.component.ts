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
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoggedUserShortView } from 'src/app/model/logged-user-short-view';


@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  search: StrintgSearch = { search: "asdasdasd" };
  stringSearch = "";
  isLogged!: boolean;
  loggedUser!: String;
  routeState!: RouteState;
  groups!: GroupVIEW[];
  currentActiveGroupId!: number;
  isAriaExpanded = false;
  loginForm!: FormGroup;
  loggedUserShortInfo!: LoggedUserShortView;
  currentURL!: string;

  groupFilter = "";
  tagsFilter = "";

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
    private searchService: SearchService,
    private readonly fb: FormBuilder,
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);
    this.authService.loggedUser().subscribe(loggedUser => this.loggedUser = loggedUser);
    this.authService.loggedUserShortInfo().subscribe(loggedUserInfo => this.loggedUserShortInfo = loggedUserInfo);
    this.routerEventService.getRouteStatus().subscribe(routeState => this.routeState = routeState);
    this.routerEventService.getCurrentURL().subscribe(currentURL => this.currentURL = currentURL);
    this.searchService.getSearchBoolean().subscribe(isClearSearch => {
      if (isClearSearch) {
        this.clearSearchParams();
        this.stringSearch = "";
        console.log("działaaaa")
      }
      // this.searchService.clearSearchFalse();
    })
  }

  clearAllToolbar() {
    this.searchService.setClearToolbarFromHome();
  }

  clearSearchParams() {
    this.searchParams.isStringSearchActive = false,
      this.searchParams.isTagSearchActive = false,
      this.searchParams.scope = "Everywhere",
      this.searchParams.searchString = "",
      this.searchParams.searchTags = [1]
  }

  changeStringSearch() {
    this.searchParams.isStringSearchActive = true;
    this.searchParams.searchString = this.stringSearch;
    this.searchService.setSearch(this.searchParams);

  }

  changeTagSearch(){
    this.searchParams.isTagSearchActive = true;
    this.searchParams.searchString = this.stringSearch;
    this.searchService.setSearch(this.searchParams);
  }



  changeScope() {
    if (this.searchParams.scope == "Everywhere") {
      this.searchParams.scope = "This Group";
    } else {
      this.searchParams.scope = "Everywhere";
    }
    this.searchService.setSearch(this.searchParams);
  }

  logoutUser(): void {
    this.authService.logout().subscribe();
  }

  getGroupsForUser() {
    this.groupService.getGroupsForUser()
      .subscribe(
        (response: any) => {
          this.groups = response;
          this.finderGroupStatus();
        },
        (error) => {
          ;
          this.errorHandler.handleError(error);
        }
      )
  }


  finderGroupStatus() {
    switch (this.currentURL) {
      case "/dashboard":
        this.currentActiveGroupId = 0;
        break;
      case "/book":
        this.currentActiveGroupId = 0;
        break;
      default:

        const activeGroup: number = +this.currentURL.substring(7, 8)

        const findedGroup = this.groups.find(x => x.groupId === activeGroup);
        if (findedGroup) {
          this.currentActiveGroupId = findedGroup.groupId;
          break;
        }

    }
  }


  readAriaExpanded() {
    this.isAriaExpanded = !this.isAriaExpanded;
  }

  submitForm() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.get('username')?.value);
    } else {
      console.log('There is a problem with the form');
    }
  }






  onSubmit() {
    const passwordValue = this.loginForm.get('password')?.value;
    const usernameValue = this.loginForm.get('username')?.value;
    this.authService.login({ password: passwordValue, username: usernameValue }).subscribe(
      (response) => {
        console.log("udało się!!!");
      },
      (error) => {
        console.log("nie udało sie!!!");
      }
    )


  }

}
