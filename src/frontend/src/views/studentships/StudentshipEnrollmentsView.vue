<template>

  <StudentshipCard :studentshipId="props.id" />

  <v-divider></v-divider>


  <template v-if="!enrolling">
  <v-row align="center" class="mb-2 mt-2">
    <v-col>
      <h2 class="text-left ml-1">Candidatos Inscritos</h2>
    </v-col>
    <v-col cols="auto">
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
    :headers="enrollHeaders"
    :items="enrollments"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    
    <!-- This is quite literally the example in 
      https://vuetifyjs.com/en/components/data-tables/basics/#header-slot
      I don't know why eslint says it's invalid -->
    <!-- eslint-disable-next-line vue/valid-v-slot -->
    <template v-slot:header.__add>
      <GradeParameterDialog :studentship-id="props.id" @parameters-saved="getParameters()">
        <v-btn 
          class="text-none font-weight-regular"
          size="large" 
          color="primary" 
          variant="tonal" 
          prepend-icon="mdi-cog"
          style="width: 100%;">
          Parâmetros
        </v-btn>
      </GradeParameterDialog>
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
          :disabled="selected.length === 0"
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
    v-model="selected"
    :items="unenrolleds"
    item-value="istId"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >

  </v-data-table>
  </template>

</template>

<script setup lang="ts">

import RemoteService from '@/services/RemoteService';
import { reactive, Ref, ref } from 'vue';
const props = defineProps<{
  id: number;
}>();

const search = ref('')

// Typescript keeps infering to string so this was needed
type alignment = 'start' | 'center' | 'end';

const enrollHeaders = [
  { title: 'IST ID', key: 'candidateId', value: 'candidate.istId' },
  { title: 'Nome', key: 'name', value: 'candidate.name' },
  { title: 'E-Mail', key: 'email', value: 'candidate.email' },
  { title: 'Ações', key: 'actions' },
  { title: 'Avaliação', key: 'evaluation', align: 'center' as alignment, children: [] },
];

enrollHeaders[4]['children'] = [
  { align: 'center', title: '_', key: '__add' },
]

const unenrolledHeaders = [
  { title: 'IST ID', key: 'candidateId', value: 'istId' },
  { title: 'Nome', key: 'name', value: 'name' },
  { title: 'E-Mail', key: 'email', value: 'email' },
  
]

const enrollments: EnrollmentDto[] = reactive([]);
const unenrolleds: CandidateDto[] = reactive([]);
const selected: Ref<number[]> = ref([]);

const enrolling = ref(false);


const parameters: Ref<GradeParameterDto[]> = ref([]);

getParameters();
async function getParameters() {
  parameters.value.splice(0, parameters.value.length)
  RemoteService.getGradeParameters(props.id).then((data) => {
    data.forEach((parameter) => {
      parameters.value.push(parameter)
    })
  });
}

getEnrollments();
async function getEnrollments() {
  enrollments.splice(0, enrollments.length);
  unenrolleds.splice(0, unenrolleds.length);
  RemoteService.getStudentshipEnrollments(props.id).then(async (data) => {
    data.forEach((enrollment: any) => {
      enrollments.push(enrollment);
    });
    console.log(enrollments);
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

const enrollCandidates = async () => {
  console.log(selected.value);
  for (let candidate of selected.value) {
    await RemoteService.createEnrollment({
      candidateIstId: candidate,
      studentshipId: props.id,
    });
  }
  getEnrollments();
  enrolling.value = false;
  selected.value.splice(0, selected.value.length);
}

import StudentshipCard from '@/views/studentships/StudentshipCard.vue';
import CandidateDto from '@/models/candidates/CandidateDto';
import EnrollmentDto from '@/models/enrollments/EnrollmentDto';
import GradeParameterDialog from '../enrollments/GradeParameterDialog.vue';

const fuzzySearch = (value: string, search: string) => {
  console.log(value, search)

  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
