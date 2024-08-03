
export default class GradeParameterDto {
  id?: number;
  name?: string;
  weight?: number;
  studentshipId?: number;

  constructor(jsonObj: Partial<GradeParameterDto>) {
    Object.assign(this, jsonObj)
  }
}
    
  