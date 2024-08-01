<template>
  <UtilBar />
  <NavBar 
    :navbarItemsRight="navbarItemsRight" 
    :navbarItemsLeft="navbarItemsLeft" 
  />
</template>

<script setup lang="ts">
import UtilBar from '@/components/UtilBar.vue'
import NavBar from '@/components/NavBar.vue'

const navbarItemsRight = [
  { name: 'Bolsas', path: '/studentships', icon: 'mdi-briefcase' },
  { name: 'Candidatos', path: '/candidates', icon: 'mdi-account-group' },
]

const navbarItemsLeft = [
  { name: 'Home', path: '/', icon: 'mdi-home' },
]

</script>
