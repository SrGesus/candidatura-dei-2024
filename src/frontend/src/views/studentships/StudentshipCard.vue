<template>
  <v-row class="d-flex align-center" style="margin-bottom: 1%; margin-top: 1%; ">
    <v-col class="d-flex align-center justify-start ml-1" style="gap: 10px;">
      <v-btn
        class="text-none font-weight-regular"
        icon="mdi-arrow-left"
        color="tonal"
        @click="back()"
      ></v-btn>
    </v-col>
    <v-col class="d-flex align-center justify-center ml-1" style="gap: 10px;">
      <v-icon>mdi-briefcase</v-icon>
      <h2>Bolsa</h2>
    </v-col>
    <v-col class="d-flex flex-wrap align-center justify-center" style="gap: 10px;">
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-delete"
        text="Eliminar"
        color="red"
        @click="deleteStudentship()"
      ></v-btn>
      <v-btn
        class="text-none font-weight-regular"
        prepend-icon="mdi-sync"
        text="Repor Valores"
        color="green"
        variant="tonal"
        @click="getStudentship()"
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
    <v-form ref="form" @submit.prevent="submitForm()" style="width: 80%;">
      <v-number-input
        label="ID*"
        required 
        v-model.number="newStudentship.id"
        disabled
      ></v-number-input>

      <v-date-input
        label="Data de Início*" 
        required
        readonly
        v-model="newStudentship.startDate"
        :rules="startDateRules"
        prepend-icon=""
        prepend-inner-icon="$calendar"
        placeholder="dd/mm/yyyy"
        min="2020-01-01"
      ></v-date-input>
      
      <v-date-input
        label="Data de Fim*" 
        required
        readonly
        v-model="newStudentship.endDate"
        :rules="endDateRules"
        prepend-icon=""
        prepend-inner-icon="$calendar"
        placeholder="dd/mm/yyyy"
        min="2020-01-01"
      ></v-date-input>

      <v-number-input
        label="Valor mensal*" 
        required 
        v-model.number="newStudentship.pay"
        :rules="payRules"
        :step="20.0"
        prefix="€"
      ></v-number-input>

      <v-number-input
        label="Vagas*" 
        required 
        v-model.number="newStudentship.vacancies"
        :rules="vacanciesRules"
      ></v-number-input>
  </v-form>
  </v-col>
</template>

<script setup lang="ts">
import { VNumberInput } from 'vuetify/labs/VNumberInput'
import { VDateInput } from 'vuetify/labs/VDateInput'
import { VForm } from 'vuetify/lib/components/index.mjs';
import { ref } from 'vue'
import type { Ref } from 'vue'

import RemoteService from '@/services/RemoteService'
import type StudentshipDto from '@/models/studentships/StudentshipDto';

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)

const props = defineProps<{
  studentshipId: number;
}>();

const newStudentship = ref({
  id: 0,
  startDate: new Date(),
  endDate: new Date(),
  pay: 0,
  vacancies: 0
} as StudentshipDto
);

getStudentship()
async function getStudentship() {
  RemoteService.getStudentship(props.studentshipId).then(async (data) => {
    newStudentship.value = data
    newStudentship.value.startDate = new Date(data.startDate!);
    newStudentship.value.endDate = new Date(data.endDate!);
  })
}


const submitForm = async () => {
  const { valid } = await form.value!.validate()
  if (valid) {
    saveStudentship()
    dialog.value = false;
  }
}

const saveStudentship = async () => {
  console.log(newStudentship.value)
  await RemoteService.updateStudentship(newStudentship.value)
  getStudentship()
}

const startDateRules = [
  (date: string) => date ? true : 'Data de início é obrigatória.'
];

const endDateRules = [
  (date: string) => date ? true : 'Data de fim é obrigatória.',
  // Note: startDate can actually be undefined, but then condition returns false
  () => newStudentship.value.endDate! > newStudentship.value.startDate! ? true : 'Data de Fim tem de ser depois da Data de Início.',
];

const payRules = [
  (pay: number) => pay > 0 ? true : 'Valor mensal é obrigatório.'
];

const vacanciesRules = [
  (vacancies: number) => vacancies > 0 ? true : 'Tem de haver pelo menos uma vaga.'
];

import router from '@/router';

function deleteStudentship() {
  RemoteService.deleteStudentship(newStudentship.value).then(() => {
    back()
  })
}

function back() {
  router.back()
}

</script>
