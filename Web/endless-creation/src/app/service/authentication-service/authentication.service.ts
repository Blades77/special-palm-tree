import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from 'src/app/model/user-view';
import { TokenVIEW } from 'src/app/model/token-view';
import { environment } from 'src/environments/environment';
import { UserStatus } from 'src/app/model/user-status';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private baseUrl= environment.apiUrl;
  private AUTH_TOKEN = "JWT_TOKEN";
  private REFRESH_TOKEN="REFRESH_TOKEN";

  isLoginSubject = new BehaviorSubject<boolean>(this.hasToken());
  user = new BehaviorSubject<String>("");

  
  constructor(
    private router: Router,
    private http: HttpClient
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
      this.isLoginSubject.next(true);
      }));
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
}

hasToken(): boolean{
  return !!localStorage.getItem(this.AUTH_TOKEN);
}


isLoggedIn(): Observable<boolean> {
  return this.isLoginSubject.asObservable();
}

loggedUser(): Observable<String> {
  return this.user.asObservable();
}

getAccessToken(): string {
  return String(localStorage.getItem(this.AUTH_TOKEN));

}


}


