import CandidateDto from "./CandidateDto"
import StudentshipDto from "./StudentshipDto"

export default class EnrollmentDto {
    // When being sent to the backend only ids matter
    candidate: Partial<CandidateDto> & {istId: number} = {istId: 0}
    studentship: Partial<StudentshipDto> & {id: number} = {id: 0}
    accepted: boolean = false
    grades?: {[key: number]: number}
    // Not on the backend
    total?: number

    constructor(jsonObj: Partial<EnrollmentDto>) {
        Object.assign(this, jsonObj)
        Object.assign(this.studentship, new StudentshipDto(jsonObj.studentship ?? {}))
    }
}
