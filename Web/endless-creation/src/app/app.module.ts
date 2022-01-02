import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { GroupComponent } from './pages/group/group.component';
import { NoPageComponent } from './pages/no-page/no-page.component';
import { TileDetailsComponent } from './pages/tile-details/tile-details.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    TileDetailsComponent,
    GroupComponent,
    NoPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
