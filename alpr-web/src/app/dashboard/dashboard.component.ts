import {Component, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {StatisticsService} from '../shared/statistics.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {scan} from 'rxjs/operators';

export type PieChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};

export type ColumnChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  legend: ApexLegend;
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @ViewChild('chart-pie') pieChart: ChartComponent;
  @ViewChild('chart-column') columnChart: ChartComponent;
  public pieChartOptions: Partial<PieChartOptions> = {
    series: null,
    chart: null,
    labels: null,
    responsive: null
  };
  public carsPerCompanyChartOptions: Partial<PieChartOptions> = {
    series: null,
    chart: null,
    labels: null,
    responsive: null
  };

  public columnChartOptions: Partial<ColumnChartOptions> = {
    series: null,
    chart: null,
    dataLabels: null,
    plotOptions: null,
    yaxis: null,
    xaxis: null,
    legend: null,
    fill: null,
    stroke: null,
    tooltip: null
  };
  registeredUsersNumber: number;
  registeredCarsNumber: number;
  scannedPlatesNumber: number;

  constructor(private statisticsService: StatisticsService,
              private snackBar: MatSnackBar) {
    this.statisticsService.getAllowedRejectedCarsLastWeek()
      .toPromise()
      .then(result => {
        this.initColumnChart(result);
      });
    this.statisticsService.getTotalAllowedRejectedCars()
      .toPromise()
      .then(response => {
        this.initPieChart(response);
      })
      .catch(error => console.log(error));
    this.statisticsService.getCarsPerCompany()
      .toPromise()
      .then(response => this.initCarsPerCompanyChart(response));
  }

  ngOnInit(): void {
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

  private initColumnChart(data: any[]): void {
    const keys: string[] = [];
    data.forEach(scanning => {
      if (keys.indexOf(scanning.scanDate) < 0) {
        keys.push(scanning.scanDate);
      }
    });
    const allowedValues: number[] = [];
    const rejectedValues: number[] = [];
    keys.forEach(key => {
      const temp: any[] = data.filter(s => s.scanDate.localeCompare(key) === 0);
      const allowed: number  = temp.filter(s => s.allowed).length;
      const rejected: number  = temp.length - allowed;
      allowedValues.push(allowed);
      rejectedValues.push(rejected);
    });
    this.columnChartOptions = {
      series: [
        {name: 'Allowed', data: allowedValues}, {name: 'Rejected', data: rejectedValues}
      ],
      chart: {
        type: 'bar',
        height: 350
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '55%',
          endingShape: 'rounded'
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: true,
        width: 2,
        colors: ['transparent']
      },
      xaxis: {
        categories: keys
      },
      yaxis: {
        title: {
          text: 'Nr. of cars',
        }
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter(val) {
            return val + ' cars';
          }
        }
      }
    };
  }

  private initPieChart(data): void {
    this.pieChartOptions = {
      series: [data.allowedCars, data.rejectedCars],
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

  private initCarsPerCompanyChart(data): void {
    this.carsPerCompanyChartOptions = {
      series: data.map(entry => entry.cars),
      chart: {
        width: 380,
        type: 'pie'
      },
      labels: data.map(entry => entry.name),
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

}
