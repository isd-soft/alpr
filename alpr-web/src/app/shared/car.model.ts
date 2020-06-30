

export class CarModel {
 public id: number = 1;
 public licensePlate: string = "";
 public brand: string = "";
 public model: string = "";
 public color: string = "";
  constructor(public id: number,
              public licensePlate: string = "",
              public brand: string = "",
              public model: string = "",
              public color: string = "",
              public ownerEmail: string = " ",
              public ownerName: string = "",
              public ownerTelephone: string = "",
              public status: string

  ){
     this.id = id;
     this.licensePlate = licensePlate;
     this.brand = brand;
     this.model = model;
     this.color = color;

  }

}
