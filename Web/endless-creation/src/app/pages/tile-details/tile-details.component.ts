import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TileService } from 'src/app/service/tile-service/tile.service';
import { Location } from '@angular/common';
import { ErrorHandlerService } from 'src/app/service/error-handler-service/error-handler.service';
import { TileVIEW } from 'src/app/model/tile-view';


@Component({
  selector: 'app-tile-details',
  templateUrl: './tile-details.component.html',
  styleUrls: ['./tile-details.component.scss']
})
export class TileDetailsComponent implements OnInit {
  tile!: TileVIEW;
  constructor(
    private activatedRoute: ActivatedRoute,
    private tileService: TileService,
    private location: Location,
    public router: Router,
    private errorHandler: ErrorHandlerService,
  ) { }

  ngOnInit(): void {
    this.getTileById();
  }

  getTileById(){
    const tileId = String(this.activatedRoute.snapshot.paramMap.get('id'));
    this.tileService.getTileFromApi(tileId)
    .subscribe(
      (response: any) => {
        this.tile = response;
        console.log(this.tile);
      },
      (error) =>{;
        this.errorHandler.handleError(error);
      }
    )
  }


  goBack(): void{
    this.location.back();
  }

}
