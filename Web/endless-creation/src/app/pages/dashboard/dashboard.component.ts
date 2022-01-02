import { Component, OnInit } from '@angular/core';
import { TileService } from '../../service/tile-service/tile.service';
import { GroupService } from '../../service/group-service/group.service';
import { GroupVIEW } from '../../model/group-view';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { TileVIEW } from 'src/app/model/tile-view';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  tiles!: TileVIEW[];
  groups!: GroupVIEW[];

  constructor(
    private tileService: TileService,
    private groupService: GroupService,
    private errorHandler: ErrorHandlerService){}

  ngOnInit(): void {
    this.getTiles();
    this.getGroups();
  }

  getTiles(){
    this.tileService.getOrderedTilesFromApi("0","desc")
    .subscribe(
      (response: any) => {
        this.tiles = response;
        console.log(this.tiles);
        
      },
      (error) =>{;
        this.errorHandler.handleError(error);
        
      }
    )
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

}
