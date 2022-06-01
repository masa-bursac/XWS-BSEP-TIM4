import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  public decodedToken: any;
  public token: any;
  public caption: string = "";
  validateForm!: FormGroup;
  file!: File;

  constructor(private router: Router, private authService : AuthService, private fb: FormBuilder, private postService : PostService) { }

  ngOnInit(): void {
    this.getToken();
    this.validateForm = this.fb.group({
      file: [null, [Validators.required]],
      caption: [null, [Validators.required]]
    });
  }

  private getToken(): void {
    this.decodedToken = this.authService.getDataFromToken();
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup ovde");
      this.router.navigate(['landingPage']);
    }else {
      if(this.decodedToken.user_role === 'ADMIN'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['adminHomePage']);
      }
    }
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.caption = this.validateForm.value.caption

    let body = new FormData();
    body.append("file", this.file);
    body.append("caption", this.caption);
    body.append("userInfoId", this.decodedToken.id);

    if(this.validateForm.valid){
      this.postService.createPost(body).subscribe(data => { 
          alert("Post created!");
      }, error => {

      });
    }

  }

  fileChange(event: any) {
    // Instantiate an object to read the file content
    let reader = new FileReader();
    // when the load event is fired and the file not empty
    if(event.target.files && event.target.files.length > 0) {
      // Fill file variable with the file content
      this.file = event.target.files[0];
    }
  }

}
