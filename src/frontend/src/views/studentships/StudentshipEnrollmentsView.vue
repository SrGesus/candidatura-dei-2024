<template>

  <StudentshipCard :studentshipId="props.id" @studentship-saved="getStudentship()"/>

  <v-divider></v-divider>


  <template v-if="!enrolling">
  <v-row align="center" class="mt-2 mb-2">
    <v-col>
      <h2 class="text-left ml-1">Candidatos Inscritos</h2>
    </v-col>
    <v-col cols="auto">
      <v-btn
        :disabled="selected.length === 0"
        class="text-none font-weight-regular mr-2"
        prepend-icon="mdi-delete"
        text="Anular Selecionados"
        color="red"
        variant="outlined"
        @click="unenrollCandidates()"
      ></v-btn>
      <span 
        v-tooltip="!isValidNumberOfSelected() ? 'O número de candidatos selecionados excede o número de vagas' : 'Dar Avaliação por Terminada'">
        <v-btn
          :disabled="!isValidNumberOfSelected()"
          class="text-none font-weight-regular mr-2"
          prepend-icon="mdi-shield-star"
          text="Selecionar Vencedores"
          hint="Selecionar os candidatos com melhor avaliação"
          color="success"
          variant="outlined"
          @click="selectCandidates()"
        ></v-btn>
      </span>
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-plus"
        text="Inscrever Candidatos"
        color="primary"
        @click="enrolling = true"
      ></v-btn>
    </v-col>
  </v-row>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  <v-data-table
    show-select
    :headers="enrollHeaders"
    :items="enrollments"
    :search="search"
    :custom-filter="fuzzySearch"
    v-model="selected"
    item-value="candidate.istId"
    class="text-left"
  >
    
    <!-- This is like the example in 
      https://vuetifyjs.com/en/components/data-tables/basics/#header-slot
      I don't know why eslint says it's invalid -->
    <!-- eslint-disable-next-line vue/valid-v-slot -->
    <template v-slot:header.add>
      <GradeParameterDialog :studentship-id="props.id" @parameters-saved="getParameters(); getEnrollments()">
        <v-btn 
          color="primary" 
          variant="tonal"
          style="width: 100%;"
          v-tooltip="'Editar Parâmetros'"
        >
          <v-icon>mdi-abacus</v-icon>
        </v-btn>
      </GradeParameterDialog>
    </template>
    <template v-slot:[`item.total`]="{ item }">
      <v-chip
        :color="getGradeColor(item.total, totalWeight)"
        >
        {{item.total}} / {{ totalWeight }}
      </v-chip>
    </template>
    <template v-for="parameter in parameters" v-slot:[getKey(parameter)]="{ item }" :key="parameter.id">
      <v-container style="width: 13rem; height: 5rem" class="d-flex align-center justify-center mr-0 ml-0">
        <v-number-input
          style="width: 10em; margin: 0;"
          v-if="parameter != null && parameter === currentParameter && item === currentEnrollment"
          v-model="item.grades[parameter.id]"
          :min="0.0"
          :max="parameter.weight"
          :step="0.5"
          hide-details
          autofocus
          control-variant="stacked"
          append-icon="mdi-check"
          @click:append="changeCurrent(null, null)"
          :base-color="getGradeColor(item.grades[parameter.id], parameter.weight)"
          :color="getGradeColor(item.grades[parameter.id], parameter.weight)"
          :suffix="`/${parameter.weight}`"
          :placeholder="item.grades[parameter.id] ? '' : '-'"
        ></v-number-input>
        <v-chip 
          v-else
          @click="changeCurrent(parameter, item)"
            :color="getGradeColor(item.grades[parameter.id], parameter.weight)"
          >
          {{item.grades[parameter.id] != undefined ? item.grades[parameter.id] : '-' }} / {{parameter.weight}}
        </v-chip>
      </v-container>
    </template>
    
  </v-data-table>

  </template>

  <template v-else>

    <v-row align="center" class="mb-2 mt-2">
    <v-col>
      <h2 class="text-left ml-1">Inscrever Candidatos</h2>
    </v-col>
    <v-col cols="auto">
        <v-btn
          class="text-none font-weight-regular mr-2"
          prepend-icon="mdi-arrow-left"
          text="Cancelar"
          color="primary"
          variant="text"
          @click="enrolling = false"
        ></v-btn>
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-content-save"
          :disabled="selectedEnrolling.length === 0"
          text="Inscrever Seleciondados"
          color="primary"
          @click="enrollCandidates()"
        ></v-btn>
    </v-col>
  </v-row>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  
  <v-data-table
    show-select
    :headers="unenrolledHeaders"
    v-model="selectedEnrolling"
    item-value="istId"
    :items="unenrolleds"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >

  </v-data-table>
  </template>

</template>

<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput';
import RemoteService from '@/services/RemoteService';
import { reactive, Ref, ref } from 'vue';
const props = defineProps<{
  id: number;
}>();

const search = ref('')

// Typescript keeps infering to string so this was needed
type alignment = 'start' | 'center' | 'end';

const parameterHeader = ref({
  title: 'Avaliação',
  key: 'evaluation',
  align: 'center' as alignment,
  children: [],
})

const enrollHeaders = ref([
  { title: 'IST ID', key: 'candidateId', value: 'candidate.istId' },
  { title: 'Nome', key: 'name', value: 'candidate.name' },
  parameterHeader.value,
]);

