import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { ErrorHandlerService } from '../service/error-handler-service/error-handler.service';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../service/authentication-service/authentication.service';
import { catchError } from 'rxjs/operators';
@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(
        private authenticationService: AuthenticationService,
        private errorHandler: ErrorHandlerService) {
        }
        

        intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
            if (this.authenticationService.hasToken()) {
              request = request.clone({
                setHeaders: { Authorization: 'Bearer ' + this.authenticationService.getAccessToken() },
              });
            }
            return next.handle(request).pipe(
              catchError((error) => {
                this.errorHandler.handleError(error);
                return throwError(error);
              })
            );
          }
}