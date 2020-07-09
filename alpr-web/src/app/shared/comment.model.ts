

export class CommentModel {
  public userEmail: string;
  public description: string;

  constructor(userEmail = '', description = '') {
    this.userEmail = userEmail;
    this.description = description;
  }
}
