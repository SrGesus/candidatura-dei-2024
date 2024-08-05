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
        v-if="studentship.active"
        :disabled="selectedEnrolled.length === 0"
        class="text-none font-weight-regular mr-2"
        prepend-icon="mdi-delete"
        text="Anular Selecionados"
        color="red"
        variant="outlined"
        @click="unenrollCandidates()"
      ></v-btn>
      <span 
        v-if="studentship.active"
        v-tooltip="!isValidNumberOfSelected() ? 'O número de candidatos selecionados excede o número de vagas' : 'Dar Avaliação por Terminada'">
        <v-btn
          :disabled="!isValidNumberOfSelected()"
          class="text-none font-weight-regular mr-2"
          prepend-icon="mdi-shield-star"
          text="Selecionar Vencedores"
          color="success"
          variant="outlined"
          @click="selectWinners()"
          ></v-btn>
        </span>
        <v-btn
          v-else
          class="text-none font-weight-regular mr-2"
          prepend-icon="mdi-email-alert"
          text="Reativar Candidaturas"
          color="success"
          @click="selectWinners()"
        ></v-btn>
      <v-btn
        v-if="studentship.active"
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
    :show-select="studentship.active"
    :headers="enrollHeaders"
    :items="enrollments"
    :search="search"
    :custom-filter="fuzzySearch"
    v-model="selectedEnrolled"
    item-value="candidate.istId"
    class="text-center justify-center"
  >
    
    <!-- This is like the example in 
      https://vuetifyjs.com/en/components/data-tables/basics/#header-slot
      I don't know why eslint says it's invalid -->
    <!-- eslint-disable-next-line vue/valid-v-slot -->
    <template v-slot:header.add>
      <GradeParameterDialog :disabled="!studentship.active" :id="props.id" @studentship-saved="getStudentship(); getEnrollments()">
        <v-btn 
          :disabled="!studentship.active"
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
    <template v-slot:[`item.add`]="{ item }">
      <v-btn
        v-if="currentEnrollment != null && item.candidate.istId === currentEnrollment.candidate.istId"
        @click="changeCurrent(null)"
        icon="mdi-check"
        ></v-btn>
      <v-alert
        v-else-if="!studentship.active"
        variant="outlined"
        :type="item.accepted ? 'success' : 'error'"
      ><strong>{{ item.accepted ? 'Selecionado' : 'Não selecionado' }}</strong></v-alert>
    </template>
    <template v-for="parameter in studentship.gradeParameters" v-slot:[getKey(parameter.id!)]="{ item }" :key="parameter.id">
      <v-container style="height: 5rem" class="d-flex align-center justify-center mr-0 ml-0">
        <v-number-input
          v-if="currentEnrollment != null && item.candidate.istId === currentEnrollment.candidate.istId"
          v-model="item.grades[parameter.id]"
          style="max-width: 8rem;"
          :min="0.0"
          :max="parameter.weight"
          :step="0.5"
          hide-details
          autofocus
          control-variant="stacked"
          :base-color="getGradeColor(item.grades[parameter.id], parameter.weight)"
          :color="getGradeColor(item.grades[parameter.id], parameter.weight)"
          :suffix="`/${parameter.weight}`"
          :placeholder="item.grades[parameter.id] ? '' : '-'"
        ></v-number-input>
        <v-chip 
          v-else
          :class="{'disable-events': !studentship.active}"
          @click="changeCurrent(item)"
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
          :disabled="selectedUnenrolled.length === 0"
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
    v-model="selectedUnenrolled"
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
import { VNumberInput } from 'vuetify/lib/labs/components.mjs';
import StudentshipCard from '@/views/studentships/StudentshipCard.vue';
import CandidateDto from '@/models/CandidateDto';
import EnrollmentDto from '@/models/EnrollmentDto';
import StudentshipDto from '@/models/StudentshipDto';
import RemoteService from '@/services/RemoteService';
import { align, getGradeColor, getKey, fuzzySearch } from '@/utils/utils';
import { reactive, ref } from 'vue';
import GradeParameterDialog from '@/views/studentships/GradeParameterDialog.vue';

const props = defineProps<{
  id: number;
}>();

const totalWeight = ref(0);
const enrolling = ref(false)
const search = ref('')
const enrollments = reactive([] as EnrollmentDto[]);
const unenrolleds = reactive([] as CandidateDto[]);
const selectedEnrolled = ref([] as number[]);
const selectedUnenrolled = ref([] as number[]);
const currentEnrollment = ref(null as EnrollmentDto);

const changeCurrent = (enrollment: EnrollmentDto) => {
  if (currentEnrollment.value != null) {
    RemoteService.updateEnrollment(currentEnrollment.value).then(() => {
    });
    currentEnrollment.value.total = Object.values(currentEnrollment.value.grades).reduce((prev, curr) => prev + curr, 0);
  }
  currentEnrollment.value = enrollment;
}

const parameterHeader = ref({
  title: 'Avaliação',
  key: 'evaluation',
  align: 'center' as align,
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
];

const studentship = ref(new StudentshipDto({}));

const getStudentship = async () => {
  RemoteService.getStudentship(props.id).then((data) => {
    studentship.value = data;
    studentship.value.totalWeight = 0;
    parameterHeader.value.children.splice(0, parameterHeader.value.children.length);
    studentship.value.gradeParameters.forEach((parameter) => {
      totalWeight.value += parameter.weight;
      parameterHeader.value.children.push({
        align: 'center',
        key: parameter.id,
        title: parameter.name,
        value: `grades.${parameter.id}`,
      });
    });
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
getStudentship();

const getEnrollments = async () => {
  enrollments.splice(0, enrollments.length)
  unenrolleds.splice(0, unenrolleds.length)
  RemoteService.getStudentshipEnrollments(props.id).then((data) => {
    data.forEach((enrollment) => {
      enrollment.total = Object.values(enrollment.grades).reduce((prev, curr) => prev + curr, 0);
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
getEnrollments();

const enrollCandidates = async () => {
  RemoteService.createEnrollments(
    selectedUnenrolled.value,
    props.id
  ).then(() => {
    getEnrollments();
    enrolling.value = false;
    selectedUnenrolled.value.splice(0, selectedUnenrolled.value.length);
  });
}

const unenrollCandidates = async () => {
  RemoteService.deleteEnrollments(
    selectedEnrolled.value,
    props.id
  ).then(() => {
    getEnrollments();
    selectedEnrolled.value.splice(0, selectedEnrolled.value.length);
  });
}

function isValidNumberOfSelected(): boolean {
  return selectedEnrolled.value.length <= studentship.value.vacancies;
}

const selectWinners = async () => {
  changeCurrent(null);
  // TODO: Consider passing this logic to backend
  RemoteService.updateEnrollments(
    enrollments.map((enrollment) => {
      enrollment.accepted = selectedEnrolled.value.includes(enrollment.candidate.istId)
      return {
        studentship: { id: props.id },
        candidate: { istId: enrollment.candidate.istId },
        accepted: enrollment.accepted
      };
    }),
  ).then(() => {
    studentship.value.active = !studentship.value.active;
    selectedEnrolled.value.splice(0, selectedEnrolled.value.length);
    RemoteService.updateStudentship(studentship.value).then(() => {
      getStudentship();
    });
  });
}

</script>