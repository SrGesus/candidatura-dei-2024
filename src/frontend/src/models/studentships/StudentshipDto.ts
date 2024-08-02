export default class StudentshipDto {
    id?: number
    startDate?: Date
    endDate?: Date
    pay?: number
    vacancies?: number
  
    constructor(jsonObj: Partial<StudentshipDto>) {
      Object.assign(this, jsonObj)
    }
  }
  