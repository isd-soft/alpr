import {Injectable} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationDialogComponent} from '../confirmation-dialog/confirmation-dialog.component';

@Injectable()
export class ConfirmationDialogService {
  constructor(private modalService: NgbModal) {}

  public confirm(
    title: string,
    message: string,
    btnConfirmText: string = 'Yes',
    btnCancelText: string = 'No',
    dialogSize: 'sm'|'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnConfirmText = btnConfirmText;
    modalRef.componentInstance.btnCancelText = btnCancelText;

    return modalRef.result;
  }
}
