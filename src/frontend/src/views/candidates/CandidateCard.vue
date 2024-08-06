<template>
  <v-row class="d-flex align-center mt-1">
    <v-col class="d-flex align-center justify-start ml-1" style="gap: 10px;">
      <v-btn
        class="text-none font-weight-regular"
        icon="mdi-arrow-left"
        color="tonal"
        @click="router.back()"
      ></v-btn>
    </v-col>
    <v-col class="d-flex align-center justify-center ml-1" style="gap: 10px;">
      <v-icon>mdi-account</v-icon>
      <h2>Candidato</h2>
    </v-col>
    <v-col class="d-flex flex-wrap align-center justify-center" style="gap: 10px;">
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-delete"
        text="Eliminar"
        color="red"
        @click="deleteCandidate()"
      ></v-btn>
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-sync"
        text="Repor Valores"
        color="green"
        variant="tonal"
        @click="getCandidate()"
      ></v-btn>
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-content-save"
        text="Guardar"
        color="primary"
        @click="submitForm()"
      ></v-btn>
    </v-col>
  </v-row>


  <v-col class="justify-center align-center d-flex">
    <v-form ref="form" style="width: 80%;">
        
      <v-card-text>
        <v-number-input
          label="IST ID*" 
          required 
          v-model.number="candidate.istId"
          :min="0"
          disabled
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
    </v-form>
  </v-col>
</template>

<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput';
import { VForm } from 'vuetify/lib/components/index.mjs';
import CandidateDto from '@/models/CandidateDto';
import router from '@/router';
import RemoteService from '@/services/RemoteService';
import { ref } from 'vue';

const props = defineProps<{
  istId: number;
}>();

const form = ref(null as VForm)
const candidate = ref(new CandidateDto({}));

const getCandidate = async () => {
  RemoteService.getCandidate(props.istId).then((data) => {
    candidate.value = data;
  });
};

getCandidate();
const submitForm = () => {
  form.value!.validate().then(({valid}) => {
    if (valid) saveCandidate()
  })
}

const saveCandidate = async () => {
  await RemoteService.updateCandidate(candidate.value)
  getCandidate()
}

const deleteCandidate = () => {
  RemoteService.deleteCandidate(candidate.value).then(() => {
    getCandidate();
    router.back();
  });
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