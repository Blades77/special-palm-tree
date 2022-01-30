import { Component, OnInit } from '@angular/core';
import { TileService } from '../../service/tile-service/tile.service';
import { GroupService } from '../../service/group-service/group.service';
import { GroupVIEW } from '../../model/group-view';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { TileVIEW } from 'src/app/model/tile-view';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import * as _ from 'lodash'


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
  
  currentActiveEndpoint = 0;
  pages=0;

  constructor(
    private tileService: TileService,
    private groupService: GroupService,
    private errorHandler: ErrorHandlerService,
    private authService: AuthenticationService,
    private router: Router){}

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);

    // this.getTiles();
    // this.getGroups();
    this.setCurrentRoute();
    this.loginBasedFunction();
  }

  

  private loginBasedFunction(){
    if(this.isLogged){
        this.getDashboardLoggedTiles("asc","createdAt",true);
    }else{
      this.getDashboardNotLoggedTiles("asc","createdAt");
    }
  }



  getDashboardLoggedTiles(order: string,sortBy: string,onlyUser: boolean){
    this.pages+=1
    this.tileService.getLoggedDashboardTiles(order,this.pages,sortBy,onlyUser)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 1;
    
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }

  getDashboardNotLoggedTiles(order: string,sortBy: string){
    this.pages+=1
    this.tileService.getNotLoggedDashboardTiles(order,this.pages,sortBy)
    .subscribe(
      (response: any) => {
        this.concatTiles(response);
        this.currentActiveEndpoint = 2;
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
        this.tiles.next(_.concat(currentTiles,this.newTiles))
  }


  onScroll() {
    console.log('scrolled')
    switch (this.currentActiveEndpoint) {
      case 0:
          console.log("Nothing");
          break;
      case 1:
          console.log("Dla logged");
          this.getDashboardLoggedTiles("asc","createdAt",true);
          break;
      case 2:
          console.log("Dla not logged");
          this.getDashboardNotLoggedTiles("asc","createdAt");
          break;
    }
  }

  private getTilesTest(){
    this.pages+=1
    this.tileService.getTilesTest(this.pages)

    .subscribe(
      (response: any) => {
        this.newTiles = response;
        console.log("Nowe tile"+this.newTiles);
        const currentTiles  = this.tiles.getValue()
      this.tiles.next(_.concat(currentTiles,this.newTiles))
    
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
      
    )
  }

  getTiles(){
    // if(!this.isLogged){
      this.tileService.getOrderedTilesFromApi("0","desc")
    .subscribe(
      (response: any) => {
        this.tiles = response;
        console.log(this.tiles);
        
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      })
    // }
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