import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from "./material.module";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {UserService} from "./shared/user.service";
import {CompanyService} from "./shared/company.service";
import { OnUsersActionsComponent } from './on-users-actions/on-users-actions.component';
import { ViewUsersComponent } from './on-users-actions/view-users/view-users.component';
import { EditUserComponent } from './on-users-actions/edit-user/edit-user.component';
import { AddUserComponent } from './on-users-actions/add-user/add-user.component';
import {MatTableModule} from '@angular/material/table';
import { LoginComponent } from './login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {MatCheckboxModule} from '@angular/material/checkbox';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    RegistrationComponent,
    OnUsersActionsComponent,
    ViewUsersComponent,
    EditUserComponent,
    AddUserComponent
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
    MatTableModule
  ],
  providers: [UserService, CompanyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
