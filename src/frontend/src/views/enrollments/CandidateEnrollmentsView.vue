<template>

  <CandidateCard :istId="props.istId" />

  <v-divider></v-divider>

  <v-row align="center" class="mt-2 mb-2">
    <v-col>
      <h2 class="text-left ml-1">Candidaturas Ativas</h2>
    </v-col>
  </v-row>

  <v-data-table
    :headers="activeHeaders"
    :items="activeEnrollments"
    class="text-left"
  >

  <template v-slot:item="{ item }">
    <tr class="clickable-rows" @click="rowClick(item)">
      <td>{{ item.studentship.id }}</td>
      <td>{{ item.studentship.startDate.toLocaleDateString() }}</td>
      <td>{{ item.studentship.endDate.toLocaleDateString() }}</td>
      <td><v-chip>{{ item.studentship.vacancies }}</v-chip></td>
      <td class="text-left">
        <v-icon 
          class="ml-2" 
          v-tooltip="'Anular Candidatura'"
          @click.stop="deleteEnrollment(item)"
        >
          mdi-account-remove
        </v-icon>
      </td>
    </tr>
  </template>
  </v-data-table>

  <v-divider></v-divider>

  <v-row align="center" class="mt-2 mb-2">
    <v-col>
      <h2 class="text-left ml-1">Candidaturas Passadas</h2>
    </v-col>
  </v-row>

  <v-data-table
    :headers="inactiveHeaders"
    :items="inactiveEnrollments"
    class="text-left"
  >
    <template v-slot:item="{ item }">
      <tr class="clickable-rows" @click="rowClick(item)">
        <td>{{ item.studentship.id }}</td>
        <td>{{ item.studentship.startDate.toLocaleDateString() }}</td>
        <td>{{ item.studentship.endDate.toLocaleDateString() }}</td>
        <td><v-chip>{{ item.studentship.vacancies }}</v-chip></td>
        <td>
          <v-icon v-if="item.accepted" color="success">mdi-check</v-icon>
          <v-icon v-else color="error">mdi-close</v-icon>
        </td>
      </tr>
    </template>
  </v-data-table>

</template>

<script setup lang="ts">

import RemoteService from '@/services/RemoteService';
import { ref } from 'vue'
import CandidateCard from '@/views/candidates/CandidateCard.vue';
import EnrollmentDto from '@/models/EnrollmentDto';
import router from '@/router';
import { align } from '@/utils/utils';

const props = defineProps<{
  istId: number | null;
}>();

const activeHeaders = [
    { title: 'ID', key: 'studentshipId', value: 'studentship.id' },
    { title: 'Data de Início', key: 'startDate', value: 'studentship.startDate' },
    { title: 'Data de Fim', key: 'endDate', value: 'studentship.endDate' },
    { title: 'Vagas', key: 'vacancies', value: 'studentship.vacancies' },
    { title: 'Ações', key: 'actions', value: 'actions', align: 'start' as align }
]
const activeEnrollments = ref([] as EnrollmentDto[]);

const inactiveHeaders = [
    { title: 'ID', key: 'studentshipId', value: 'studentship.id' },
    { title: 'Data de Início', key: 'startDate', value: 'studentship.startDate' },
    { title: 'Data de Fim', key: 'endDate', value: 'studentship.endDate' },
    { title: 'Vagas', key: 'vacancies', value: 'studentship.vacancies' },
    { title: 'Selecionado', key: 'accepted', value: 'accepted', align: 'start' as align }
]
const inactiveEnrollments = ref([] as EnrollmentDto[]);

const getEnrollments = () => {
    activeEnrollments.value.splice(0, activeEnrollments.value.length)
    inactiveEnrollments.value.splice(0, inactiveEnrollments.value.length)
    RemoteService.getCandidateEnrollments(props.istId).then((data) => {
        data.forEach((enrollment) => {
            if (enrollment.studentship.active) {
                activeEnrollments.value.push(enrollment);
            } else {
                inactiveEnrollments.value.push(enrollment);
            }
        });
    })
}

getEnrollments();

const deleteEnrollment = (enrollment: EnrollmentDto) => {
    RemoteService.deleteEnrollment(enrollment).then(() => {
        getEnrollments();
    });
}

const rowClick = (obj: EnrollmentDto) => {
  router.push({ name: 'studentship', params: { id: obj.studentship.id } })
}

</script>