import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpResponseBase } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(
    private router: Router,
  ) { }

  public handleError(error: HttpErrorResponse){
    const endpoint = this.getEndpoint(error.url);

    switch (error.status) {
      case 404:
        this.handleError404(error);
        break;
    }

  }

  private handleError404(error: HttpErrorResponse){
    
    this.router.navigate(['/404']);
  }
  

  private getEndpoint(url: any): string {
    const split = url.split('/');
    return split[split.length - 1];
  }
}
