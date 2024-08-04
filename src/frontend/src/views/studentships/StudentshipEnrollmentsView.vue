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
    <template v-slot:header.add>
      <GradeParameterDialog :studentship-id="props.id" @parameters-saved="getParameters()">
        <v-btn 
          color="primary" 
          variant="tonal"
          style="width: 100%;">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </GradeParameterDialog>
    </template>

    <template v-for="parameter in parameters" v-slot:[`item.${parameter.id}`]="{ item }" :key="parameter.id">
      <v-chip
        :color="item.grades[parameter.id] >= parameter.weight / 2 ? 'success' : 'error'"
        >
        {{item.grades[parameter.id] ? item.grades[parameter.id] : '-' }}
      </v-chip>
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

const parameterHeader = ref({
  title: 'Avaliação',
  key: 'evaluation',
  align: 'center' as alignment,
  children: [],
})

const enrollHeaders = ref([
  { title: 'IST ID', key: 'candidateId', value: 'candidate.istId' },
  { title: 'Nome', key: 'name', value: 'candidate.name' },
  { title: 'E-Mail', key: 'email', value: 'candidate.email' },
  // { title: 'Avaliação', key: 'evaluation', align: 'center' as alignment, children: [] },
  parameterHeader.value,
]);

const unenrolledHeaders = [
  { title: 'IST ID', key: 'candidateId', value: 'istId' },
  { title: 'Nome', key: 'name', value: 'name' },
  { title: 'E-Mail', key: 'email', value: 'email' },

]

const enrollments: EnrollmentDto[] = reactive([]);
const unenrolleds: CandidateDto[] = reactive([]);
const selected: Ref<number[]> = ref([]);

const enrolling = ref(false);


const parameters = reactive([]);

getParameters();
async function getParameters() {
  parameters.splice(0, parameters.length)
  parameterHeader.value.children.splice(0, parameterHeader.value.children.length)
  RemoteService.getGradeParameters(props.id).then((data) => {
    data.forEach((parameter) => {
      parameters.push(parameter)
      enrollHeaders.value[3]['children'].push({
        align: 'center',
        title: parameter.name,
        key: parameter.id.toString(),
        value: `grades[${parameter.id}]`,
      })
    })
    parameterHeader.value.children.push({
      align: 'center',
      key: 'add',
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
import GradeParameterDto from '@/models/grades/GradeParameterDto';

const fuzzySearch = (value: string, search: string): boolean => {
  console.log(value, search)
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
