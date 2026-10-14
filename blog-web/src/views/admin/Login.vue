<script setup lang="ts">
import { onMounted, ref } from "vue";
import { api } from "../../api/http";
import { useRouter } from "vue-router";
const username = ref("admin"); const password = ref(""); const error = ref(""); const loading = ref(false); const router = useRouter();
async function submit() { loading.value = true; error.value = ""; try { const response = await api.post("/auth/login", { username: username.value, password: password.value }); localStorage.setItem("token", response.data.data.token); router.push("/admin/articles"); } catch { error.value = "登录失败，请检查用户名和密码。"; } finally { loading.value = false; } }
onMounted(() => { if (localStorage.getItem("token")) router.push("/admin/articles"); });
</script>
<template><section class="login-card"><div class="login-brand"><span class="brand__mark">P</span><span>Paitrick Blog</span></div><div><p class="eyebrow">内容工作台</p><h1>欢迎回来</h1><p class="login-copy">登录以管理你的文章与发布内容。</p></div><form class="login-form" @submit.prevent="submit"><div class="field"><label for="username">用户名</label><input id="username" v-model="username" class="input" autocomplete="username" required /></div><div class="field"><label for="password">密码</label><input id="password" v-model="password" class="input" type="password" autocomplete="current-password" required /></div><p v-if="error" class="form-error">{{ error }}</p><button class="button button--primary login-submit" :disabled="loading">{{ loading ? "正在登录…" : "登录后台" }}</button></form><p class="login-footnote">仅限管理员访问</p></section></template>
