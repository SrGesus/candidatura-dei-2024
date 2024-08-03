export default class CandidateDto {
  selected?: boolean
  istId?: number
  name?: string
  email?: string

  constructor(jsonObj: Partial<CandidateDto>) {
    Object.assign(this, jsonObj)
  }
}
