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

  <v-divider></v-divider>

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
    <template v-slot:item="{ item }">
      <tr class="clickable-rows" @click="rowClick(item)">
        <td>{{ item.istId }}</td>
        <td>{{ item.name }}</td>
        <td>{{ item.email }}</td>
        <td>
          <v-row>
            <CandidateDialog :edit="item" @candidate-saved="getCandidates">
              <v-icon class="mr-2">mdi-pencil</v-icon>
            </CandidateDialog>
            <v-icon @click.stop="deleteCandidate(item)">mdi-delete</v-icon>
          </v-row>
        </td>
      </tr>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import CandidateDialog from '@/views/candidates/CandidateDialog.vue';

import { reactive, ref } from 'vue';

import RemoteService from '@/services/RemoteService';
import { fuzzySearch } from '@/utils/utils';
import CandidateDto from '@/models/CandidateDto';
import router from '@/router';
import { align } from '@/utils/utils';

const search = ref('')
const candidates = reactive([] as CandidateDto[]);
const headers = [
  { title: 'IST ID', value: 'istId', key: 'istId' },
  { title: 'Name', value: 'name', key: 'name' },
  { title: 'E-Mail', value: 'email', key: 'email' },
  { title: 'Ações', value: 'actions', key: 'actions', align: 'start' as align }
];

const getCandidates = async () => {
  candidates.splice(0, candidates.length);
  RemoteService.getCandidates().then((data) => {
    candidates.push(...data);
  });
}

getCandidates();

const deleteCandidate = (candidate: CandidateDto) => {
  RemoteService.deleteCandidate(candidate).then(() => {
    getCandidates();
  });
}

const rowClick = (row: CandidateDto) => {
  router.push({ name: 'candidate', params: { istId: row.istId } });
}

</script>
