
export default class GradeDto {
    gradeParameterId?: number;
    candidateIstId?: number;
    studentshipId?: number;
    grade?: number;
  
    constructor(jsonObj: Partial<GradeDto>) {
      Object.assign(this, jsonObj)
    }

    static default(): GradeDto {
      return new GradeDto({
        gradeParameterId: 0,
        candidateIstId: 0,
        studentshipId: 0,
        grade: 0
      })
    }
  }
      
    