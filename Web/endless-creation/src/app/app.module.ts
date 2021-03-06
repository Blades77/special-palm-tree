import { NgModule, APP_INITIALIZER } from '@angular/core';
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
import { TimeaPassedPipe} from './helpers/timePassed.pipe';
import { LinkyModule } from 'ngx-linky';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { DotsComponent } from './components/dots/dots.component';
import { TooltipCustomClassComponent } from './components/tooltip-custom-class/tooltip-custom-class.component';
import { FormsModule } from '@angular/forms';
import { BookComponent } from './pages/book/book.component';
import { AuthenticationService } from './service/authentication-service/authentication.service';
import { appInitializer } from './helpers/appInitializer';
import { MatchesCategoryPipe } from './helpers/groupFilterPipe';



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
    TooltipCustomClassComponent,
    BookComponent,
    MatchesCategoryPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    LinkyModule,
    InfiniteScrollModule,
    FormsModule,
    
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, multi: true, deps: [AuthenticationService] },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
