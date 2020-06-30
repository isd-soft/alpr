export class CarModel {

  constructor(public id: number= 1,
              public licensePlate: string = '',
              public brand: string = '',
              public model: string = '',
              public color: string = '',
              public ownerEmail: string = '',
              public ownerName: string = '',
              public ownerTelephone: string = '',
              public status: string = ''

  ){

  }
  /*
  get licensePlate(): string {
          return this.licensePlate;
      }
  set licensePlate(value: string) {
          this.licensePlate = value;
      }
  get brand(): string {
           return this.brand;
       }
  set brand(value: string) {
           this.brand = value;
       }
  get model(): string {
            return this.model;
        }
  set model(value: string) {
           this.model = value;
        }
  get color(): string {
           return this.color;
         }
  set color(value: string) {
           this.color = value;
}
  get ownerEmail(): string {
            return this.ownerEmail;
        }
  set ownerEmail(value: string) {
            this.ownerEmail = value;
        }
       */
}
