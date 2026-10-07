<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { get } from '../api/http'
const rows = ref<any[]>([])
onMounted(async () => { rows.value = await get('/tags') })
</script>
<template><h1>标签</h1><article v-for="x in rows" :key="x.id"><h2># {{ x.name }}</h2><small>{{ x.slug }}</small></article></template>
