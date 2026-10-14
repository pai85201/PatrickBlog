<script setup lang="ts">
import { onMounted, ref } from "vue";
import { get } from "../api/http";
const rows = ref<any[]>([]);
onMounted(async () => { rows.value = await get<any[]>("/tags"); });
</script>
<template><section><div class="blog-hero blog-hero--compact"><p class="eyebrow">Browse</p><h1>标签</h1><p>用更细的维度浏览主题。</p></div><div v-if="rows.length" class="tag-directory"><article v-for="item in rows" :key="item.id" class="tag-directory__item"><span class="tag-directory__hash">#</span><div><h2>{{ item.name }}</h2><p>/{{ item.slug }}</p></div></article></div><div v-else class="empty-state"><div><div class="empty-state__icon">#</div><h3>暂无标签</h3><p>标签创建后会显示在这里。</p></div></div></section></template>
