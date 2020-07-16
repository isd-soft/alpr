import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {StatisticsService} from '../shared/statistics.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AppModel} from '../shared/app.model';
import {HealthModel} from '../shared/health.model';
import {CpuModel} from '../shared/cpu.model';
import {UptimeModel} from '../shared/uptime.model';
import {HttpRequestModel} from '../shared/httpRequest.model';
import {totalMemoryModel} from '../shared/totalMemory.model';
import {usedMemoryModel} from '../shared/usedMemory.model';
import {MatTableDataSource} from '@angular/material/table';
import {ParkingHistory} from '../shared/parking.history.model';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FileHandler} from "../utils/file.handler";
import {PlanHandler} from "../utils/plan.handler";
import {ParkingPlanService} from "../shared/parking.plan.service";
import {CarModel} from "../shared/car.model";
import {ParkingPlanModel} from "../shared/parking.plan.model";
import { HttpClient, HttpEventType} from "@angular/common/http";


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
  imageUrl: string = "/assets/img/parking.png";
  fileToUpload: File = null;
  imgURL : string = '/assets/img/parking.png';
  parkingPlan: ParkingPlanModel[];



  @ViewChild('donut-chart-morning') donutChartMorning: ChartComponent;
  public MorningDonutChartOptions: Partial<MorningChartOptions> = {
    series: null,
    chart: null,
    responsive: null,
    labels: null,
  };

  @ViewChild('donut-chart-evening') donutChartEvening: ChartComponent;
  public EveningDonutChartOptions: Partial<EveningChartOptions> = {
    series: null,
    chart: null,
    responsive: null,
    labels: null,
  };


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

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

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
  value = '';


  companiesColumnsToDisplay: string[] = ['companyName', 'totalParkingSpots',
    'leftParkingSpots', 'OccupiedNrParkingSpots', 'SpotsAmountIndicator'];
  histories: ParkingHistory[];
  historiesDataSource: MatTableDataSource<ParkingHistory> =
    new MatTableDataSource<ParkingHistory>(this.histories);


  labelDefault = 'Upload Photo';
  label: string = this.labelDefault;

  @ViewChild('planFileInput')
  planFileInput: ElementRef;


  constructor(private statisticsService: StatisticsService,
              private snackBar: MatSnackBar,
              private planHandler: PlanHandler,
              private parkingPlanService: ParkingPlanService,
              private httpClient: HttpClient,
              ) {}


  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;

