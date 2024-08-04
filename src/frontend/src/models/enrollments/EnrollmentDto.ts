import CandidateDto from "../candidates/CandidateDto"
import StudentshipDto from "../studentships/StudentshipDto"

export default class EnrollmentDto {
  enrollmentId?: number;
  candidate?: CandidateDto;
  studentship?: StudentshipDto;
  grades?: { [key: number]: number };
  total?: number;


  constructor(jsonObj: Partial<EnrollmentDto>) {
    Object.assign(this, jsonObj)
  }

  static default(): EnrollmentDto {
    return new EnrollmentDto({
      enrollmentId: 0,
      candidate: CandidateDto.default(),
      studentship: StudentshipDto.default(),
      grades: {}
    })
  }
}
  
