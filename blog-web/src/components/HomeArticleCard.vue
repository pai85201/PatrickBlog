<script setup lang="ts">
import { computed } from "vue";
import { Bot, Database, FileCode2, Rocket } from "@lucide/vue";
type Article = { title:string; slug:string; summary:string; publishedAt?:string; categoryName?:string; tags?:{name:string;slug:string}[] };
const props = defineProps<{ article:Article; featured?:boolean }>();
const context = computed(() => `${props.article.categoryName || ""} ${props.article.tags?.map((tag) => tag.name).join(" ") || ""}`.toLowerCase());
const articleIcon = computed(() => /(esc|agent|ai|探索)/.test(context.value) ? Bot : /(基础|java|jvm|mysql|redis|数据库)/.test(context.value) ? Database : /(工程|部署|云原生|ci)/.test(context.value) ? Rocket : FileCode2);
const tone = computed(() => /(esc|agent|ai|探索)/.test(context.value) ? "exploration" : /(基础|java|jvm|mysql|redis|数据库)/.test(context.value) ? "foundation" : /(工程|部署|云原生|ci)/.test(context.value) ? "engineering" : "solution");
</script>
<template><RouterLink class="home-article-card" :class="[{ 'home-article-card--featured': featured }, `home-article-card--${tone}`]" :to="`/articles/${article.slug}`"><div class="home-article-card__visual"><span class="home-article-card__icon"><component :is="articleIcon" :size="20" :stroke-width="1.8" /></span><span class="home-article-card__index">{{ featured ? "FEATURED" : "NOTE" }}</span></div><div class="home-article-card__meta"><span>{{ article.categoryName || "未分类" }}</span><span>·</span><span>{{ article.publishedAt || "近期" }}</span></div><h3>{{ article.title }}</h3><p>{{ article.summary || "暂无摘要" }}</p><div class="home-article-card__footer"><span class="home-article-card__tags"><span v-for="tag in article.tags?.slice(0,3)" :key="tag.slug" class="tag">{{ tag.name }}</span></span><span class="home-article-card__read">阅读 <b>→</b></span></div></RouterLink></template>
