import { Component, OnInit } from '@angular/core';
import {ConnectionURL} from "../shared/url";
import {PlanHandler} from "../utils/plan.handler";
import {ParkingPlanService} from "../shared/parking.plan.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-parking-plan',
  templateUrl: './parking-plan.component.html',
  styleUrls: ['./parking-plan.component.css']
})
export class ParkingPlanComponent implements OnInit {

  constructor( private planHandler: PlanHandler,
               private parkingPlanService: ParkingPlanService,
               private httpClient: HttpClient,) { }

  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;

  public onFileChanged(event) {
    console.log(event);
    this.selectedFile = event.target.files[0];
    this.onUpload();
  }

  onUpload() {
    console.log(this.selectedFile);

    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    this.httpClient.post(ConnectionURL.url + '/parkingplan/upload', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
          if (response.status === 200) {
            console.log('na na na')
            this.message = 'Image uploaded successfully';
            this.getImage();
          } else {
            this.message = 'Image not uploaded successfully';
          }
        }
      );
  }
  getImage() {
    this.httpClient.get(ConnectionURL.url + '/parkingplan/get')
      .subscribe(
        res => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.photo;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }
  removeUploadedPlan() {
    this.retrievedImage = null;
  }

  ngOnInit(): void {
    this.getImage();
  }

}
