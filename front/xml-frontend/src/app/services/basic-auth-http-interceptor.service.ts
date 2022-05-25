import { Injectable } from '@angular/core';

import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthService } from './auth.service'

@Injectable({
  providedIn: 'root'
})
export class BasicAuthHttpInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (sessionStorage.getItem('username') && sessionStorage.getItem('basicauth')) {
      req = req.clone({
       
          setHeaders: { Authorization: `Bearer ${ localStorage.getItem('token')}` }
      })
    }

    return next.handle(req);

  }
}
