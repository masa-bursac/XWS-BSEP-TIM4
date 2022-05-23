import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';


@Injectable()
export class JwtInterceptorComponent implements HttpInterceptor {
    constructor(private authenticationService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add auth header with jwt if user is logged in and request is to api url

        const currentUser = sessionStorage.getItem('username');

        const isLoggedIn = currentUser && localStorage.getItem('token');
       // const isApiUrl = request.url.startsWith(config.apiUrl);
        if (isLoggedIn/* && isApiUrl*/) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            });
        }

        return next.handle(request);
    }
}