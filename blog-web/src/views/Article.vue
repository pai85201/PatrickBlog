<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { get } from "../api/http";
import { marked } from "marked";
const route = useRoute(); const article = ref<any>(); const loading = ref(true);
const html = computed(() => article.value ? marked.parse(article.value.content) : "");
onMounted(async () => { try { article.value = await get(`/articles/${route.params.slug}`); } finally { loading.value = false; } });
</script>
<template><article v-if="article" class="article-page"><header class="article-header"><p class="eyebrow">文章</p><h1>{{ article.title }}</h1><div class="article-header__meta"><span>{{ article.publishedAt || "未发布" }}</span><span v-if="article.categoryId">· 分类 #{{ article.categoryId }}</span><span v-for="tag in article.tags" :key="tag.slug" class="tag">{{ tag.name }}</span></div></header><div class="markdown" v-html="html"></div></article><div v-else-if="!loading" class="empty-state"><div><div class="empty-state__icon">!</div><h3>文章未找到</h3><p>该链接可能已失效，或文章暂未发布。</p><RouterLink class="button" style="margin-top:16px" to="/">返回首页</RouterLink></div></div></template>
