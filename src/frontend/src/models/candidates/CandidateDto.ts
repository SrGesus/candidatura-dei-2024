export default class CandidateDto {
  selected?: boolean
  istId?: number
  name?: string
  email?: string

  constructor(jsonObj: Partial<CandidateDto>) {
    Object.assign(this, jsonObj)
  }

  static default(): CandidateDto {
    return new CandidateDto({
      selected: false,
      istId: 0,
      name: '',
      email: ''
    })
  }
}
