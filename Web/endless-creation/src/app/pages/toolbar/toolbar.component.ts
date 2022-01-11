import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';
import { StringLiteralLike } from 'typescript';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  isLogged!: boolean;
  loggedUser!: String;
  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe(isLogged => this.isLogged = isLogged);
    this.authService.loggedUser().subscribe(loggedUser => this.loggedUser = loggedUser);
  }

  logoutUser(): void {
    this.authService.logout().subscribe();
  }

}
