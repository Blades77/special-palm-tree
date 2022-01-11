import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TileDetailsComponent } from './pages/tile-details/tile-details.component';
import { GroupComponent} from './pages/group/group.component';
import { NoPageComponent } from './pages/no-page/no-page.component';
import { LoginComponent } from './pages/login/login.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'group/:id', component: GroupComponent },
  { path: 'tile-detail/:id', component: TileDetailsComponent },
  { path: 'login', component: LoginComponent},
  { path: '404', component: NoPageComponent},
  { path: 'login', component: LoginComponent},
  { path: '**', redirectTo: '/404'},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
