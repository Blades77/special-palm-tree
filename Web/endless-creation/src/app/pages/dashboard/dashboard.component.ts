import { Component, OnInit } from '@angular/core';
import { TileService } from '../../service/tile-service/tile.service';
import { GroupService } from '../../service/group-service/group.service';
import { GroupVIEW } from '../../model/group-view';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { TileVIEW } from 'src/app/model/tile-view';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public isCollapsed = false;
  tiles!: TileVIEW[];
  groups!: GroupVIEW[];
  isLogged!: boolean;
  currentRoute!: string;


  constructor(
    private tileService: TileService,
    private groupService: GroupService,
    private errorHandler: ErrorHandlerService,
    private authService: AuthenticationService,
    private router: Router){}

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);
    this.getTiles();
    this.getGroups();
    this.setCurrentRoute();
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
          console.log("Wbijam do ifa tilllllesave");
          const isSaved = this.tiles.find(x => x.tileId === tileId)?.userSavedTile
          this.tiles.find(x => x.tileId === tileId).userSavedTile = !isSaved;
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
          const isLiked = this.tiles.find(x => x.tileId === tileId)?.tileLikedByTheUser
          this.tiles.find(x => x.tileId === tileId).tileLikedByTheUser = !isLiked;
          if(!isLiked){
            this.tiles.find(x => x.tileId === tileId).likesCount+=1
          }else{
            this.tiles.find(x => x.tileId === tileId).likesCount-=1
          }
        }
        
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      })
  }


}
