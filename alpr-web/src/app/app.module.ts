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
import {AddCarComponent} from './add-car/add-car.component';
import { HomeComponent } from './home/home.component';
import { NavigationComponent } from './navigation/navigation.component';
import {MatTabsModule} from '@angular/material/tabs';
import { ProfileComponent } from './profile/profile.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import {AuthenticationService} from './auth/authentication.service';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    UsersComponent,
    AddCarComponent,
    CarListComponent,
    HomeComponent,
    NavigationComponent,
    ProfileComponent,
    HeaderComponent,
    FooterComponent
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
    HttpClientModule

  ],
  providers: [UserService, CompanyService, CarService,
    FormExtractor, FormGenerator, AuthenticationService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
  ],

  bootstrap: [AppComponent],
  exports: [MatPaginatorModule]
})
export class AppModule {
}
