

export class CommentModel {
  public userEmail: string;
  public description: string;
  public date: any;

  constructor(userEmail = '', description = '', date = null) {
    this.userEmail = userEmail;
    this.description = description;
    this.date = date;
  }
}
