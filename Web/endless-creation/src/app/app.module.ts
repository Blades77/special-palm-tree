import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { GroupComponent } from './pages/group/group.component';
import { NoPageComponent } from './pages/no-page/no-page.component';
import { TileDetailsComponent } from './pages/tile-details/tile-details.component';
import { LoginComponent } from './pages/login/login.component';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { ToolbarComponent } from './pages/toolbar/toolbar.component';
import { TimeaPassedPipe } from './helpers/timePassed.pipe';
import { LinkyModule } from 'ngx-linky';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { DotsComponent } from './components/dots/dots.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    TileDetailsComponent,
    GroupComponent,
    NoPageComponent,
    LoginComponent,
    ToolbarComponent,
    TimeaPassedPipe,
    DotsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    LinkyModule,
    InfiniteScrollModule,
    
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
