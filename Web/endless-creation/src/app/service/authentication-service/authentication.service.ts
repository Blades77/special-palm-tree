import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from 'src/app/model/user-view';
import { TokenVIEW } from 'src/app/model/token-view';
import { environment } from 'src/environments/environment';
import { UserStatus } from 'src/app/model/user-status';
import { LoggedUserShortView } from 'src/app/model/logged-user-short-view';
import { ErrorHandlerService } from '../error-handler-service/error-handler.service';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private baseUrl= environment.apiUrl;
  private AUTH_TOKEN = "JWT_TOKEN";
  private REFRESH_TOKEN="REFRESH_TOKEN";

  isLoginSubject = new BehaviorSubject<boolean>(false);
  loggedUserShortView = new BehaviorSubject<LoggedUserShortView>({username: "",userId: 0,userImageLink: ""});
  user = new BehaviorSubject<String>("");

  
  constructor(
    private router: Router,
    private http: HttpClient,
    private errorHandler: ErrorHandlerService
) {

  }

login(credentials: any): Observable<any> {
  console.log("jestem w serwisie logowania")
  return this.http.post<TokenVIEW>(this.baseUrl+"login",credentials).pipe(
    tap((token) => {
      console.log('jestem po d koniec')
      localStorage.setItem(this.AUTH_TOKEN, token.accessToken);
      localStorage.setItem(this.REFRESH_TOKEN, token.refreshToken);
      this.user.next(credentials.username);
      this.setUserShortInfo();
      this.isLoginSubject.next(true);
      }));
}

setUserShortInfo(){
  return this.http.get<LoggedUserShortView>(this.baseUrl+"user/basicData").subscribe(
    (response: any) => {
      console.log(response.userImageLink+" tu jest response")
      this.loggedUserShortView.next(response);
    },
    (error) =>{;
      this.errorHandler.handleError(error);
    }
  )
}


logout(): Observable<any> {
  return this.http.post(this.baseUrl + 'logout', {}).pipe(
    tap(() => this.removeTokenAndSetLayout()));
    
}

removeTokenAndSetLayout(): void {
  localStorage.removeItem(this.AUTH_TOKEN);
  localStorage.removeItem(this.REFRESH_TOKEN);
  this.user.next("");
  this.isLoginSubject.next(false);
  this.loggedUserShortView.next({username: "",userId: 0,userImageLink: ""});
  this.stopRefreshTokenTimer()
}

hasToken(): boolean{
  return !!localStorage.getItem(this.AUTH_TOKEN);
}

isUserLogedBool(){
  if(this.isLoginSubject.value){
    return true;
  }else{
    return false;
  }
}

isLoggedIn(): Observable<boolean> {
  return this.isLoginSubject.asObservable();
}

loggedUser(): Observable<String> {
  return this.user.asObservable();
}

loggedUserShortInfo(): Observable<LoggedUserShortView> {
  return this.loggedUserShortView.asObservable();
}

getAccessToken(): string {
  return String(localStorage.getItem(this.AUTH_TOKEN));

}

getRefreshToken(): string {
  return String(localStorage.getItem(this.REFRESH_TOKEN));

}
// refreshTokenWithAppIni(){
//   const refreshToken = localStorage.getItem(this.REFRESH_TOKEN);
//   if(refreshToken){
//     return this.refreshToken(refreshToken);
//   }
//   return this.isLoginSubject.asObservable();
  
// }

refreshToken(refreshToken: string){
  return this.http.post<TokenVIEW>(this.baseUrl+"user/refresh/",{refreshToken: "Bearer "+refreshToken}).pipe(
    tap((token) => {
      console.log('jestem po d koniec tokenowania refreshu')
      localStorage.setItem(this.AUTH_TOKEN, token.accessToken);
      localStorage.setItem(this.REFRESH_TOKEN, token.refreshToken);
      this.user.next("stringuje4444");
      this.isLoginSubject.next(true);
      this.setUserShortInfo();
      this.startRefreshTokenTimer();
      return this.isLoginSubject.asObservable();
      }));
}

private refreshTokenTimeout: any;

private startRefreshTokenTimer(){
  const refreshToken = localStorage.getItem(this.REFRESH_TOKEN);
  if(refreshToken){
    const expiry = (JSON.parse(atob(refreshToken.split('.')[1]))).exp;
    const expires = new Date(expiry * 1000);
    const timeout = expires.getTime() - Date.now() - (60 * 1000);
    this.refreshTokenTimeout = setTimeout(() => this.refreshToken(refreshToken).subscribe(), timeout);
    // return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }


}
   
private stopRefreshTokenTimer() {
  clearTimeout(this.refreshTokenTimeout);
}

}


