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

        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            type="submit"
            text="Save"
            color="primary"
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
import { VForm } from 'vuetify/lib/components/index.mjs'
import { ref } from 'vue'
import type { Ref } from 'vue'

import type StudentshipDto from '@/models/studentships/StudentshipDto'
import RemoteService from '@/services/RemoteService'
import { useDate } from 'vuetify/lib/framework.mjs'

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)
const newStudentship = ref<StudentshipDto>({})

const emit = defineEmits(['studentship-created'])

const adapter = useDate();

const submitForm = async () => {
  const { valid } = await form.value!.validate()
  if (valid) {
    saveStudentship()
    dialog.value = false;
  }
}

const saveStudentship = async () => {
  console.log(newStudentship.value)
  RemoteService.createStudentship(newStudentship.value).then(() => {
    newStudentship.value = {    }
    emit('studentship-created')
  })
}

const startDateRules = [
  (date: string) => date ? true : 'Data de início é obrigatória.'
];

const endDateRules = [
  (date: string) => date ? true : 'Data de fim é obrigatória.',
  // Note: startDate can actually be undefined, but condition will return false
  () => newStudentship.value.endDate! > newStudentship.value.startDate! ? true : 'Data de Fim tem de ser posterior a Data de Início.',
];

const payRules = [
  (pay: number) => pay > 0 ? true : 'Valor mensal é obrigatório.'
];

const vacanciesRules = [
  (vacancies: number) => vacancies > 0 ? true : 'Tem de haver pelo menos uma vaga.'
];

</script>
