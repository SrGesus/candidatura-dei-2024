<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Bolsas</h2>
    </v-col>
    <v-col cols="auto">
      <CreateStudentshipDialog @studentship-created="getStudentships" />
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
    :headers="headers"
    :items="studentships"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
  <template v-slot:item="{ item }">
    <tr class="clickable-rows" @click="rowClick(item)">
        <td>{{ item.id }}</td>
        <td>{{ item.startDate }}</td>
        <td>{{ item.endDate }}</td>
        <td>{{ item.pay }}</td>
        <td>{{ item.vacancies }}</td>
        <td>
          <EditStudentshipDialog style="cursor: pointer;" :studentship="item" @studentship-edited="getStudentships" />
          <v-icon @click="deleteStudentship(item)">mdi-delete</v-icon>
        </td>
      </tr>
  </template>
  
  </v-data-table>
</template>

<script setup lang="ts">
const rowClick = (obj) => {
  console.log(obj)
  router.push({ name: 'studentship', params: { id: obj.id } })
}
import CreateStudentshipDialog from '@/views/studentships/CreateStudentshipDialog.vue'
import EditStudentshipDialog from '@/views/studentships/EditStudentshipDialog.vue'
import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'

import { reactive } from 'vue'
import type StudentshipDto from '@/models/studentships/StudentshipDto'
import router from '@/router';

const search = ref('')
const headers = [
  { title: 'ID', value: 'id', key: 'id'},
  { title: 'Data de Início', value: 'startDate', key: 'startDate' },
  { title: 'Data de Fim', value: 'endDate', key: 'endDate' },
  { title: 'Valor Mensal (€)', value: 'pay', key: 'pay' },
  { title: 'Vagas', value: 'vacancies', key: 'vacancies' },
  { title: 'Actions', value: 'actions', key: 'actions' },
]

const studentships: StudentshipDto[] = reactive([])

getStudentships()
async function getStudentships() {
  studentships.splice(0, studentships.length)
  RemoteService.getStudentships().then(async (data) => {
    data.forEach((studentship: StudentshipDto) => {
      studentships.push(studentship)
    })
  })
}

function deleteStudentship(studentship: StudentshipDto) {
  RemoteService.deleteStudentship(studentship).then(() => {
    getStudentships()
  })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
