<script setup lang="ts">
import { computed } from "vue";
import { useRoute } from "vue-router";
import AdminLayout from "./components/AdminLayout.vue";

const route = useRoute();
const isAdmin = computed(() => route.path.startsWith("/admin"));
const isLogin = computed(() => route.path === "/admin/login");
</script>

<template>
  <AdminLayout v-if="isAdmin && !isLogin"><RouterView /></AdminLayout>
  <div v-else-if="isLogin" class="auth-shell"><RouterView /></div>
  <div v-else class="site-shell">
    <header class="site-header">
      <div class="site-header__inner">
        <RouterLink class="brand" to="/"><span class="brand__mark">P</span><span>Paitrick Blog</span></RouterLink>
        <nav class="site-nav" aria-label="主导航">
          <RouterLink to="/">文章</RouterLink>
          <RouterLink to="/categories">分类</RouterLink>
          <RouterLink to="/tags">标签</RouterLink>
          <a href="/#about">关于</a>
          <RouterLink class="site-nav__admin" to="/admin/articles">后台</RouterLink>
        </nav>
      </div>
    </header>
    <main class="site-main"><RouterView /></main>
    <footer class="site-footer">Built for writing, learning, and shipping.</footer>
  </div>
</template>
