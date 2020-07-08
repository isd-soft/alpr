class detailsModel {
  constructor(public database: string = "") {
  }
}

class DataBaseModel {
  constructor(public status: string = "",
              public details: detailsModel = new detailsModel()) {
  }
}

class detailDiskModel {
  constructor(public total: number = 0,
              public free: number = 0) {
  }
}

class diskSpaceModel {
  constructor(public details: detailDiskModel = new detailDiskModel()) {
  }
}

class ComponentsModel {
  constructor(public db: DataBaseModel = new DataBaseModel(),
              public diskSpace: diskSpaceModel = new diskSpaceModel()) {
  }
}

export class HealthModel {
  constructor(public status: string = "",
              public components: ComponentsModel = new ComponentsModel()
  ){}
}
