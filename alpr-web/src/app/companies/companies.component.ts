import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {FormGenerator} from '../utils/form.generator';
import {FormExtractor} from '../utils/form.extractor';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {FormGroup} from '@angular/forms';
import {CompanyService} from '../shared/company.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {CompanyModel} from '../shared/company.model';
import {DomSanitizer} from '@angular/platform-browser';
import {FileHandler} from '../utils/file.handler';

@Component({
  selector: 'app-users',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css'],
})
export class CompaniesComponent implements OnInit, AfterViewInit {

  columnsToDisplay = ['name', 'nrParkingSpots', 'logo', 'actions'];
  addCompanyForm: FormGroup = this.formGenerator.generateCompanyAddForm();
  editCompanyForm: FormGroup;
  companies: CompanyModel[] = [];
  companiesDataSource: MatTableDataSource<CompanyModel> =
    new MatTableDataSource<CompanyModel>(this.companies);
  editedCompany: CompanyModel;
  value = '';
  companyPhotoToView: any;
  companyPhotoToEdit: any;
  companyPhotoToAdd: any;

  defaultUploadInputLabel = 'Upload Photo';
  uploadInputLabel: string = this.defaultUploadInputLabel;

  @ViewChild('companyAddFileInput')
  companyAddFileInput: ElementRef;

  @ViewChild('companyEditFileInput')
  companyEditFileInput: ElementRef;

  constructor(
    private companyService: CompanyService,
    private router: Router,
    private formGenerator: FormGenerator,
    private formExtractor: FormExtractor,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private sanitizer: DomSanitizer,
    private fileHandler: FileHandler) {
  }

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  ngOnInit() {
    this.loadCompanies();
    this.companyService.getAll()
      .subscribe(companies => this.companies = companies);
  }

  loadCompanies() {
    this.companyService.getAll()
      .subscribe(companies => {
        this.companies = companies;
        this.updateTable(this.companies);
      });
  }

  updateTable(companies: CompanyModel[]) {
    this.companiesDataSource = new MatTableDataSource<CompanyModel>(companies);
    this.companiesDataSource.paginator = this.paginator;
    this.companiesDataSource.sort = this.sort;
  }

  onEdit(editCompanyTemplate, companyModel: CompanyModel) {
    this.editedCompany = companyModel;
    if (companyModel.logo) {
      this.companyPhotoToEdit = this.base64PhotoToUrl(companyModel.logo);
    } else {
      this.companyPhotoToEdit = null;
    }
    this.uploadInputLabel = this.defaultUploadInputLabel;
    this.editCompanyForm = this.formGenerator
      .generateCompanyEditForm(this.editedCompany);
    this.openTemplate(editCompanyTemplate);
  }

  onDelete(companyModel: CompanyModel) {
    this.companyService.removeById(companyModel.id).toPromise()
      .then(_ => {
        this.snackBar.open('Successfully deleted', 'OK', {duration: 3000});
        this.companies.splice(this.companies.indexOf(companyModel), 1);
        this.updateTable(this.companies);
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  onAdd(addCompanyTemplate) {
    this.openTemplate(addCompanyTemplate);
  }

  addCompany() {
    if (this.addCompanyForm.valid) {
      const companyModel: CompanyModel = this.formExtractor.extractCompany(this.addCompanyForm);
      companyModel.logo = this.companyPhotoToAdd;
      this.companyService.add(companyModel).toPromise()
        .then(_ => {
          this.snackBar.open('Success', 'OK', {duration: 3000});
          this.loadCompanies();
        })
        .catch(error => {
          this.handleError(error);
        });
    } else {
      this.snackBar.open('Fill all the required fields, please',
        'OK', {duration: 4000});
    }
  }

  handleError(httpError: string): void {
    this.snackBar.open(httpError, 'ok', {duration: 4000});
  }


  updateCompany() {
    const companyModel: CompanyModel = this.formExtractor.extractCompany(this.editCompanyForm);
    companyModel.id = this.editedCompany.id;
    companyModel.logo = this.companyPhotoToEdit;

    this.companyService.update(companyModel)
      .toPromise()
      .then(_ => {
        this.snackBar.open('Successfully updated', 'OK', {duration: 3000});
        this.loadCompanies();
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  ngAfterViewInit(): void {
    this.companyService.getAll().toPromise()
      .then(companies => {
        this.companies = companies;
        this.companiesDataSource = new MatTableDataSource<CompanyModel>(this.companies);
        this.companiesDataSource.paginator = this.paginator;
        this.companiesDataSource.sort = this.sort;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  private openTemplate(template) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(template, dialogConfig);
  }

  applyFilter(filterValue: string) {
    this.companiesDataSource.filter = filterValue.trim().toLowerCase();
  }

  clearInput() {
    this.companiesDataSource.filter = '';
  }

  private base64PhotoToUrl(base64Photo: string) {
    return this.sanitizer.bypassSecurityTrustUrl('data:Image/*;base64,' +
      base64Photo);
  }

  onCompanyIconClick(viewCompanyTemplate, company: CompanyModel) {
    if (company.logo) {
      this.companyPhotoToView = this.base64PhotoToUrl(company.logo);
    } else {
      this.companyPhotoToView = null;
    }
    this.openTemplate(viewCompanyTemplate);
  }

  handleEditCompanyFileInput(files: FileList) {
    this.fileHandler.loadCompanyPhoto(files)
      .then(result => {
        this.companyPhotoToEdit = result;
        this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
        this.companyEditFileInput.nativeElement.value = '';
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }

  handleAddCompanyFileInput(files: FileList) {
    this.fileHandler.loadCompanyPhoto(files)
      .then(result => {
        this.companyPhotoToAdd = result;
        this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
        this.companyAddFileInput.nativeElement.value = '';
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }

  removeUploadedCompany() {
    this.companyAddFileInput.nativeElement.value = '';
    this.companyEditFileInput.nativeElement.value = '';
  }

  setDefaultCompanyPhoto() {
    this.companyPhotoToEdit = null;
    this.companyPhotoToAdd = null;
    this.uploadInputLabel = this.defaultUploadInputLabel;
  }
}
