<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { get } from '../api/http'
type Article = { id:number; title:string; slug:string; summary:string; publishedAt:string; tags:{name:string;slug:string}[] }
const items = ref<Article[]>([])
onMounted(async () => { items.value = (await get<{items: Article[]}>('/articles')).items })
</script>
<template><h1>最新文章</h1><article v-for="a in items" :key="a.id"><h2><RouterLink :to="'/articles/'+a.slug">{{ a.title }}</RouterLink></h2><p>{{ a.summary }}</p><small>{{ a.publishedAt || '未发布' }} · <span v-for="t in a.tags" :key="t.slug">#{{t.name}} </span></small></article><p v-if="!items.length">还没有已发布文章。</p></template>
