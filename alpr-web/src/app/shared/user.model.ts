export class User {
  constructor(public email: string = '',
              public firstName: string = '',
              public lastName: string = '',
              public age: number = 0,
              public telephoneNumber: string = '',
              public password: string = '',
              public company: string = '',
              public role: string = '',
              public token?: string,
              public photo?: string) {
  }
}
