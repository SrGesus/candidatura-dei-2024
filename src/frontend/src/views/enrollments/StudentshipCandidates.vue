<template>
  <EditStudentship :studentshipId="props.id" />
</template>

<script setup lang="ts">

import StudentshipDto from '@/models/studentships/StudentshipDto';
import RemoteService from '@/services/RemoteService';
import { ref } from 'vue';

const props = defineProps<{
  id: number;
}>();

const studentship = ref(null)

import EditStudentship from './EditStudentship.vue';

</script>
