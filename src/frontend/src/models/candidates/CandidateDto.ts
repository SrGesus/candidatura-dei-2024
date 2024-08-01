export default class CandidateDto {
  istId?: number
  name?: string
  email?: string

  constructor(jsonObj: Partial<CandidateDto>) {
    Object.assign(this, jsonObj)
  }
}
