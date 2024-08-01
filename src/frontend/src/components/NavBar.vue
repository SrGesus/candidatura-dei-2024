<template>
  <v-app-bar>
    <v-btn v-for="item in navbarItemsLeft" :key="item.name" :to="item.path">
      {{ item.name }}
      <v-icon color="contrast" :icon="item.icon"></v-icon>
    </v-btn>
    <v-spacer></v-spacer>
    <v-btn v-for="item in navbarItemsRight" :key="item.name" :to="item.path">
      {{ item.name }}
      <v-icon color="contrast" :icon="item.icon"></v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script setup lang="ts">
defineProps<{ 
  navbarItemsRight: { name: string; path: string; icon: string }[],
  navbarItemsLeft: { name: string; path: string; icon: string }[] 
}>()
</script>
