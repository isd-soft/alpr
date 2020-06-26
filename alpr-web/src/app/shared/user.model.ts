
export class User {
  public id: number = 1;
  public email: string = "";
  public firstName: string = "";
  public lastName: string = "";
  public age: number = 0;
  public telephoneNumber: string = "";
  public password: string = "";
  public company: string = "";
  constructor(id: number = 1,
              email: string = "",
              firstName: string = "",
              lastName: string = "",
              age: number = 0,
              telephoneNumber: string = "",
              password: string = "",
              company: string = "") {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.telephoneNumber = telephoneNumber;
    this.password = password;
    this.company = company;
  }

}
