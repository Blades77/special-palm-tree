import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/service/authentication-service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  constructor(private authService: AuthenticationService, private readonly fb: FormBuilder) {
    this.loginForm = this.fb.group({
      username: ['',[Validators.required]],
      password: ['',[Validators.required]]
    });

   }

  ngOnInit(): void {}

  submitForm() {
    if (this.loginForm.valid) {
        console.log(this.loginForm.get('username')?.value);
    } else {
        console.log('There is a problem with the form');
    }
  }






  onSubmit(){
    const passwordValue = this.loginForm.get('password')?.value;
    const usernameValue = this.loginForm.get('username')?.value;
    this.authService.login({password: passwordValue, username: usernameValue}).subscribe(
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