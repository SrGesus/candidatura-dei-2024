export default class StudentshipDto {
    id?: string
    startDate?: Date
    endDate?: Date
    pay?: number
    vacancies?: number
  
    constructor(jsonObj: Partial<StudentshipDto>) {
      Object.assign(this, jsonObj)
    }
  }
  