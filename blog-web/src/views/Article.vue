<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { get } from '../api/http'
import { marked } from 'marked'
const route = useRoute()
const article = ref<any>()
const html = computed(() => article.value ? marked.parse(article.value.content) : '')
onMounted(async () => { article.value = await get('/articles/' + route.params.slug) })
</script>
<template><article v-if="article"><h1>{{ article.title }}</h1><p>{{ article.publishedAt }} · <span v-for="t in article.tags" :key="t.slug">#{{ t.name }} </span></p><div class="markdown" v-html="html"></div></article></template>
