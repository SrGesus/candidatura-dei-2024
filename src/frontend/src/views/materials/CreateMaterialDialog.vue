<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Material"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Material">
        <v-form ref="form" @submit.prevent="submitForm()">
        <v-card-text>

            <v-text-field 
              label="Nome*" 
              required 
              v-model="newMaterial.name"
              :rules="nameRules"
            ></v-text-field>
  
            <v-autocomplete
              :items="types"
              :rules="typeRules"
              label="Categoria*"
              auto-select-first
              required
              v-model="newMaterial.type"
            ></v-autocomplete>
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
import { ref } from 'vue'
import type { Ref } from 'vue'
import type MaterialDto from '@/models/materials/MaterialDto'
import RemoteService from '@/services/RemoteService'
import { VForm } from 'vuetify/lib/components/index.mjs';

const dialog = ref(false)
const form: Ref<null | VForm> = ref(null)

const emit = defineEmits(['material-created'])

const submitForm = async () => {
  const { valid } = await form.value!.validate()
  if (valid) {
    saveMaterial()
    dialog.value = false;
  }
}

const typeMappings = {
  Chave: 'KEY',
  Cacifo: 'LOCKER',
  Genérico: 'GENERIC'
}

const newMaterial = ref<MaterialDto>({
  name: '',
  type: ''
})

const saveMaterial = async () => {
  newMaterial.value.type = typeMappings[(newMaterial.value.type) as keyof typeof typeMappings]

  await RemoteService.createMaterial(newMaterial.value)
  newMaterial.value = {
    name: '',
    type: ''
  }
  emit('material-created')
}

const nameRules = [
  (name: string) => {
    return name ? true : 'Nome é obrigatório.'
  },
];

const types = ['Chave', 'Cacifo', 'Genérico'];

// FIXME wrong rule
const typeRules = [
  (type: string) => {
    return type ? true : 'Tipo é obrigatório.'
  },
];

</script>
