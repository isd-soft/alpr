import {Component, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from 'ng-apexcharts';
import {StatisticsService} from '../shared/statistics.service';

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
  constructor(private statisticsService: StatisticsService) {
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
  }

}
