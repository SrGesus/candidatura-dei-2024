export default class EnrollmentId {
  candidateIstId?: number;
  studentshipId?: number;

  constructor(jsonObj: Partial<EnrollmentId>) {
    Object.assign(this, jsonObj)
  }
}
  
