import {Injectable} from '@angular/core';

@Injectable()
export class FileHandler {
  loadCarPhoto(files: FileList): Promise<any> {
    return new Promise<any>(((resolve, reject) => {
      if (files[0].size > 10485760) {
        reject('Only sizes less than 10 MB are accepted');
      }
      const mimeType = files[0].type;
      if (mimeType.match(/image\/*/) == null) {
        reject('Only images can be uploaded');
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(files[0]);
        reader.onload = (_event) => {
          return resolve(reader.result);
        };
      }
    }));
  }

  loadCompanyPhoto(files: FileList): Promise<any> {
    return new Promise<any>(((resolve, reject) => {
      if (files[0].size > 10485760) {
        reject('Only sizes less than 10 MB are accepted');
      }
      const mimeType = files[0].type;
      if (mimeType.match(/image\/*/) == null) {
        reject('Only images can be uploaded');
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(files[0]);
        reader.onload = (_event) => {
          return resolve(reader.result);
        };
      }
    }));
  }
}
