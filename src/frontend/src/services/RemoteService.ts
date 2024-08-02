import axios from 'axios'

import MaterialDto from '@/models/materials/MaterialDto'
import CandidateDto from '@/models/candidates/CandidateDto'
import StudentshipDto from '@/models/studentships/StudentshipDto'

const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class RemoteService {

  // ------------------ Studentship ------------------
  static async getStudentships(): Promise<StudentshipDto[]> {
    return httpClient.get('/studentships/all').then((response) => {
      return response.data.map((studentship: any) => {
        return new StudentshipDto(studentship)
      })
    })
  }

  static async getStudentship(istId: number): Promise<StudentshipDto | null> {
    return httpClient.get(`/studentships/get/${istId}`).then((response) => {
      if (response.data) {
        return new StudentshipDto(response.data)
      } else {
        return null
      }
    })
    .catch(() => {
      return null
    })
  }
  
  static async createStudentship(studentship: StudentshipDto): Promise<StudentshipDto> {
    return httpClient.post('/studentships/create', studentship).then((response) => {
      return new StudentshipDto(response.data)
    })
  }

  static async updateStudentship(studentship: StudentshipDto): Promise<StudentshipDto> {
    return httpClient.put('/studentships/update', studentship).then((response) => {
      return new StudentshipDto(response.data)
    })
  }

  static async deleteStudentship(studentship: StudentshipDto): Promise<void> {
    return httpClient.delete(`/studentships/delete/${studentship.id}`)
  }

  // ------------------ Candidates -------------------
  static async getCandidates(): Promise<CandidateDto[]> {
    return httpClient.get('/candidates/all').then((response) => {
      return response.data.map((candidate: any) => {
        return new CandidateDto(candidate)
      })
    })
  }

  static async getCandidate(istId: number): Promise<CandidateDto | null> {
    return httpClient.get(`/candidates/get/${istId}`).then((response) => {
      if (response.data) {
        return new CandidateDto(response.data)
      } else {
        return null
      }
    })
    .catch(() => {
      return null
    })
  }
  
  static async createCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.post('/candidates/create', candidate).then((response) => {
      return new CandidateDto(response.data)
    })
  }

  static async updateCandidate(candidate: CandidateDto): Promise<CandidateDto> {
    return httpClient.put('/candidates/update', candidate).then((response) => {
      return new CandidateDto(response.data)
    })
  }

  static async deleteCandidate(candidate: CandidateDto): Promise<void> {
    return httpClient.delete(`/candidates/delete/${candidate.istId}`)
  }

  // ------------------- Materials -------------------
  static async getMaterials(): Promise<MaterialDto[]> {
    return httpClient.get('/materials/all').then((response) => {
      return response.data.map((material: any) => {
        return new MaterialDto(material)
      })
    })
  }

  static async createMaterial(material: MaterialDto): Promise<MaterialDto> {
    return httpClient.post('/materials/create', material).then((response) => {
      return new MaterialDto(response.data)
    })
  }

  static async updateMaterial(material: MaterialDto): Promise<MaterialDto> {
    return httpClient.put('/materials/update', material).then((response) => {
      return new MaterialDto(response.data)
    })
  }

  static async deleteMaterial(material: MaterialDto): Promise<void> {
    return httpClient.delete(`/materials/delete/${material.id}`)
  }
}
