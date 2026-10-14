<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { get } from "../api/http";

type Category = { id: number; name: string; slug: string; description?: string };
type Article = { id: number; title: string; slug: string; summary: string; categoryId?: number; publishedAt?: string };
const route = useRoute();
const rows = ref<Category[]>([]);
const articles = ref<Article[]>([]);
const selectedSlug = computed(() => String(route.query.slug || ""));
const selectedCategory = computed(() => rows.value.find((category) => category.slug === selectedSlug.value));
const selectedArticles = computed(() => selectedCategory.value ? articles.value.filter((article) => article.categoryId === selectedCategory.value?.id) : []);

onMounted(async () => {
  const [categoryList, articlePage] = await Promise.all([get<Category[]>("/categories"), get<{ items: Article[] }>("/articles")]);
  rows.value = categoryList;
  articles.value = articlePage.items;
});
</script>

<template>
  <section>
    <div v-if="selectedCategory" class="category-detail-header">
      <RouterLink class="text-link" to="/categories">← 全部分类</RouterLink>
      <p class="eyebrow">Category</p><h1>{{ selectedCategory.name }}</h1><p>{{ selectedCategory.description || "这个分类下的技术文章。" }}</p>
    </div>
    <div v-else class="blog-hero blog-hero--compact"><p class="eyebrow">Browse</p><h1>分类</h1><p>按主题整理文章与工程笔记。</p></div>

    <div v-if="selectedCategory" class="category-article-list">
      <RouterLink v-for="article in selectedArticles" :key="article.id" class="post-card" :to="`/articles/${article.slug}`"><div class="post-card__meta">{{ article.publishedAt || "近期" }}</div><h2 class="post-card__title">{{ article.title }}</h2><p class="post-card__summary">{{ article.summary }}</p></RouterLink>
      <div v-if="!selectedArticles.length" class="empty-state"><div><div class="empty-state__icon">□</div><h3>这个分类还没有文章</h3><p>内容发布后会自动显示在这里。</p></div></div>
    </div>
    <div v-else-if="rows.length" class="directory-grid"><article v-for="item in rows" :key="item.id" class="directory-card"><span class="directory-card__icon">□</span><h2>{{ item.name }}</h2><p>{{ item.description || "暂未添加分类说明。" }}</p><RouterLink class="text-link" :to="`/categories?slug=${item.slug}`">查看文章 →</RouterLink></article></div>
    <div v-else class="empty-state"><div><div class="empty-state__icon">□</div><h3>暂无分类</h3><p>分类创建后会显示在这里。</p></div></div>
  </section>
</template>
