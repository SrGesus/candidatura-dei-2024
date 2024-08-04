export default class StudentshipDto {
    id?: number
    startDate?: Date
    endDate?: Date
    pay?: number
    vacancies?: number
  
    constructor(jsonObj: Partial<StudentshipDto>) {
      Object.assign(this, jsonObj)
      this.startDate = jsonObj.startDate ? new Date(jsonObj.startDate) : undefined
      this.endDate = jsonObj.endDate ? new Date(jsonObj.endDate) : undefined
    }

    static default(): StudentshipDto {
      return new StudentshipDto({
        id: 0,
        startDate: new Date(),
        endDate: new Date(),
        pay: 0,
        vacancies: 0
      })
    }
  }
  