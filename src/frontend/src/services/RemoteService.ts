import CandidateDto from '@/models/CandidateDto'
import EnrollmentDto from '@/models/EnrollmentDto'
import StudentshipDto from '@/models/StudentshipDto'
import axios from 'axios'


const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class RemoteService {


  // --------------------- Candidates ---------------------
  static async getCandidates(): Promise<CandidateDto[]> {
    return httpClient.get('/candidates/all').then((response) => {
      return response.data.map((candidate: any) => {
        return new CandidateDto(candidate);
      });
    });
  }

  static async getCandidate(istId: number): Promise<CandidateDto> {
    return httpClient.get(`/candidates/${istId}`).then((response) => {
      return new CandidateDto(response.data);
    });
  }

  static async candidateExists(istId: number): Promise<boolean> {
    return httpClient.get(`/candidates/${istId}`).then((response) => {
      return response.data !== null;
    }).catch((reason) => {
      if (reason && reason.response && reason.response.status === 404) {
        return false;
      } else {
        throw reason;
      }
    });
  }

  static async createCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.post('/candidates/create', candidate).then((response) => {
      return new CandidateDto(response.data);
    });
  }

  static async updateCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.put('/candidates/update', candidate).then((response) => {
      return new CandidateDto(response.data);
    });
  }

  static async deleteCandidate(candidate: CandidateDto): Promise<void> {
    return httpClient.delete(`/candidates/delete/${candidate.istId}`);
  }


  // -------------------- Studentships --------------------

  static async getStudentships(): Promise<StudentshipDto[]> {
    return httpClient.get('/studentships/all').then((response) => {
      return response.data.map((studentship: any) => {
        return new StudentshipDto(studentship);
      });
    });
  }

  static async getStudentship(id: number): Promise<StudentshipDto> {
    return httpClient.get(`/studentships/${id}`).then((response) => {
      return new StudentshipDto(response.data);
    });
  }

  static async createStudentship(studentship: StudentshipDto): Promise<StudentshipDto> {
    return httpClient.post('/studentships/create', studentship).then((response) => {
      return new StudentshipDto(response.data);
    });
  }

  static async updateStudentship(studentship: StudentshipDto): Promise<StudentshipDto> {
    return httpClient.put('/studentships/update', studentship).then((response) => {
      return new StudentshipDto(response.data);
    });
  }

  static async deleteStudentship(studentship: StudentshipDto): Promise<void> {
    return httpClient.delete(`/studentships/delete/${studentship.id}`);
  }


  // --------------------- Enrollments --------------------

  static async getEnrollments(): Promise<EnrollmentDto[]> {
    return httpClient.get('/enrollments/all').then((response) => {
      return response.data.map((enrollment: any) => {
        return new EnrollmentDto(enrollment);
      });
    });
  }

  static async getCandidateEnrollments(istId: number): Promise<EnrollmentDto[]> {
    return httpClient.get(`/enrollments/candidate/${istId}`).then((response) => {
      return response.data.map((enrollment: any) => {
        return new EnrollmentDto(enrollment);
      });
    });
  }

  static async getStudentshipEnrollments(studentshipId: number): Promise<EnrollmentDto[]> {
    return httpClient.get(`/enrollments/studentship/${studentshipId}`).then((response) => {
      return response.data.map((enrollment: any) => {
        return new EnrollmentDto(enrollment);
      });
    });
  }

  static async createEnrollments(candidates: number[], studentshipId: number): Promise<EnrollmentDto[]> {
    const enrollments = candidates.map((candidate) => {
      return new EnrollmentDto({
        candidate: { istId: candidate },
        studentship: { id: studentshipId }
      });
    });
    return httpClient.post('/enrollments/create/batch', enrollments).then((response) => {
      return response.data.map((enrollment: any) => {
        return new EnrollmentDto(enrollment);
      });
    });
  }

  static async updateEnrollment(enrollment: EnrollmentDto): Promise<EnrollmentDto> {
    return httpClient.put('/enrollments/update', enrollment).then((response) => {
      return new EnrollmentDto(response.data);
    });
  }

  static async updateEnrollments(enrollments: EnrollmentDto[]): Promise<EnrollmentDto[]> {
    return httpClient.put('/enrollments/update/batch', enrollments).then((response) => {
      return response.data.map((enrollment: any) => {
        return new EnrollmentDto(enrollment);
      });
    });
  }

  static async deleteEnrollment(enrollment: EnrollmentDto): Promise<void> {
    return httpClient.delete(`/enrollments/delete/${enrollment.candidate.istId}/${enrollment.studentship.id}`);
  }

  static async deleteEnrollments(candidates: number[], studentshipId: number): Promise<void> {
    const enrollments = candidates.map((candidate) => {
      return new EnrollmentDto({
        candidate: { istId: candidate },
        studentship: { id: studentshipId }
      });
    });
    console.log(enrollments);
    return httpClient.delete('/enrollments/delete/batch', { data: enrollments });
  }
}
