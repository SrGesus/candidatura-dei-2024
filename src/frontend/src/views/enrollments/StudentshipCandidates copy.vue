<template>
<template v-if="studentship">
    <v-table hide-default-footer style="text-align: center;">
      <tr>
        <th>
        <v-container>
          <v-col class="d-flex align-center justify-center">
            <v-icon>mdi-briefcase</v-icon>
            <h2>Bolsa</h2>
          </v-col>
        </v-container>
        </th>
        <td>
        <v-container>
          <v-row justify="end" cols="auto" style="gap: 2%;">
            <v-btn
              class="text-none font-weight-regular"
              prepend-icon="mdi-delete"
              text="Eliminar"
              color="red"
              @click="deleteStudentship(studentship)"
            ></v-btn>
            <v-btn
              class="text-none font-weight-regular"
              prepend-icon="mdi-content-save"
              text="Guardar"
              color="primary"
              variant="tonal"
            ></v-btn>
          </v-row>
        </v-container>
        </td>
      </tr>
      <tr>
        <th>
          ID
        </th>
        <td>
          <v-number-input
            disabled
            focused
            v-model.number="studentship.id"
          ></v-number-input>
        </td>
      </tr>
      <tr>
        <th>
          Data de Início
        </th>
        <td>
          <v-date-input
            disabled
            focused
            :model-value="studentship.startDate"
            prepend-icon=""
            prepend-inner-icon="$calendar"
            placeholder="dd/mm/yyyy"
          ></v-date-input>
        </td>
      </tr>
      <tr>
        <th>
          Data de Fim
        </th>
        <td>
          <v-date-input
            disabled
            focused
            :model-value="studentship.endDate"
            prepend-icon=""
            prepend-inner-icon="$calendar"
            placeholder="dd/mm/yyyy"
          ></v-date-input>
        </td>
      </tr>
      <tr>
        <th>
          Valor mensal
        </th>
        <td>
          <v-number-input
            disabled
            focused
            v-model.number="studentship.pay"
            prefix="€"
          ></v-number-input>
        </td>
      </tr>
      <tr>
        <th>
          Vagas
        </th>
        <td>
          <v-number-input
            disabled
            focused
            v-model.number="studentship.vacancies"
          ></v-number-input>
        </td>
      </tr>
    </v-table>
</template>
<template v-else>
  <!-- Adicionar algum componente para erro 404 -->
  <h1>404</h1>
</template>
</template>

<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput'
import { VDateInput } from 'vuetify/labs/VDateInput'

import StudentshipDto from '@/models/studentships/StudentshipDto';
import RemoteService from '@/services/RemoteService';
import CreateStudentshipDialog from '@/views/studentships/CreateStudentshipDialog.vue'
import EditStudentshipDialog from '@/views/studentships/EditStudentshipDialog.vue'
import { reactive, ref } from 'vue';

const props = defineProps<{
  id: number;
}>();


const studentship = ref(null)

getStudentship()
async function getStudentship() {
  RemoteService.getStudentship(props.id).then(async (data) => {
    studentship.value = data
  })
}

import router from '@/router';

function deleteStudentship(studentship: StudentshipDto) {
  RemoteService.deleteStudentship(studentship).then(() => {
    router.back()
  })
}

const startDateRules = [
  (date: string) => date ? true : 'Data de início é obrigatória.'
];

const endDateRules = [
  (date: string) => date ? true : 'Data de fim é obrigatória.',
  // Note: startDate can be undefined, but then condition returns false
  () => studentship.value.endDate! > studentship.value.startDate! ? true : 'Data de Fim tem de ser depois da Data de Início.',
];

const payRules = [
  (pay: number) => pay > 0 ? true : 'Valor mensal é obrigatório.'
];

const vacanciesRules = [
  (vacancies: number) => vacancies > 0 ? true : 'Tem de haver pelo menos uma vaga.'
];
</script>
