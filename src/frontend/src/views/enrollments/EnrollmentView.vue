<template>
  <v-row align="center" class="mb-2">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Candidaturas</h2>
    </v-col>
    <v-col cols="auto">
    </v-col>
  </v-row>

  <v-divider></v-divider>

  <v-row align="center" class="mb-2 mt-2">
    <v-col>
      <h3 class="text-left ml-1">Candidaturas Abertas</h3>
    </v-col>
    <v-col cols="auto">
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-eye"
        :text="'Ver ' + (active ? 'Passadas' : 'Ativas')"
        color="primary"
        variant="tonal"
        @click="toggleActive"
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
    :headers="active ? headers : inactiveHeaders"
    :items="active ? enrollments : inactiveEnrollments"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >

    <template v-slot:item="{ item }">
      <tr>
        <td class="clickable-rows" @click="toStudentship(item)">
          {{ item.studentship.id  }}
        </td>
        <td  class="clickable-rows" @click="toCandidate(item)">
          {{ item.candidate.istId }}
        </td>
        <td>{{ item.candidate.name }}</td>
        <td>{{ item.candidate.email }}</td>
        <td>
          <v-chip
            :color="getGradeColor(item.total, item.studentship.totalWeight)"
            >
            {{item.total}} / {{ item.studentship.totalWeight }}
          </v-chip>
        </td>
        <td v-if="!active">
          <v-alert
            variant="outlined"  density="compact"
            :type="item.accepted ? 'success' : 'error'"
          ><strong>{{ item.accepted ? 'Selecionado' : 'Não selecionado' }}</strong></v-alert>
        </td>
        <td>
          <v-icon @click.stop="deleteEnrollment(item)">mdi-delete</v-icon>
        </td>
      </tr>
    </template>
    <template v-slot:[`item.accepted`]="{ item }" >
      <v-icon v-if="item.accepted" color="success">mdi-check</v-icon>
      <v-icon v-else color="error">mdi-close</v-icon>
    </template>
  </v-data-table>
</template>


<script setup lang="ts">
import EnrollmentDto from '@/models/EnrollmentDto';
import router from '@/router';
import RemoteService from '@/services/RemoteService';
import { fuzzySearch, getGradeColor } from '@/utils/utils';
import { ref } from 'vue';

const active = ref(true);

const search = ref('');
const headers = [
  { title: 'ID Candidatura', value: 'studentship.id', key: 'id'},
  { title: 'IST ID', value: 'candidate.istId', key: 'istId' },
  { title: 'Nome', value: 'candidate.name', key: 'name' },
  { title: 'E-Mail', value: 'candidate.email', key: 'email' },
  { title: 'Avaliação', value: 'total', key: 'grade' },
  { title: 'Ações', value: 'actions', key: 'actions' },
]
const inactiveHeaders = [
  { title: 'ID Candidatura', value: 'studentship.id', key: 'id'},
  { title: 'IST ID', value: 'candidate.istId', key: 'istId' },
  { title: 'Nome', value: 'candidate.name', key: 'name' },
  { title: 'E-Mail', value: 'candidate.email', key: 'email' },
  { title: 'Avaliação', value: 'total', key: 'grade' },
  { title: 'Selecionado', value: 'accepted', key: 'accepted' },
  { title: 'Ações', value: 'actions', key: 'actions' },
]
const enrollments = ref([] as EnrollmentDto[]);
const inactiveEnrollments = ref([] as EnrollmentDto[]);

const getEnrollments = async () => {
  enrollments.value.splice(0, enrollments.value.length);
  inactiveEnrollments.value.splice(0, inactiveEnrollments.value.length);
  RemoteService.getEnrollments().then((data) => {
    data.forEach((enrollment: EnrollmentDto) => {
      enrollment.total = Object.values(enrollment.grades).reduce((prev, curr) => prev + curr, 0);
      // TODO: This is being calculated multiple times, should be done in backend instead
      enrollment.studentship.totalWeight = Object.values(enrollment.studentship.gradeParameters).reduce((prev, curr) => prev + curr.weight, 0);
      if (enrollment.studentship.active) {
        enrollments.value.push(enrollment);
      } else {
        inactiveEnrollments.value.push(enrollment);
      }
    });
  });
};
getEnrollments();

const deleteEnrollment = async (enrollment: EnrollmentDto) => {
  RemoteService.deleteEnrollment(enrollment).then(() => {
    getEnrollments();
  }).catch(() => {
    getEnrollments();
  });
};

const toStudentship = (enrollment: EnrollmentDto) => {
  router.push({ name: 'studentship', params: { id: enrollment.studentship.id } });
};

const toCandidate = (enrollment: EnrollmentDto) => {
  router.push({ name: 'candidate', params: { istId: enrollment.candidate.istId } });
};

const toggleActive = () => {
  active.value = !active.value;
  getEnrollments();
};

</script>
