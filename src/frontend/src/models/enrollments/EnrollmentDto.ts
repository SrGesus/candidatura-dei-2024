import type CandidateDto from "../candidates/CandidateDto"
import type StudentshipDto from "../studentships/StudentshipDto"

export default class EnrollmentDto {
  enrollmentId?: number;
  candidate?: CandidateDto;
  studentship?: StudentshipDto;
  grades?: Map<string, number>;

  constructor(jsonObj: Partial<EnrollmentDto>) {
    Object.assign(this, jsonObj)
  }
}
  
