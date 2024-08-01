<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Candidato"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Candidato">
        <v-form ref="form" @submit.prevent="submitForm()">
        <v-card-text>

          <v-number-input
              label="IST ID*" 
              required 
              v-model.number="newCandidate.istId"
              :rules="istIdRules"
            ></v-number-input>

            <v-text-field 
              label="Nome*" 
              required 
              v-model="newCandidate.name"
              :rules="nameRules"
            ></v-text-field>

            <v-text-field 
              label="E-Mail*" 
              required 
              v-model="newCandidate.email"
              :rules="emailRules"
              validate-on="blur"
              inputmode="email"
            ></v-text-field>

        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            type="submit"
            color="primary"
            text="Save"
            variant="tonal"
          ></v-btn>
        </v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput'

import { ref } from 'vue'
import type CandidateDto from '@/models/candidates/CandidateDto'
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const form = ref(null)

const emit = defineEmits(['candidate-created'])

const submitForm = async () => {
  const { valid } = await form.value.validate()
  if (valid) {
    saveCandidate()
    dialog.value = false;
  }
}

const newCandidate = ref<CandidateDto>({
  istId: 0,
  name: '',
  email: ''
})

const saveCandidate = async () => {
  console.log(newCandidate.value)
  await RemoteService.createCandidate(newCandidate.value)
  newCandidate.value = {
    istId: 0,
    name: '',
    email: ''
  }
  emit('candidate-created')
}

const nameRules = [
  name => {
    return name ? true : 'Nome é obrigatório.'
  },
];

const istIdRules = [
  istId => {
    return istId > 0 ? true : 'IST ID é obrigatório.'
  },
  istId => {
    return  Number.isInteger(istId) ? true : 'IST ID têm de ser um inteiro.'
  },
  async istId => {
    return await RemoteService.getCandidate(istId) ? 'Candidato com este IST ID já foi registado.' : true
  }
];

const emailRules = [
  email => {
    // Regex doesn't cover all cases but is a sensible approximation
    return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
      .test(email) ? true : 'E-Mail inválido.'
  },
];

</script>
