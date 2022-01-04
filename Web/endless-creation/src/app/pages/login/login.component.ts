import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.authService.login({password: "1234", username: "string4"}).subscribe(
      (response) => {
        console.log("udało się!!!");
      },
      (error) => {
        console.log("nie udało sie!!!");
      }
    )
    

  }

  logoutUser(): void {
    this.authService.logout().subscribe();
  }

}
