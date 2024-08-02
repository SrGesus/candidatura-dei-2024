<template>
  <v-dialog v-model="dialog" max-width="400">
    <template v-slot:activator="{ props: activatorProps }">
      <v-icon v-bind="activatorProps" class="mr-2">mdi-pencil</v-icon>
    </template>

    <v-card prepend-icon="mdi-account" title="Editar Candidato">
        <v-form ref="form" @submit.prevent="submitForm()">
        <v-card-text>

          <v-number-input
              label="IST ID*" 
              required 
              v-model.number="newCandidate.istId"
              disabled
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

          <!-- TODO: Either make close reset the values or add a button to reset values -->
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
</template>

<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput'

import { ref } from 'vue'
import type { Ref } from 'vue'
import RemoteService from '@/services/RemoteService'
import CandidateDto from '@/models/candidates/CandidateDto';
import { VForm } from 'vuetify/lib/components/index.mjs';

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)

const props = defineProps<{
  candidate: CandidateDto;
}>();

const emit = defineEmits(['candidate-edited'])

const newCandidate = ref<CandidateDto>({ ...props.candidate })

const submitForm = async () => {
  const { valid } = await form.value!.validate()
  if (valid) {
    saveCandidate()
    dialog.value = false;
  }
}

const saveCandidate = async () => {
  console.log(newCandidate.value)
  await RemoteService.updateCandidate(newCandidate.value)
  emit('candidate-edited')
}

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
