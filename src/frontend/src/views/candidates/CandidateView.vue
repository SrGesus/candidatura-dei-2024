<template>
  <v-row align="center" class="mb-2">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Candidatos</h2>
    </v-col>
    <v-col cols="auto">
      <CandidateDialog :edit="false" @candidate-saved="getCandidates">
          <v-btn
            class="text-none font-weight-regular"
            prepend-icon="mdi-plus"
            text="Adicionar Candidato"
            color="primary"
          ></v-btn>
      </CandidateDialog>  
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
    :items="candidates"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    <template v-slot:[`item.actions`]="{ item }">
      <v-row>
        <CandidateDialog :edit="item" @candidate-saved="getCandidates">
          <v-icon class="mr-2">mdi-pencil</v-icon>
        </CandidateDialog>
        <v-icon @click="deleteCandidate(item)">mdi-delete</v-icon>
      </v-row>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'

import { reactive } from 'vue'
import type CandidateDto from '@/models/candidates/CandidateDto'
import CandidateDialog from './CandidateDialog.vue'

const search = ref('')
const headers = [
  { title: 'IST ID', value: 'istId', key: 'istId' },
  { title: 'Name', value: 'name', key: 'name ' },
  { title: 'E-Mail', value: 'email', key: 'email' },
  { title: 'Actions', value: 'actions', key: 'actions' }
]

const candidates: CandidateDto[] = reactive([])

getCandidates()
async function getCandidates() {
  candidates.splice(0, candidates.length)
  RemoteService.getCandidates().then(async (data) => {
    data.forEach((candidate: CandidateDto) => {
      candidates.push(candidate)
    })
  })
}

function deleteCandidate(candidate: CandidateDto) {
  RemoteService.deleteCandidate(candidate).then(() => {
    getCandidates()
  })
}

const fuzzySearch = (value: string, search: string) => {
  // Regex to match any character in between the search characters
  let searchRegex = new RegExp(search.split('').join('.*'), 'i')
  return searchRegex.test(value)
}
</script>
