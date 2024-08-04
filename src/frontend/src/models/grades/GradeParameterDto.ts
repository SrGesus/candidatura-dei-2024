
export default class GradeParameterDto {
  id?: number;
  name?: string;
  weight?: number;
  studentshipId?: number;

  constructor(jsonObj: Partial<GradeParameterDto>) {
    Object.assign(this, jsonObj)
  }

  static default(): GradeParameterDto {
    return new GradeParameterDto({
      id: 0,
      name: '',
      weight: 0,
      studentshipId: 0
    })
  }
}
