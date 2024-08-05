
export default class StudentshipDto {
  id?: number
  startDate: Date = new Date()
  endDate: Date = new Date()
  amount: number = 0
  vacancies: number = 0
  active: boolean = true
  // Can be sent undefined, is not null if received from backend
  gradeParameters?: {
    id?: number,
    name: string,
    weight: number
  }[]
  // Not on the backend
  totalWeight: number = 0

  constructor(jsonObj: Partial<StudentshipDto>) {
    Object.assign(this, jsonObj)
    this.startDate = new Date(this.startDate)
    this.endDate = new Date(this.endDate)
  }
}
