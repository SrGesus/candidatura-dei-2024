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
      <td>{{ item.pay.toFixed(2) }} €</td>
      <td><v-chip>{{ item.vacancies }}</v-chip></td>
      <td>
        <v-row>
          <StudentshipDialog :edit="item" :studentship="item" @studentship-saved="getStudentships">
            <v-icon class="mr-2">mdi-pencil</v-icon>
          </StudentshipDialog>
          <v-icon @click="deleteStudentship(item)">mdi-delete</v-icon>
        </v-row>
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

import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'

import { reactive } from 'vue'
import type StudentshipDto from '@/models/studentships/StudentshipDto'
import router from '@/router';
import StudentshipDialog from './StudentshipDialog.vue';

const search = ref('')
const headers = [
  { title: 'ID', value: 'id', key: 'id'},
  { title: 'Data de Início', value: 'startDate', key: 'startDate' },
  { title: 'Data de Fim', value: 'endDate', key: 'endDate' },
  { title: 'Valor Mensal (€)', value: 'pay', key: 'pay' },
  { title: 'Vagas', value: 'vacancies', key: 'vacancies' },
  { title: 'Ações', value: 'actions', key: 'actions' }
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
  console.log(value, search)
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
