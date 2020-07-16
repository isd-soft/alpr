export class CompanyModel {
  constructor(public id: number = 1,
              public name: string = '',
              public nrParkingSpots: number = 0,
              public logo?: string) {
  }
}
