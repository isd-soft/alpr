import {CompanyModel} from './company.model';

export class ParkingHistory {
  constructor(public companyName: string,
              public totalParkingSpots: number,
              public leftParkingSpots: number) {
  }
}
