<template>
  <v-dialog v-model="dialog" max-width="400">
    <template v-slot:activator="{ props: activatorProps }">
      <v-icon v-bind="activatorProps" class="mr-2">mdi-pencil</v-icon>
    </template>

    <v-card prepend-icon="mdi-briefcase" title="Editar Bolsa">
        <v-form ref="form" @submit.prevent="submitForm()">
        <v-card-text>

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
import { VDateInput } from 'vuetify/labs/VDateInput'
import { VForm } from 'vuetify/lib/components/index.mjs';
import { ref } from 'vue'
import type { Ref } from 'vue'

import RemoteService from '@/services/RemoteService'
import type StudentshipDto from '@/models/studentships/StudentshipDto';

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)

const props = defineProps<{
  studentship: StudentshipDto;
}>();

const emit = defineEmits(['studentship-edited'])

const newStudentship = ref<StudentshipDto>({ 
  ...props.studentship,
  startDate: new Date(props.studentship.startDate!),
  endDate: new Date(props.studentship.endDate!)
})

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
  emit('studentship-edited')
}

const startDateRules = [
  (date: string) => date ? true : 'Data de início é obrigatória.'
];

const endDateRules = [
  (date: string) => date ? true : 'Data de fim é obrigatória.',
  // Note: startDate can actually be undefined, but then condition returns false
  (date: string) => newStudentship.value.endDate! > newStudentship.value.startDate! ? true : 'Data de Fim tem de ser depois da Data de Início.',
];

const payRules = [
  (pay: number) => pay > 0 ? true : 'Valor mensal é obrigatório.'
];

const vacanciesRules = [
  (vacancies: number) => vacancies > 0 ? true : 'Tem de haver pelo menos uma vaga.'
];


</script>
