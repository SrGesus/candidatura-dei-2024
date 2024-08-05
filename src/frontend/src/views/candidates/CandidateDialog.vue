<template>
  <v-dialog v-model="dialog" max-width="400">
    
    <!-- Activator -->
    <template v-slot:activator="{ props: activatorProps }">
      <div v-bind="activatorProps" style="cursor: pointer;">
        <slot></slot>
      </div>
    </template>
  
    <!-- Dialogue Container -->
    <v-card prepend-icon="mdi-account" :title="title">
    <v-form ref="form" @submit.prevent="submitForm()">
  
      <v-card-text>
        <v-number-input
          label="IST ID*" 
          required 
          v-model.number="candidate.istId"
          :rules="istIdRules"
          :min="0"
          :disabled="!!props.edit"
        ></v-number-input>
  
        <v-text-field 
          label="Nome*" 
          required 
          v-model="candidate.name"
          :rules="nameRules"
        ></v-text-field>
  
        <v-text-field 
          label="E-Mail*" 
          required 
          v-model="candidate.email"
          :rules="emailRules"
          inputmode="email"
        ></v-text-field>
      </v-card-text>
  
      <v-divider></v-divider>
  
      <v-card-actions>
        <v-spacer></v-spacer>
  
        <v-btn 
          text="Cancelar" 
          variant="plain" 
          @click="dialog = false"
        ></v-btn>
        <v-btn
          type="submit"
          color="primary"
          text="Guardar"
          variant="tonal"
        ></v-btn>
      </v-card-actions>
    </v-form>
    </v-card>
  
  </v-dialog>
  </template>
  
<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput';
import { VForm } from 'vuetify/lib/components/index.mjs';

import { ref } from 'vue'
import type { Ref } from 'vue'
import CandidateDto from '@/models/CandidateDto'
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)
const emit = defineEmits(['candidate-saved'])

const props = defineProps<{
  edit: false | CandidateDto;
}>();

const title = props.edit ? 'Editar Candidato' : 'Novo Candidato';

const candidate = ref<CandidateDto>(
  new CandidateDto(props.edit ? props.edit : {})
)

const submitForm = () => {
  form.value!.validate().then(({valid}) => {
    if (valid) {
      saveCandidate()
      dialog.value = false
    }
  })
}

const saveCandidate = async () => {
  if (props.edit) {
    await RemoteService.updateCandidate(candidate.value)
  } else {
    await RemoteService.createCandidate(candidate.value)
  }
  candidate.value = {
    istId: 0,
    name: '',
    email: ''
  }
  emit('candidate-saved')
}

const istIdRules = [
  (istId: number) => {
    return istId > 0 ? true : 'IST ID é obrigatório.'
  },
  (istId: number) => {
    return  Number.isInteger(istId) ? true : 'IST ID têm de ser um inteiro.'
  },
  async (istId: number) => {
    return !props.edit && await RemoteService.candidateExists(istId) ? 'Candidato com este IST ID já foi registado.' : true
  }
];

const nameRules = [
  (name: string) => {
    return name ? true : 'Nome é obrigatório.'
  },
];

const emailRules = [
  (email: string) => {
    // Regex doesn't cover all cases but is a sensible approximation
    return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
      .test(email) ? true : 'E-Mail inválido.'
  },
];

</script>