//Gets called when the user selects an image
  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
    this.onUpload();
  }
  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    //Make a call to the Spring Boot Application to save the image
    this.httpClient.post('http://localhost:8080/parkingplan/upload', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
          if (response.status === 200) {
            console.log('na na na')
            this.message = 'Image uploaded successfully';
            this.getImage();
          } else {
            this.message = 'Image not uploaded successfully';
          }
        }
      );
  }
  //Gets called when the user clicks on retieve image button to get the image from back end
  getImage() {
    //Make a call to Spring Boot to get the Image Bytes.
    this.httpClient.get('http://localhost:8080/parkingplan/get')
      .subscribe(
        res => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }


  handlePlanInput(files: FileList) {
    const fileToUpload = files.item(0);

    this.planHandler.loadPlanPhoto(files)
      .then(result => {
        this.imgURL = result;
        this.label = fileToUpload.name;
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }



  removeUploadedPlan() {
    this.retrievedImage = null;
  }










  initCharts(): void {
    this.statisticsService.getParkingHistoryForToday()
      .toPromise()
      .then(histories => {
        this.histories = histories;
        this.historiesDataSource = new MatTableDataSource<ParkingHistory>(histories);
        this.historiesDataSource.paginator = this.paginator;
        this.historiesDataSource.sort = this.sort;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });

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
      });

    this.statisticsService.getCarsPerCompany()
      .toPromise()
      .then(response => this.initCarsPerCompanyChart(response));

    this.statisticsService.getNumberScansMorning()
      .toPromise()
      .then(response => this.initCarsInTheMorningDonutChart(response));

    this.statisticsService.getNumberScansEvening()
      .toPromise()
      .then(response => this.initCarsInTheEveningDonutChart(response));

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
        this.appInfo = response.app;
      });


    this.statisticsService.getHealth()
      .toPromise()
      .then(response => {
        this.healthInfo = response;
      });


    this.statisticsService.getCpuCount()
      .toPromise()
      .then(response => {
        this.cpuInfo = response;
      });

    this.statisticsService.getUptime()
      .toPromise()
      .then(response => {
        this.uptimeInfo = response;
      });

    this.statisticsService.getHttpRequest()
      .toPromise()
      .then(response => {
        this.httpInfo = response;
      });

    this.statisticsService.getTotalMemory()
      .toPromise()
      .then(response => {
        this.totalMemoryInfo = response;
      });

    this.statisticsService.getUsedMemory()
      .toPromise()
      .then(response => {
        this.usedMemoryInfo = response;
      });

    this.statisticsService.getNumberScansMorning()
      .toPromise()
      .then(response => {
        this.initCarsInTheMorningDonutChart(response);
      });

    this.statisticsService.getNumberScansEvening()
      .toPromise()
      .then(response => this.initCarsInTheEveningDonutChart(response));

    this.parkingPlanService.getParkingPlan()
      .toPromise()
      .then(response => {this.retrievedImage = response.photo})
      .catch();

  }

  ngOnInit(): void {
    this.initCharts();
    this.getImage();
  }


  handleFileInput(file: FileList){
    this.fileToUpload = file.item(0);
    //Show image preview
    var reader = new FileReader();
    reader.onload = (event: any) => {
      this.imageUrl = event.target.result;
    }
    reader.readAsDataURL(this.fileToUpload);
  }



  private initCarsEnteredExited(data: any[]): void {
    const keys: string[] = [];
    data.forEach(scanning => {
      if (keys.indexOf(scanning.scanDate.slice(0, 10)) < 0) {
        keys.push(scanning.scanDate.slice(0, 10));
      }
    });
    const enteredValues: number[] = [];
    const exitedValues: number[] = [];
    keys.forEach(key => {
      const temp: any[] = data.filter(s => s.scanDate.slice(0, 10).localeCompare(key) === 0);
      const entered: number = temp.filter(s => s.status === 'IN').length;
      const exited: number = temp.length - entered;
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
      if (keys.indexOf(scanning.scanDate.slice(0, 10)) < 0) {
        keys.push(scanning.scanDate.slice(0, 10));
      }
    });
    const allowedValues: number[] = [];
    const rejectedValues: number[] = [];
    keys.forEach(key => {
      const temp: any[] = data.filter(s => s.scanDate.slice(0, 10).localeCompare(key) === 0);
      const allowed: number = temp.filter(s => s.allowed).length;
      const rejected: number = temp.length - allowed;
      allowedValues.push(allowed);
      rejectedValues.push(rejected);
    });
    this.columnChartOptions = {
      series: [
        {name: 'Allowed', data: allowedValues}, {name: 'Rejected', data: rejectedValues}
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


  private initPieChart(data): void {
    this.pieChartOptions = {
      series: [data.allowedCars, data.rejectedCars],
      chart: {
        type: 'pie',
        toolbar: {
          show: true,
          offsetX: 0,
          offsetY: 0,
          tools: {
            download: true,
            selection: true,
            zoom: true,
            zoomin: true,
            zoomout: true,
            pan: true,
            customIcons: []
          },
          export: {
            csv: {
              filename: undefined,
              columnDelimiter: ',',
              headerCategory: 'category',
              headerValue: 'value',
              dateFormatter(timestamp) {
                return new Date(timestamp).toDateString();
              }
            }
          },
          autoSelected: 'zoom'
        },
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
        type: 'pie',
        toolbar: {
          show: true,
          offsetX: 0,
          offsetY: 0,
          tools: {
            download: true,
            selection: true,
            zoom: true,
            zoomin: true,
            zoomout: true,
            pan: true,
            customIcons: []
          },
          export: {
            csv: {
              filename: undefined,
              columnDelimiter: ',',
              headerCategory: 'category',
              headerValue: 'value',
              dateFormatter(timestamp) {
                return new Date(timestamp).toDateString();
              }
            }
          },
          autoSelected: 'zoom'
        },
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

  applyFilter(filterValue: string) {
    this.historiesDataSource.filter = filterValue.trim().toLowerCase();
  }

  clearInput() {
    this.historiesDataSource.filter = '';
  }

}
