import { Component, OnInit } from '@angular/core';
import { TileService } from '../../service/tile-service/tile.service';
import { GroupService } from '../../service/group-service/group.service';
import { GroupVIEW } from '../../model/group-view';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { TileVIEW } from 'src/app/model/tile-view';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import * as _ from 'lodash';
import { Search } from 'src/app/model/search';
import { SearchService } from 'src/app/service/search-service/search.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public isCollapsed = false;
  tiles = new BehaviorSubject<TileVIEW[]>([]);
  newTiles!: TileVIEW[];
  groups!: GroupVIEW[];
  isLogged!: boolean;
  currentRoute!: string;
  isNotEndOFData = true;
  oldTile!: TileVIEW;
  activeTerm = "All Time";
  activeTermValue = "allTime";
  activeSearchParam = "createdAt";
  order = "desc";
  isUrlChanged = false;
  newOrHot = "new";
  search!: Search;

  //clear page i przy concacie ustawić ze jak zaszła zmiana kafelkow np z newest na oldest to zeby je nadpisywało całkiem

  searchParams = {
    newest: true,
    hot: false,
    likes: false
  };

  term = {
    allTime: true,
    today: false,
    week: false,
    month: false,
    year: false
  };

  
  currentActiveEndpoint = 0;
  pages=0;

  constructor(
    private tileService: TileService,
    private groupService: GroupService,
    private errorHandler: ErrorHandlerService,
    private authService: AuthenticationService,
    private searchService: SearchService,
    private router: Router){}

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);
    this.searchService.getSearch().subscribe(search => {
      this.search = search;
      console.log("Subscirbe wywołuje doSearch()")
      this.doSearch();
    });
    this.searchService.getClearToolbarFromHome().subscribe(home =>{
      if(home==true){
        this.clearSearch();
        this.searchService.setClearToolbarFromHomeFalse();
      }
    })

    this.setCurrentRoute();
    this.getDashboardNewOrHot(this.newOrHot,this.pages);
  }

  doSearch(){
    console.log("Log z ciała doSearch()")
    if(this.search.isStringSearchActive){
      this.searchString();
    }else if(this.search.isTagSearchActive){
      this.searchTags();
    }
  }

  clearSearch(){
    console.log("Wywoałnie clearSearch()")
    this.searchService.clearSearchTrue();
    this.isUrlChanged = true;
    this.markSearchParam(0);
    this.onScroll();
  }


  searchString(){
    console.log("Search by String")
    this.isUrlChanged = true;
    this.markSearchParam(0);
    this.onScroll();
  }
  searchTags(){
    console.log("Search by tags")
    this.isUrlChanged = true;
    this.markSearchParam(0);
    this.onScroll();
  }

  setSearchEndpoint(){
    if(this.searchParams.newest || this.searchParams.hot){
      this.currentActiveEndpoint = 1;
    }else if(this.searchParams.likes){
      this.currentActiveEndpoint = 2;
    }
  }



  getDashboardNewOrHot(type: string, page: number){
    this.tileService.getDashBoardNewOrHot(type,page)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 1;
        this.pages+=1;
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }


  getDashboardNewOrHotWithStringSearch(type: string, page: number){
    this.searchService.getSearchHotNewForString(type,page,this.search.searchString)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 1;
        this.pages+=1;
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }


  getDashboardNewOrHotWithTagSearch(type: string, page: number){
    this.searchService.getSearchHotNewForTags(type,page,this.search.searchString)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 1;
        this.pages+=1;
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }

  getDashboardLikes(term: string, order: string, page: number){
    this.tileService.getDashBoardLikes(term,order,page)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 2;
        console.log("testuje paggess"+this.pages)
        this.pages+=1;
        console.log("testuje pagesss2"+this.pages)
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }

  getDashboardLikesWithStringSearch(term: string, order: string, page: number){
    this.searchService.getSearcLikesForString(term,order,page,this.search.searchString)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 2;
        console.log("testuje paggess"+this.pages)
        this.pages+=1;
        console.log("testuje pagesss2"+this.pages)
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }


  getDashboardLikesWithTagSearch(term: string, order: string, page: number){
    this.searchService.getSearcLikesForTags(term,order,page,this.search.searchString)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 2;
        console.log("testuje paggess"+this.pages)
        this.pages+=1;
        console.log("testuje pagesss2"+this.pages)
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }

  private concatTiles(response: any){
    this.newTiles = response;
        const currentTiles  = this.tiles.getValue()
        const newLastTile = currentTiles[currentTiles.length-1];
        this.oldTile = newLastTile;
        if(this.oldTile === newLastTile){
          this.isNotEndOFData = false;
        }
        if(this.isUrlChanged){
          this.tiles.next(this.newTiles);
          this.isUrlChanged = false;
        }else{
          this.tiles.next(_.concat(currentTiles,this.newTiles))
        }
  }


  onScroll() {
    console.log('scrolled')
    switch (this.currentActiveEndpoint) {
      case 0:
          console.log("Nothing");
          break;
      case 1:
          console.log("Dla new or hot");
          if(this.search.isStringSearchActive == false && this.search.isTagSearchActive == false){
            this.getDashboardNewOrHot(this.newOrHot,this.pages);
            console.log("New or hot");
          }else if(this.search.isStringSearchActive){
            this.getDashboardNewOrHotWithStringSearch(this.newOrHot,this.pages);
            console.log("New or hot search by string");
          }else if(this.search.isTagSearchActive){
            this.getDashboardNewOrHotWithTagSearch(this.newOrHot,this.pages);
            console.log("New or hot search by tags");
          }

          break;
      case 2:
          console.log("Dla likes");
          if(this.search.isStringSearchActive == false && this.search.isTagSearchActive == false){
            this.getDashboardLikes(this.activeTermValue,this.order,this.pages);
            console.log("Likes")
          }else if(this.search.isStringSearchActive){
            console.log("Likes search by string");
            this.getDashboardLikesWithStringSearch(this.activeTermValue,this.order,this.pages);
          }else if(this.search.isTagSearchActive){
            console.log("Likes search by tags")
            this.getDashboardLikesWithTagSearch(this.activeTermValue,this.order,this.pages);
          }
          break;
    }
  }




  markSearchParam(serachNumber: number){
    this.pages = 0;
    this.isUrlChanged = true;
    this.clearSearchParam();
    switch(serachNumber){
      case 0:
        this.searchParams.newest = true;
        this.currentActiveEndpoint = 1;
        this.newOrHot = "new";
        break;
      case 1:
        this.searchParams.hot = true;
        this.currentActiveEndpoint = 1;
        this.newOrHot = "hot";
        break;
      case 2:
        this.searchParams.likes = true;
        this.activeSearchParam = "likes";
        this.currentActiveEndpoint = 2;
        this.order = "desc";
        break;    
    }
    this.onScroll();
  }

  clearSearchParam(){
    this.searchParams.newest = false;
    this.searchParams.hot = false;
    this.searchParams.likes = false;
  }

  markTerm(termNumber: number){
    this.pages = 0;
    this.clearTerm();
    this.isUrlChanged = true;
    switch(termNumber){
      case 0:
        this.term.allTime  = true;
        this.activeTerm = "All Time";
        this.activeTermValue = "alltime";
        break;
      case 1:
        this.term.today = true;
        this.activeTerm = "Today";
        this.activeTermValue="today";
        break;
      case 2:
        this.term.week = true;
        this.activeTerm = "This week";
        this.activeTermValue="week";
        break;  
      case 3:
        this.term.month = true;
        this.activeTerm = "This Month";
        this.activeTermValue="month";
        break;
      case 4:
        this.term.year = true;
        this.activeTerm = "This year";
        this.activeTermValue= "year";
        break;  
  }
  this.onScroll();
}

  clearTerm(){
    this.term.allTime = false;
    this.term.today = false;
    this.term.week = false;
    this.term.month = false;
    this.term.year = false;
  }



  getGroups(){
    this.groupService.getGroups()
    .subscribe(
      (response: any) => {
        this.groups = response;
        
      },
      (error) =>{;
        this.errorHandler.handleError(error);
        
      }
    )
  }
  copyLink(tileId: number){
    const link = "http://localhost:4200/tile-detail/"+tileId;
    navigator.clipboard.writeText(link);
  }

  getDimensionsByFind(id: number){
    return
  }

  setCurrentRoute(){
    this.currentRoute = this.router.url;
  }

  doSave(tileId: number){
    this.tileService.doSave(tileId)
    .subscribe(
      (response: any) => {
        if(response === true){
          const currentTiles = this.tiles.getValue();
          var isSaved = currentTiles.find(x => x.tileId === tileId);
          if(isSaved){
            isSaved.userSavedTile  = !isSaved.userSavedTile
          }

          this.tiles.next(currentTiles);
        }
        
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      })
    }



  doLike(tileId: number){
    this.tileService.doLike(tileId)
    .subscribe(
      (response: any) => {
        if(response === true){
          console.log("Wbijam do ifa");
          const currentTiles = this.tiles.getValue()
          var tile = currentTiles.find(x => x.tileId === tileId)
          if(tile){
            tile.tileLikedByTheUser =!tile.tileLikedByTheUser
          }
          if(tile?.tileLikedByTheUser){
            tile.likesCount+=1
          }else{
            if(tile)
            tile.likesCount-=1
          }
          this.tiles.next(currentTiles);
        }
        
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      })
  }

}