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

  public createPost(body: FormData) : Observable<any>{ 
    return this.http.post(post_url + `/create`, body);
  }

  public getAllPosts(id:number): Observable<any>{
    return this.http.get(post_url + `/getAllPosts/${id}`);
  }

  public like(userId:number, postId:number): Observable<any>{
    return this.http.put(post_url + `/like/${userId}/${postId}`, null);
  }

  public dislike(userId:number, postId:number): Observable<any>{
    return this.http.put(post_url + `/dislike/${userId}/${postId}`, null);
  }

  public addComment(body:any): Observable<any>{
    return this.http.put(post_url + `/addComment`, body);
  }
}
