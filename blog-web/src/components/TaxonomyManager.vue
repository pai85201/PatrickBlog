<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { api, get } from "../api/http";
import { useRouter } from "vue-router";

const props = defineProps<{ resource: "category" | "tag" }>();
type Item = { id: number; name: string; slug: string; description?: string; updatedAt?: string };
const router = useRouter();
const items = ref<Item[]>([]);
const loading = ref(true);
const saving = ref(false);
const error = ref("");
const editingId = ref<number | null>(null);
const form = ref({ name: "", slug: "", description: "" });
const isCategory = computed(() => props.resource === "category");
const title = computed(() => (isCategory.value ? "分类管理" : "标签管理"));
const description = computed(() => isCategory.value ? "建立稳定的文章主题与内容栏目。" : "为文章补充可自由组合的技术关键词。");
const endpoint = computed(() => `/admin/${props.resource === "category" ? "categories" : "tags"}`);

function resetForm() { editingId.value = null; form.value = { name: "", slug: "", description: "" }; error.value = ""; }
function edit(item: Item) { editingId.value = item.id; form.value = { name: item.name, slug: item.slug, description: item.description || "" }; error.value = ""; }
async function load() { try { items.value = await get<Item[]>(`/${props.resource === "category" ? "categories" : "tags"}`); } catch { router.push("/admin/login"); } finally { loading.value = false; } }
async function save() { saving.value = true; error.value = ""; const payload = isCategory.value ? form.value : { name: form.value.name, slug: form.value.slug }; try { if (editingId.value) await api.put(`${endpoint.value}/${editingId.value}`, payload); else await api.post(endpoint.value, payload); resetForm(); await load(); } catch { error.value = "保存失败：请检查必填项，slug 必须唯一。"; } finally { saving.value = false; } }
async function remove(item: Item) { if (!confirm(`确定删除「${item.name}」吗？`)) return; try { await api.delete(`${endpoint.value}/${item.id}`); if (editingId.value === item.id) resetForm(); await load(); } catch { error.value = "删除失败，请确认该内容没有被其他数据使用。"; } }
onMounted(load);
</script>

<template>
  <section>
    <div class="page-heading"><div><h1>{{ title }}</h1><p>{{ description }}</p></div><button class="button button--primary" type="button" @click="resetForm">＋ 新建{{ isCategory ? "分类" : "标签" }}</button></div>
    <div class="taxonomy-layout">
      <div class="card"><div class="card__header"><h2 class="card__title">{{ isCategory ? "全部分类" : "全部标签" }}</h2><span class="table-subtitle">{{ loading ? "正在加载" : `${items.length} 个` }}</span></div>
        <div v-if="items.length" class="taxonomy-list"><article v-for="item in items" :key="item.id" class="taxonomy-item"><div class="taxonomy-item__icon">{{ isCategory ? "□" : "#" }}</div><div class="taxonomy-item__content"><strong>{{ item.name }}</strong><span>/{{ item.slug }}</span><p v-if="isCategory">{{ item.description || "暂未添加分类说明。" }}</p></div><div class="taxonomy-item__actions"><button class="button button--ghost" @click="edit(item)">编辑</button><button class="button button--ghost button--danger" @click="remove(item)">删除</button></div></article></div>
        <div v-else-if="!loading" class="empty-state"><div><div class="empty-state__icon">{{ isCategory ? "□" : "#" }}</div><h3>还没有{{ isCategory ? "分类" : "标签" }}</h3><p>创建后即可在文章编辑器中选择。</p></div></div>
      </div>
      <aside class="card taxonomy-form"><div class="card__header"><h2 class="card__title">{{ editingId ? `编辑${isCategory ? "分类" : "标签"}` : `新建${isCategory ? "分类" : "标签"}` }}</h2></div><form class="card__body editor-fields" @submit.prevent="save"><div class="field"><label>名称</label><input v-model="form.name" class="input" :placeholder="isCategory ? '例如：工程实践' : '例如：Kubernetes'" required /></div><div class="field"><label>Slug</label><input v-model="form.slug" class="input" :placeholder="isCategory ? 'engineering-practice' : 'kubernetes'" required /><span class="field-hint">使用小写英文、数字和连字符，且必须唯一。</span></div><div v-if="isCategory" class="field"><label>说明</label><textarea v-model="form.description" class="textarea" placeholder="简要介绍该分类的文章范围"></textarea></div><p v-if="error" class="form-error">{{ error }}</p><div class="taxonomy-form__actions"><button v-if="editingId" class="button" type="button" @click="resetForm">取消</button><button class="button button--primary" :disabled="saving">{{ saving ? "保存中…" : "保存" }}</button></div></form></aside>
    </div>
  </section>
</template>
