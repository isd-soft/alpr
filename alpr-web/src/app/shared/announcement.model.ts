
export class AnnouncementModel {
  public id = 0;
  public title = '';
  public description = '';
  public priority = '';
  public date = null;

  constructor(id = 0, title = '', description = '', priority = '', date = null) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.date = date;
  }
}
