<template>
<v-dialog v-model="dialog" max-width="600">
  
  <!-- Activator -->
  <template v-slot:activator="{ props: activatorProps }">
    <div v-bind="activatorProps" style="cursor: pointer;">
      <slot></slot>
    </div>
  </template>

  <!-- Dialogue Container -->
  <v-card prepend-icon="mdi-abacus" title="Editar Parâmetros">
  <v-form ref="form" @submit.prevent="submitForm()">

    <v-card-text>
      <v-row class="text-center align-center" v-for="parameter in parameters.concat(newParameters).filter((v) => v.change !== 'delete')" :key="parameter.id">
        <v-col>
          <v-text-field
            v-model="parameter.name"
            label="Nome"
            required
            :rules="nameRules"
            hide-details="auto"
          ></v-text-field>
        </v-col>
        <v-col>
          <v-number-input
            v-model.number="parameter.weight"
            label="Peso"
            required
            :rules="weightRules"
            hide-details="auto"
            control-variant="stacked"
            :step="0.5"
          ></v-number-input>
        </v-col>
        <v-col cols="auto">
          <v-btn
            @click="deleteParameter(parameter)"
            color="error"
            variant="text"
            icon="mdi-delete"
          ></v-btn>
        </v-col>
        <v-divider></v-divider>
      </v-row>
      <v-row>
        <v-col class="text-center">
          <v-btn
            @click="newParameters.push({ name: '', weight: 0 })"
            prepend-icon="mdi-plus"
            text="Adicionar Parâmetro"
            color="primary"
          >
          </v-btn>
        </v-col>
      </v-row>
    </v-card-text>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn 
        text="Cancelar" 
        variant="plain" 
        @click="dialog = false; getParameters()"
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
import type CandidateDto from '@/models/candidates/CandidateDto'
import RemoteService from '@/services/RemoteService'
import GradeParameterDto from '@/models/grades/GradeParameterDto';

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)
const emit = defineEmits(['parameters-saved'])


const props = defineProps<{
  studentshipId: number;
}>();

const deleteParameter = (parameter: GradeParameterDto & {change?: 'update' | 'delete'}) => {
  if (parameter.id) {
    parameter.change = 'delete'
  } else {
    newParameters.value.splice(newParameters.value.indexOf(parameter), 1)
  }
}

const parameters: Ref<(GradeParameterDto & {change?: 'update' | 'delete'})[]> = ref([]);
const newParameters: Ref<GradeParameterDto[]> = ref([]);


const submitForm = () => {
  form.value!.validate().then(({valid}) => {
    if (valid) {
      saveParameters()
      dialog.value = false
    }
  })
}

const saveParameters = async () => {
  for (let parameter of parameters.value) {
    if (parameter.change === 'update') {
      await RemoteService.updateGradeParameter(parameter);
    } else if (parameter.change === 'delete') {
      await RemoteService.deleteGradeParameter(parameter);
    }
  }
  for (let parameter of newParameters.value) {
    parameter.studentshipId = props.studentshipId;
    await RemoteService.createGradeParameter(parameter);
  }
  newParameters.value.splice(0, newParameters.value.length);
  getParameters()
  emit('parameters-saved')
}

getParameters();
async function getParameters() {
  parameters.value.splice(0, parameters.value.length)
  newParameters.value.splice(0, newParameters.value.length);
  RemoteService.getGradeParameters(props.studentshipId).then((data) => {
    data.forEach((parameter) => {
      parameters.value.push(parameter)
    })
  });
}

const nameRules = [
  (v: string) => v ? true : 'Nome é obrigatório.',
]

const weightRules = [
  (v: number) => v > 0 ? true : 'Peso deve ser maior que 0.',
];

</script>
