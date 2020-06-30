

export class CarModel {
 public id: number = 1;
 public licensePlate: string = "";
 public brand: string = "";
 public model: string = "";
 public color: string = "";
  constructor(public id: number,
              public brand: string = "",
              public color: string = "",
              public licensePlate: string = "",
              public model: string = "",
              public owner: string = " ",
              public telephoneNumber: number,
              public InOut: string
){     this.id = id;
       this.licensePlate = licensePlate;
       this.brand = brand;
       this.model = model;
       this.color = color;

  }

}
