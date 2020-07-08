import {Component, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {StatisticsService} from '../shared/statistics.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AppModel} from "../shared/app.model";
import {HealthModel} from "../shared/health.model";
import {CpuModel} from "../shared/cpu.model";
import {UptimeModel} from "../shared/uptime.model";
import {HttpRequestModel} from "../shared/httpRequest.model";
import {totalMemoryModel} from "../shared/totalMemory.model";
import {usedMemoryModel} from "../shared/usedMemory.model";

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
  appInfo: AppModel = new AppModel();
  healthInfo: HealthModel = new HealthModel();
  cpuInfo: CpuModel = new CpuModel();
  uptimeInfo: UptimeModel = new UptimeModel();
  httpInfo: HttpRequestModel = new HttpRequestModel();
  totalMemoryInfo: totalMemoryModel = new totalMemoryModel();
  usedMemoryInfo: usedMemoryModel = new usedMemoryModel();

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
        this.chartOptions.series = [response.allowedCars, response.rejectedCars];
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

    this.statisticsService.getInfo()
      .toPromise()
      .then(response => {
        console.log(response);
        this.appInfo = response.app;
        console.log(this.appInfo);
      })


    this.statisticsService.getHealth()
      .toPromise()
      .then(response => {
        console.log(response);
        this.healthInfo = response;
        console.log(this.healthInfo);
      })


    this.statisticsService.getCpuCount()
      .toPromise()
      .then(response => {
        console.log(response);
        this.cpuInfo = response;
        console.log(this.cpuInfo);
      })

    this.statisticsService.getUptime()
      .toPromise()
      .then(response => {
        console.log(response);
        this.uptimeInfo = response;
        console.log(this.uptimeInfo);
      })

    this.statisticsService.getHttpRequest()
      .toPromise()
      .then(response => {
        console.log(response);
        this.httpInfo = response;
        console.log(this.httpInfo);
      })

    this.statisticsService.getTotalMemory()
      .toPromise()
      .then(response => {
        console.log(response);
        this.totalMemoryInfo = response;
        console.log(this.totalMemoryInfo);
      })

    this.statisticsService.getUsedMemory()
      .toPromise()
      .then(response => {
        console.log(response);
        this.usedMemoryInfo = response;
        console.log(this.usedMemoryInfo);
      })
  }

}
