<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { Code2, Lightbulb, Rocket, Sparkles } from "@lucide/vue";
import { get } from "../api/http";
import HomeArticleCard from "../components/HomeArticleCard.vue";
import HomeCategoryCard from "../components/HomeCategoryCard.vue";
import PixelStarMascot from "../components/PixelStarMascot.vue";

type Category = { id: number; name: string; slug: string; description?: string };
type Article = { id: number; title: string; slug: string; summary: string; publishedAt?: string; categoryId?: number; tags?: { name: string; slug: string }[]; categoryName?: string };
const items = ref<Article[]>([]); const categories = ref<Category[]>([]); const loading = ref(true);
const coreCategories = [
  { icon: Code2, variant: "basic" as const, title: "技术基础", slug: "tech-foundations", description: "扎实的底层能力与工程常识。", topics: "Java · JVM · Redis · MySQL · 并发" },
  { icon: Rocket, variant: "engineering" as const, title: "工程实践", slug: "engineering-practice", description: "把技术真正部署到生产环境。", topics: "云原生 · 部署 · CI/CD · 稳定性" },
  { icon: Lightbulb, variant: "solution" as const, title: "技术方案", slug: "technical-solutions", description: "将复杂业务拆解为清晰结构。", topics: "建模 · 流程 · 状态机 · 架构" },
  { icon: Sparkles, variant: "exploration" as const, title: "技术探索", slug: "technical-exploration", description: "面向下一代开发方式的实验。", topics: "ESC · Agent · OpenConver · AI Coding" },
];
const articles = computed(() => { const categoryById = new Map(categories.value.map((category) => [category.id, category.name])); return items.value.map((article) => ({ ...article, categoryName: categoryById.get(article.categoryId ?? -1) })); });
const featured = computed(() => articles.value.slice(0, 3)); const latest = computed(() => articles.value.slice(3));
onMounted(async () => { try { const [articlePage, categoryList] = await Promise.all([get<{ items: Article[] }>("/articles"), get<Category[]>("/categories")]); items.value = articlePage.items; categories.value = categoryList; } finally { loading.value = false; } });
</script>

<template>
  <section class="portal-home">
    <div class="portal-home__backdrop" aria-hidden="true"><span class="portal-orbit portal-orbit--one"></span><span class="portal-orbit portal-orbit--two"></span><span class="portal-pixel portal-pixel--one"></span><span class="portal-pixel portal-pixel--two"></span></div>
    <div class="portal-hero">
      <div class="portal-hero__copy"><p class="portal-kicker"><span></span> PAITRICK BLOG</p><h1>工程化思考，<br /><em>持续技术探索。</em></h1><p class="portal-hero__description">记录从技术基础到云原生实践的思考，也为 ESC、Agent 与 AI Coding 的实验保留空间。</p><div class="portal-hero__signal"><span class="portal-hero__signal-dot"></span> BUILDING IN PUBLIC · SINCE 2026</div></div>
      <div class="portal-hero__aside"><PixelStarMascot variant="exploration" /><span>01</span><p>Notes on<br />building systems.</p></div>
    </div>
    <div class="home-category-grid"><HomeCategoryCard v-for="category in coreCategories" :key="category.slug" :icon="category.icon" :variant="category.variant" :title="category.title" :description="category.description" :topics="category.topics" :to="`/categories?slug=${category.slug}`" /></div>
    <section v-if="featured.length" class="home-section"><div class="home-section__heading"><div><p class="eyebrow">Featured</p><h2>精选文章</h2></div><RouterLink to="/categories" class="text-link">浏览分类 <span>→</span></RouterLink></div><div class="featured-article-grid"><HomeArticleCard v-for="article in featured" :key="article.id" :article="article" featured /></div></section>
    <section id="about" class="about-panel"><div><p class="eyebrow">About</p><h2>一个为工程实践而建的技术站点。</h2></div><p>这里不追逐噪音，专注沉淀可复用的技术理解：从 Java 与数据库，到云原生交付，再到面向 Agent 的架构探索。</p></section>
    <section v-if="latest.length" class="home-section home-section--latest"><div class="home-section__heading"><div><p class="eyebrow">Latest</p><h2>最新文章</h2></div><RouterLink to="/tags" class="text-link">查看标签 <span>→</span></RouterLink></div><div class="latest-article-list"><HomeArticleCard v-for="article in latest" :key="article.id" :article="article" /></div></section>
    <div v-if="!items.length && !loading" class="empty-state portal-empty"><div><div class="empty-state__icon">✦</div><h3>文章正在准备中</h3><p>第一篇文章发布后，会出现在这里。</p></div></div>
  </section>
</template>
