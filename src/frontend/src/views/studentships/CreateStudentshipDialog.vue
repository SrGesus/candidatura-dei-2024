<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Bolsa"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-briefcase" title="Nova Bolsa">
        <v-form ref="form" @submit.prevent="submitForm()">
        <v-card-text>
          
          <v-date-input
            label="Data de Início*" 
            required 
            v-model="newStudentship.startDate"
            :rules="startDateRules"
          ></v-date-input>
          
          <v-date-input
            label="Data de Fim*" 
            required 
            v-model="newStudentship.endDate"
            :rules="endDateRules"
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
import { VDateInput } from 'vuetify/labs/VDateInput'
import { ref } from 'vue'
import type StudentshipDto from '@/models/studentships/StudentshipDto'
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const form = ref(null)

const emit = defineEmits(['studentship-created'])

const submitForm = async () => {
  const { valid } = await form.value.validate()
  if (valid) {
    saveStudentship()
    dialog.value = false;
  }
}

const newStudentship = ref<StudentshipDto>({
  startDate: new Date(),
  endDate: new Date(),
  pay: 0,
  vacancies: 0
})

const saveStudentship = async () => {
  console.log(newStudentship.value)
  await RemoteService.createStudentship(newStudentship.value)
  newStudentship.value = {
    startDate: new Date(),
    endDate: new Date(),
    pay: 0,
    vacancies: 0
  }
  emit('studentship-created')
}

const startDateRules = [
  (date: string) => new Date(date) ? true : 'Data de início é obrigatória.'
];

const endDateRules = [
  (date: string) =>  new Date(date) ? true : 'Data de fim é obrigatória.',
  (date: string) => new Date(date) > newStudentship.value.startDate ? true : 'Data de Fim tem de ser depois da Data de Início.',
];

const payRules = [
  (pay: number) => pay > 0 ? true : 'Valor mensal é obrigatório.'
];

const vacanciesRules = [
  (vacancies: number) => vacancies > 0 ? true : 'Tem de haver pelo menos uma vaga.'
];

</script>
