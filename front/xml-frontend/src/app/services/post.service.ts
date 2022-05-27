import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const post_url = 'http://localhost:8080/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  public getAllPublicPosts(): Observable<any>{
    return this.http.get(post_url+`/getAllPublic`);
  }
}
