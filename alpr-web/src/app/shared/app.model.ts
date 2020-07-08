class JavaModel {
  constructor(public version: string = "") {
  }
}

export class AppModel {
  constructor(public name: string = "",
              public description: string = "",
              public version: string = "",
              public encoding: string = "",
              public java: JavaModel = new JavaModel()
  ){}
}
