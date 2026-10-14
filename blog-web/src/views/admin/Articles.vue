<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { api, get } from "../../api/http";
import { useRouter } from "vue-router";

type Article = { id: number; title: string; slug: string; status: string; categoryId?: number; tags?: { name: string; slug: string }[]; updatedAt?: string };
type Category = { id: number; name: string };
const rows = ref<Article[]>([]); const categories = ref<Category[]>([]); const search = ref(""); const status = ref("ALL"); const loading = ref(true); const router = useRouter();
const filteredRows = computed(() => rows.value.filter((article) => (status.value === "ALL" || article.status === status.value) && `${article.title} ${article.slug}`.toLowerCase().includes(search.value.toLowerCase())));
const categoryName = (id?: number) => categories.value.find((category) => category.id === id)?.name ?? "未分类";
const statusClass = (value: string) => ({ PUBLISHED: "badge--published", ARCHIVED: "badge--archived" }[value] ?? "badge--draft");
const statusText = (value: string) => ({ PUBLISHED: "已发布", ARCHIVED: "已归档", DRAFT: "草稿" }[value] ?? value);
async function load() { try { const [articles, categoryList] = await Promise.all([get<any>("/admin/articles"), get<Category[]>("/categories")]); rows.value = articles.items; categories.value = categoryList; } catch { router.push("/admin/login"); } finally { loading.value = false; } }
async function remove(id: number) { if (confirm("确定删除这篇文章吗？该操作不可撤销。")) { await api.delete(`/admin/articles/${id}`); await load(); } }
onMounted(load);
</script>
<template>
  <section>
    <div class="page-heading"><div><h1>文章管理</h1><p>管理草稿、已发布文章与内容状态。</p></div><RouterLink class="button button--primary" to="/admin/articles/new">＋ 新建文章</RouterLink></div>
    <div class="card">
      <div class="card__header"><h2 class="card__title">全部文章 <span class="table-subtitle">{{ loading ? "正在加载" : `${filteredRows.length} 篇内容` }}</span></h2><div style="display:flex;gap:8px"><input v-model="search" class="input" style="width:210px" placeholder="搜索标题或 slug" /><select v-model="status" class="select" style="width:120px"><option value="ALL">全部状态</option><option value="DRAFT">草稿</option><option value="PUBLISHED">已发布</option><option value="ARCHIVED">已归档</option></select></div></div>
      <div v-if="!loading && filteredRows.length" class="table-wrap"><table class="data-table"><thead><tr><th>文章</th><th>分类 / 标签</th><th>状态</th><th>更新时间</th><th></th></tr></thead><tbody><tr v-for="article in filteredRows" :key="article.id"><td><span class="table-title">{{ article.title }}</span><span class="table-subtitle">/{{ article.slug }}</span></td><td><span class="table-subtitle">{{ categoryName(article.categoryId) }}</span><span v-for="tag in article.tags" :key="tag.slug" class="tag">{{ tag.name }}</span></td><td><span class="badge" :class="statusClass(article.status)">{{ statusText(article.status) }}</span></td><td><span class="table-subtitle">{{ article.updatedAt || "—" }}</span></td><td><div class="table-actions"><RouterLink class="button button--ghost" :to="`/admin/articles/${article.id}/edit`">编辑</RouterLink><button class="button button--ghost button--danger" @click="remove(article.id)">删除</button></div></td></tr></tbody></table></div>
      <div v-else-if="!loading" class="empty-state"><div><div class="empty-state__icon">⌕</div><h3>{{ rows.length ? "没有匹配的文章" : "还没有文章" }}</h3><p>{{ rows.length ? "尝试调整搜索条件或状态筛选。" : "从第一篇文章开始，建立你的知识库。" }}</p><RouterLink v-if="!rows.length" class="button button--primary" style="margin-top:16px" to="/admin/articles/new">新建文章</RouterLink></div></div>
      <div class="pagination-placeholder"><span>分页将在内容量增加后启用</span><span>第 1 页</span></div>
    </div>
  </section>
</template>
