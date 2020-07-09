
export class AnnouncementModel {
  public title = '';
  public description = '';
  public priority = '';
  public date = null;

  constructor(title, description, priority, date) {
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.date = date;
  }
}
