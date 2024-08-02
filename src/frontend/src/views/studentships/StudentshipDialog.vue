<template>
<v-dialog v-model="dialog" max-width="400">
  
  <!-- Activator -->
  <template v-slot:activator="{ props: activatorProps }">
    <div v-bind="activatorProps" style="cursor: pointer;">
      <slot></slot>
    </div>
  </template>

  <!-- Dialogue Container -->
  <v-card prepend-icon="mdi-briefcase" :title="title">
  <v-form ref="form" @submit.prevent="submitForm()">

    <v-card-text>
      <v-number-input
        label="ID*" 
        required 
        v-model.number="studentship.id"
        disabled
        v-if="props.edit"
      ></v-number-input>

      <v-date-input
        label="Data de Início*" 
        required
        readonly
        v-model="studentship.startDate"
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
        v-model="studentship.endDate"
        :rules="endDateRules"
        prepend-icon=""
        prepend-inner-icon="$calendar"
        placeholder="dd/mm/yyyy"
        min="2020-01-01"
      ></v-date-input>

      <v-number-input
        label="Valor mensal*" 
        required 
        v-model.number="studentship.pay"
        :rules="payRules"
        :step="20.0"
        prefix="€"
      ></v-number-input>
      
      <v-number-input
        label="Vagas*" 
        required 
        v-model.number="studentship.vacancies"
        :rules="vacanciesRules"
      ></v-number-input>
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
import { VDateInput } from 'vuetify/labs/VDateInput';
import { VForm } from 'vuetify/lib/components/index.mjs';

import { ref } from 'vue'
import type { Ref } from 'vue'
import type StudentshipDto from '@/models/studentships/StudentshipDto'
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)
const emit = defineEmits(['studentship-saved'])

const props = defineProps<{
  edit: false | StudentshipDto;
}>();

const title = props.edit ? 'Editar Bolsa' : 'Nova Bolsa';

const studentship = ref<StudentshipDto>(
  props.edit ? { 
    ...props.edit,
    startDate: new Date(props.edit.startDate!),
    endDate: new Date(props.edit.endDate!)
  } : {}
)

const submitForm = () => {
  form.value!.validate().then(({valid}) => {
    if (valid) {
      saveStudentship()
      dialog.value = false
    }
  })
}

const saveStudentship = async () => {
  if (props.edit) {
    await RemoteService.updateStudentship(studentship.value)
  } else {
    await RemoteService.createStudentship(studentship.value)
  }
  studentship.value = {}
  emit('studentship-saved')
}

const startDateRules = [
  (date: string) => date ? true : 'Data de início é obrigatória.'
];

const endDateRules = [
  (date: string) => date ? true : 'Data de fim é obrigatória.',
  // Note: startDate can actually be undefined, but then condition returns false
  () => studentship.value.endDate! > studentship.value.startDate! ? true : 'Data de Fim tem de ser depois da Data de Início.',
];

const payRules = [
  (pay: number) => pay > 0 ? true : 'Valor mensal é obrigatório.'
];

const vacanciesRules = [
  (vacancies: number) => vacancies > 0 ? true : 'Tem de haver pelo menos uma vaga.'
];

</script>
