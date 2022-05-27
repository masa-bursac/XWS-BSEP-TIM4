import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {

  public username: any;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getUsername();
  }

  private getUsername(): void {
    this.username = this.route.snapshot.params.username;
    console.log(this.username)
  }

}
