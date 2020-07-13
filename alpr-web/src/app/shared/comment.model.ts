

export class CommentModel {
  public userEmail: string;
  public description: string;
  public date: any;
  public time: any;

  constructor(userEmail = '', description = '', date = null, time = null) {
    this.userEmail = userEmail;
    this.description = description;
    this.date = date;
    this.time = time;
  }
}