const unenrolledHeaders = [
  { title: 'IST ID', key: 'candidateId', value: 'istId' },
  { title: 'Nome', key: 'name', value: 'name' },
  { title: 'E-Mail', key: 'email', value: 'email' },

]

const enrollments: EnrollmentDto[] = reactive([]);
const unenrolleds: CandidateDto[] = reactive([]);
const selectedEnrolling: Ref<number[]> = ref([]);
const selected: Ref<number[]> = ref([]);
const currentParameter = ref<GradeParameterDto | null>(null);
const currentEnrollment = ref<EnrollmentDto | null>(null);
const enrolling = ref(false);


const parameters: GradeParameterDto[] = reactive([]);
const totalWeight = ref(0);

async function changeCurrent(parameter: GradeParameterDto, enrollment: EnrollmentDto) {
  if (currentParameter.value != null && currentEnrollment.value != null) {
    RemoteService.gradeCandidate(new GradeDto({
      gradeParameterId: currentParameter.value.id,
      candidateIstId: currentEnrollment.value.candidate.istId,
      studentshipId: props.id,
      grade: currentEnrollment.value.grades[currentParameter.value.id],
    }));
    currentEnrollment.value.total = 0;
    currentEnrollment.value.total = Object.values(currentEnrollment.value.grades).reduce((prev, grade) => prev + grade, 0);
  }
  currentParameter.value = parameter;
  currentEnrollment.value = enrollment;
}

type headerKey = number;

getParameters();
async function getParameters() {
  parameters.splice(0, parameters.length)
  totalWeight.value = 0;
  parameterHeader.value.children.splice(0, parameterHeader.value.children.length)
  RemoteService.getGradeParameters(props.id).then((data) => {
    data.forEach((parameter) => {
      totalWeight.value += parameter.weight;
      parameters.push(parameter)
      parameterHeader.value.children.push({
        align: 'center',
        title: parameter.name,
        key: parameter.id as headerKey,
        value: `grades[${parameter.id}]`,
      })
    })
    parameterHeader.value.children.push({
      align: 'center',
      key: 'total',
      title: 'Total',
      value: `total`,
    })
    parameterHeader.value.children.push({
      align: 'center',
      key: 'add',
    })
  });
}

const studentship = ref({
  id: 0,
  startDate: new Date(),
  endDate: new Date(),
  pay: 0,
  vacancies: -1,
} as StudentshipDto
);

getStudentship()
async function getStudentship() {
  RemoteService.getStudentship(props.id).then(async (data) => {
    studentship.value = data
    studentship.value.startDate = new Date(data.startDate!);
    studentship.value.endDate = new Date(data.endDate!);
  })
}

getEnrollments();
async function getEnrollments() {
  enrollments.splice(0, enrollments.length);
  unenrolleds.splice(0, unenrolleds.length);
  RemoteService.getStudentshipEnrollments(props.id).then(async (data) => {
    data.forEach((enrollment: EnrollmentDto) => {
      enrollment.total = Object.values(enrollment.grades).reduce((prev, grade) => prev + grade, 0);
      enrollments.push(enrollment);
    });
    // FIXME: I don't like this (O(nm)), but since a single studentship
    // won't have too many enrollments, it's fine for now.
    RemoteService.getCandidates().then(async (data) => {
      data.forEach((candidate: CandidateDto) => {
        if (!enrollments.some((enrollment: EnrollmentDto) => enrollment.candidate.istId === candidate.istId)) {
          unenrolleds.push(candidate);
        }
      });
    });
  });
}

const unenrollCandidates = async () => {
  for (let candidate of selected.value) {
    await RemoteService.deleteEnrollment({
      candidateIstId: candidate,
      studentshipId: props.id,
    });
  }
  getEnrollments();
  selected.value.splice(0, selected.value.length);
}

const selectCandidates = async () => {

}

const enrollCandidates = async () => {
  for (let candidate of selectedEnrolling.value) {
    await RemoteService.createEnrollment({
      candidateIstId: candidate,
      studentshipId: props.id,
    });
  }
  getEnrollments();
  enrolling.value = false;
  selectedEnrolling.value.splice(0, selectedEnrolling.value.length);
}

import StudentshipCard from '@/views/studentships/StudentshipCard.vue';
import CandidateDto from '@/models/candidates/CandidateDto';
import EnrollmentDto from '@/models/enrollments/EnrollmentDto';
import GradeParameterDialog from '../enrollments/GradeParameterDialog.vue';
import GradeParameterDto from '@/models/grades/GradeParameterDto';
import GradeDto from '@/models/grades/GradeDto';
import StudentshipDto from '@/models/studentships/StudentshipDto';

const fuzzySearch = (value: string, search: string): boolean => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}

function getGradeColor(grade: number, weight: number): string {
  if (grade === undefined) {
    return 'grey';
  }
  if (grade <= weight / 3) {
    return 'error';
  } else if (grade <= 2 * weight / 3) {
    return 'warning';
  } else {
    return 'success';
  }
}

function getKey(parameter: GradeParameterDto): `item.${string}` {
  return `item.${parameter.id}`;
}

function isValidNumberOfSelected(): boolean {
  return selected.value.length <= studentship.value.vacancies;
}

</script>

