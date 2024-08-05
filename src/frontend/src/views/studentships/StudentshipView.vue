<template>
  <v-row align="center" class="mb-2">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Bolsas</h2>
    </v-col>
    <v-col cols="auto">
      <StudentshipDialog :edit="false" @studentship-saved="getStudentships">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Bolsa"
          color="primary"
        ></v-btn>
      </StudentshipDialog>  
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
    :group-by="[{key: 'active', order: 'desc'}]"
    :headers="headers"
    :items="studentships"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    <template v-slot:group-header="{ item, columns, toggleGroup, isGroupOpen }">
      <tr class="clickable-rows" @click="toggleGroup(item)">
        <td :colspan="columns.length">
          <v-btn
            :icon="isGroupOpen(item) ? '$expand' : '$next'"
            size="small"
            variant="text"
            disabled
          ></v-btn>
          {{ item.value ? 'Bolsas com Candidaturas Abertas' : 'Bolsas Passadas' }}
        </td>
      </tr>
    </template>
    <template v-slot:item="{ item }">
      <tr class="clickable-rows" @click="rowClick(item)">
        <td></td>
        <td>{{ item.id }}</td>
        <td>{{ item.startDate.toLocaleDateString() }}</td>
        <td>{{ item.endDate.toLocaleDateString() }}</td>
        <td>{{ item.amount.toFixed(2) }} €</td>
        <td><v-chip>{{ item.vacancies }}</v-chip></td>
        <td>
          <v-row>
            <StudentshipDialog :edit="item" :studentship="item" @studentship-saved="getStudentships">
              <v-icon class="mr-2">mdi-pencil</v-icon>
            </StudentshipDialog>
            <v-icon @click.stop="deleteStudentship(item)">mdi-delete</v-icon>
          </v-row>
        </td>
      </tr>
    </template>
  </v-data-table>

</template>

<script setup lang="ts">
import StudentshipDialog from '@/views/studentships/StudentshipDialog.vue';
import StudentshipDto from '@/models/StudentshipDto';
import router from '@/router';
import RemoteService from '@/services/RemoteService';
import { fuzzySearch } from '@/utils/utils';
import { reactive, ref } from 'vue';


const search = ref('')
const studentships = reactive([] as StudentshipDto[]);
const headers = [
  { title: 'ID', value: 'id', key: 'id'},
  { title: 'Data de Início', value: 'startDate', key: 'startDate' },
  { title: 'Data de Fim', value: 'endDate', key: 'endDate' },
  { title: 'Valor Mensal (€)', value: 'pay', key: 'pay' },
  { title: 'Vagas', value: 'vacancies', key: 'vacancies' },
  { title: 'Ações', value: 'actions', key: 'actions' }
]

const rowClick = (obj) => {
  router.push({ name: 'studentship', params: { id: obj.id } })
}

const getStudentships = async () => {
  studentships.splice(0, studentships.length)
  RemoteService.getStudentships().then((data) => {
    studentships.push(...data)
  })
}
getStudentships()

const deleteStudentship = (studentship: StudentshipDto) => {
  RemoteService.deleteStudentship(studentship).then(() => {
    getStudentships()
  })
}

</script>