
export default class CandidateDto {
    istId: number = 0;
    name: string = '';
    email: string = '';

    constructor(jsonObj: Partial<CandidateDto>) {
        Object.assign(this, jsonObj);
    }
}
