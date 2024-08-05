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
      <v-row class="text-center align-center" v-for="(parameter, idx) in params" :key="idx">
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
            @click="deleteParameter(idx)"
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
            @click="params.push({ name: '', weight: 0 })"
            prepend-icon="mdi-plus"
            text="Adicionar Parâmetro"
            color="primary"
          ></v-btn>
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
import RemoteService from '@/services/RemoteService'

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)
const emit = defineEmits(['studentship-saved'])

const params = ref([] as { id?: number, name: string, weight: number }[])

const props = defineProps<{
  id: number;
}>();

const deleteParameter = (idx: number) => {
  params.value.splice(idx, 1);
}

const submitForm = () => {
  form.value!.validate().then(({valid}) => {
    if (valid) {
      saveParameters()
      dialog.value = false
    }
  })
}

const saveParameters = async () => {
  RemoteService.getStudentship(props.id).then(async (data) => {
    data.gradeParameters = params.value;
    RemoteService.updateStudentship(data).then(() => {
      console.log("Value after update: ", data);
      emit('studentship-saved')
    })
  })
}

const getParameters = async () => {
  RemoteService.getStudentship(props.id).then(async (data) => {
    params.value = data ? data.gradeParameters : [];
  })
}
getParameters();

const nameRules = [
  (v: string) => v ? true : 'Nome é obrigatório.',
]

const weightRules = [
  (v: number) => v > 0 ? true : 'Peso deve ser maior que 0.',
];

</script>
