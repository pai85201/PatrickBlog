<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { get, api } from '../../api/http'
import { useRouter } from 'vue-router'
const rows = ref<any[]>([])
const router = useRouter()
async function load(){ try { rows.value = (await get<any>('/admin/articles')).items } catch { router.push('/admin/login') } }
async function del(id:number){ if(confirm('确定删除？')){ await api.delete('/admin/articles/'+id); load() } }
onMounted(load)
</script>
<template><div class="toolbar"><h1>文章管理</h1><RouterLink class="button" to="/admin/articles/new">新建文章</RouterLink></div><table><thead><tr><th>标题</th><th>状态</th><th>更新时间</th><th></th></tr></thead><tbody><tr v-for="a in rows" :key="a.id"><td>{{a.title}}</td><td>{{a.status}}</td><td>{{a.updatedAt}}</td><td><RouterLink :to="'/admin/articles/'+a.id+'/edit'">编辑</RouterLink> <button @click="del(a.id)">删除</button></td></tr></tbody></table></template>
