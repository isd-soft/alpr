export class Car {
 public id: number = 1;
 public licensePlate: string = "";
 public brand: string = "";
 public model: string = "";
 public color: string = "";
    constructor(id: number = 1,
                licensePlate: string = "",
                brand: string = "",
                model: string = "",
                color: string = "") {
             this.id = id;
             this.licensePlate = licensePlate;
             this.brand = brand;
             this.model = model;
             this.color = color;
    }
  }
