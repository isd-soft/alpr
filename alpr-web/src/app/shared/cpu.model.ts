class MeasurementModel {
  private _value : number
  constructor() {
  }

  get value(): number {
    return this._value;
  }

  set value(value: number) {
    this._value = value;
  }
}

export class CpuModel {
  private _measurements : MeasurementModel[];

  constructor(){}

  get measurements(): MeasurementModel[] {
    return this._measurements;
  }

  set measurements(value: MeasurementModel[]) {
    this._measurements = value;
  }
}
