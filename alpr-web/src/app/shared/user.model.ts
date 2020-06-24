
export class User {
  constructor(public id: number = 1,
              public email: string = "",
              public firstName: string = "",
              public lastName: string = "",
              public age: number = 0,
              public telephone: string = "",
              public password: string = "",
              public company: string = "") {
  }
}
