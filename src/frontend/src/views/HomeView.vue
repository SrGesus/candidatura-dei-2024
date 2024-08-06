<template>
  <div class="mb-6">
    <h1>Home</h1>
  </div>

  <v-row class="mb-6 mt-6" cols="auto">
    <v-col>
      <v-btn
        class="text-none font-weight-regular"
        block append-icon="mdi-briefcase"
        text="Bolsas" color="secondary"
        size="x-large" to="studentships"
        ></v-btn>
    </v-col>
    <v-col>
      <v-btn
        class="text-none font-weight-regular"
        block append-icon="mdi-account"
        text="Candidatos" color="secondary"
        size="x-large" to="candidates"
        ></v-btn>
    </v-col>
    <v-col>
      <v-btn
        class="text-none font-weight-regular"
        block append-icon="mdi-form-select"
        text="Bolsas" color="secondary"
        size="x-large" to="enrollments"
        ></v-btn>
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import router from '@/router';

</script>
