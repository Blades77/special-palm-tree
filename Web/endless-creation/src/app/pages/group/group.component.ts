import { Component, OnInit } from '@angular/core';
import { TileService } from '../../service/tile-service/tile.service';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { TileVIEW } from 'src/app/model/tile-view';
import { ActivatedRoute, Router } from '@angular/router';



@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements OnInit {
  tiles!: TileVIEW[];

  constructor(
    private tileService: TileService,
    private errorHandler: ErrorHandlerService,
    private activatedRoute: ActivatedRoute,) {}

  ngOnInit(): void {
    this.getTiles();
  }
   getTiles(){
    const groupId = String(this.activatedRoute.snapshot.paramMap.get('id'));
    this.tileService.getOrderedTilesFromApi(groupId,"desc")
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

}
