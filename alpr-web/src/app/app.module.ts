import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RegistrationComponent} from './registration/registration.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {UserService} from './shared/user.service';
import {CompanyService} from './shared/company.service';
import {MatTableModule} from '@angular/material/table';
import {LoginComponent} from './login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDividerModule} from '@angular/material/divider';
import {MatDialogModule} from '@angular/material/dialog';
import {FormExtractor} from './utils/form.extractor';
import {FormGenerator} from './utils/form.generator';
import {UsersComponent} from './users/users.component';
import {JwtInterceptor} from './auth/jwt.interceptor';
import {ErrorInterceptor} from './auth/error.interceptor';
import {CarListComponent} from './car-list/car-list.component';
import {CarService} from './shared/car.service';
import {MatIconModule} from '@angular/material/icon';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {HomeComponent} from './home/home.component';
import {NavigationComponent} from './navigation/navigation.component';
import {CompaniesComponent} from './companies/companies.component';
import {MatTabsModule} from '@angular/material/tabs';
import {ProfileComponent} from './profile/profile.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {AuthenticationService} from './auth/authentication.service';
import {MatSidenavModule} from '@angular/material/sidenav';



// @ts-ignore
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DashboardComponent} from './dashboard/dashboard.component';
import {NgApexchartsModule} from 'ng-apexcharts';
import {StatisticsService} from './shared/statistics.service';
import {MatListModule} from '@angular/material/list';
import { AnnouncementComponent } from './announcement/announcement.component';
import {AnnouncementService} from './shared/announcement.service';
import {ScrollingModule} from '@angular/cdk/scrolling';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import {ConfirmationDialogService} from './shared/confirmation-dialog.service';
import { AddCommentDialogComponent } from './add-comment-dialog/add-comment-dialog.component';
import { ViewCommentDialogComponent } from './view-comment-dialog/view-comment-dialog.component';
import { AddAnnouncementDialogComponent } from './add-announcement-dialog/add-announcement-dialog.component';
import {FileHandler} from './utils/file.handler';
import {CompanyCarsComponent} from './company-cars/company-cars.component';
import { EditAnnouncementDialogComponent } from './edit-announcement-dialog/edit-announcement-dialog.component';
import {MatBadgeModule} from "@angular/material/badge";
import {PlanHandler} from "./utils/plan.handler";
import {ParkingPlanService} from "./shared/parking.plan.service";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MaterialFileInputModule} from "ngx-material-file-input";
import { ParkingPlanComponent } from './parking-plan/parking-plan.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    UsersComponent,
    CarListComponent,
    CompaniesComponent,
    HomeComponent,
    NavigationComponent,
    ProfileComponent,
    HeaderComponent,
    FooterComponent,
    CompanyCarsComponent,
    DashboardComponent,
    AnnouncementComponent,
    ConfirmationDialogComponent,
    AddCommentDialogComponent,
    ViewCommentDialogComponent,
    AddAnnouncementDialogComponent,
    EditAnnouncementDialogComponent,
    ParkingPlanComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    NoopAnimationsModule,
    MaterialModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    HttpClientModule,
    MatSnackBarModule,
    AppRoutingModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatTableModule,
    MatDividerModule,
    MatDialogModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatToolbarModule,
    MatTabsModule,
    HttpClientModule,
    MatSidenavModule,
    NgbModule,
    NgApexchartsModule,
    MatListModule,
    ScrollingModule,
    MatBadgeModule,
    MatProgressBarModule,
    MaterialFileInputModule


  ],
  providers: [UserService, CompanyService, CarService, AnnouncementService, ConfirmationDialogService,
    FormExtractor, FormGenerator, AuthenticationService, StatisticsService,
    FileHandler,PlanHandler,ParkingPlanService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},

  ],
  bootstrap: [AppComponent],
  exports: [MatPaginatorModule],
  entryComponents: [ConfirmationDialogComponent, AddCommentDialogComponent,
    ViewCommentDialogComponent,
    AddAnnouncementDialogComponent,
  EditAnnouncementDialogComponent]
})
export class AppModule {
}
