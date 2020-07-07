import {Component, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {StatisticsService} from '../shared/statistics.service';
import {MatSnackBar} from '@angular/material/snack-bar';

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @ViewChild('chart') chart: ChartComponent;
  public chartOptions: Partial<ChartOptions>;
  registeredUsersNumber: number;
  registeredCarsNumber: number;
  scannedPlatesNumber: number;

  constructor(private statisticsService: StatisticsService,
              private snackBar: MatSnackBar) {
    this.chartOptions = {
      series: [0, 0],
      chart: {
        width: 380,
        type: 'pie'
      },
      labels: ['Allowed', 'Rejected'],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }
      ]
    };

  }

  ngOnInit(): void {
    this.statisticsService.getTotalAllowedRejectedCars()
      .toPromise()
      .then(response => {
        this.chartOptions.series = [response.allowedCars + 1, response.rejectedCars + 1];
      })
      .catch(error => console.log(error));

    this.statisticsService.getCarsStatistics()
      .toPromise()
      .then(response => {
        this.registeredCarsNumber = response.carsNumber;
        this.scannedPlatesNumber = response.scansNumber;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });

    this.statisticsService.getUsersStatistics()
      .toPromise()
      .then(response => {
        this.registeredUsersNumber = response.peopleNumber;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

}
