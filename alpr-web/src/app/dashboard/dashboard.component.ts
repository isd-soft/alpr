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

export type MorningChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
};

export type EveningChartOptions = {
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
  @ViewChild("donut-chart-morning") donutChartMorning: ChartComponent;
  public MorningDonutChartOptions: Partial<MorningChartOptions>;

  @ViewChild("donut-chart-evening") donutChartEvening: ChartComponent;
  public EveningDonutChartOptions: Partial<EveningChartOptions>;


  @ViewChild('chart-pie') pieChart: ChartComponent;
  public pieChartOptions: Partial<PieChartOptions> = {
    series: null,
    chart: null,
    labels: null,
    responsive: null
  };

  @ViewChild('chart-column') columnChart: ChartComponent;
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

  @ViewChild('chart-column-cars-entered') carsEnteredExitedChart: ChartComponent;
    public carsEnterExitedChartOptions: Partial<ColumnChartOptions> = {
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


  public carsPerCompanyChartOptions: Partial<PieChartOptions> = {
    series: null,
    chart: null,
    labels: null,
    responsive: null
  };


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

    this.statisticsService.getAllowedRejectedCarsLastWeek()
      .toPromise()
      .then(result => {
        this.initColumnChart(result);
      });

    this.statisticsService.getEnteredExitedCarsLastWeek()
      .toPromise()
      .then(result => {
        this.initCarsEnteredExited(result);
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

    this.statisticsService.getNumberScansMorning()
      .toPromise()
      .then(response => this.initCarsInTheMorningDonutChart(response));

    this.statisticsService.getNumberScansEvening()
      .toPromise()
      .then(response => this.initCarsInTheEveningDonutChart(response));

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

    this.statisticsService.getInfo()
      .toPromise()
      .then(response => {
        console.log(response);
        this.appInfo = response.app;
        console.log(this.appInfo);
      });


    this.statisticsService.getHealth()
      .toPromise()
      .then(response => {
        console.log(response);
        this.healthInfo = response;
        console.log(this.healthInfo);
      });


    this.statisticsService.getCpuCount()
      .toPromise()
      .then(response => {
        console.log(response);
        this.cpuInfo = response;
        console.log(this.cpuInfo);
      });

    this.statisticsService.getUptime()
      .toPromise()
      .then(response => {
        console.log(response);
        this.uptimeInfo = response;
        console.log(this.uptimeInfo);
      });

    this.statisticsService.getHttpRequest()
      .toPromise()
      .then(response => {
        console.log(response);
        this.httpInfo = response;
        console.log(this.httpInfo);
      });

    this.statisticsService.getTotalMemory()
      .toPromise()
      .then(response => {
        console.log(response);
        this.totalMemoryInfo = response;
        console.log(this.totalMemoryInfo);
      });

    this.statisticsService.getUsedMemory()
      .toPromise()
      .then(response => {
        console.log(response);
        this.usedMemoryInfo = response;
        console.log(this.usedMemoryInfo);
      });

    this.statisticsService.getNumberScansMorning()
      .toPromise()
      .then(response => {
        console.log(response);
      })
  }

  private initCarsEnteredExited(data: any[]): void {
    const keys: string[] = [];
    data.forEach(scanning => {
      if (keys.indexOf(scanning.scanDate) < 0) {
        keys.push(scanning.scanDate);
      }
    });
    const enteredValues: number[] = [];
    const exitedValues: number[] = [];
    keys.forEach(key => {
      const temp: any[] = data.filter(s => s.scanDate.localeCompare(key) === 0);
      const entered: number  = temp.filter(s => s.status === 'IN').length;
      const exited: number  = temp.length - entered;
      enteredValues.push(entered);
      exitedValues.push(exited);
    });

    this.carsEnterExitedChartOptions = {
      series: [
        {name: 'Entered', data: enteredValues}, {name: 'Exited', data: exitedValues}
      ],
      chart: {
        type: 'bar',
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

  private initCarsInTheMorningDonutChart(data): void {
    console.log(data)
    this.MorningDonutChartOptions = {
      series: data.map(entry => entry.cars),
      chart: {
        type: 'donut'
      },
      labels: data.map(entry => 'Hour : ' + entry.hour + ':00'),
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

  private initCarsInTheEveningDonutChart(data): void {
    console.log(data)
    this.EveningDonutChartOptions = {
      series: data.map(entry => entry.cars),
      chart: {
        type: 'donut'
      },
      labels: data.map(entry => 'Hour : ' + entry.hour + ':00'),
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
