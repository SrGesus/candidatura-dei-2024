<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Listagem de Candidatos</h2>
    </v-col>
    <v-col cols="auto">
      <CreateCandidateDialog @candidate-created="getCandidates" />
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
      <EditCandidateDialog :candidate="item" @candidate-edited="getCandidates" />
      <v-icon @click="deleteCandidate(item)">mdi-delete</v-icon>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import CreateCandidateDialog from '@/views/candidates/CreateCandidateDialog.vue'
import { ref } from 'vue'
import RemoteService from '@/services/RemoteService'

import { reactive } from 'vue'
import type CandidateDto from '@/models/candidates/CandidateDto'
import EditCandidateDialog from '@/views/candidates/EditCandidateDialog.vue'

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
