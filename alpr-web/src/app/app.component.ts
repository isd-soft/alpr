import { Component } from '@angular/core';
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'alpr-web';


  constructor(private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer) {
    // this.matIconRegistry.addSvgIcon( 'icon', this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/img/logo.svg"));

    // iconRegistry.addSvgIcon('icon',
    //   sanitizer.bypassSecurityTrustResourceUrl('assets/img/logo.svg'));

  }

}
