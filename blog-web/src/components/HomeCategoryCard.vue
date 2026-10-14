<script setup lang="ts">
import type { Component } from "vue";
import PixelStarMascot from "./PixelStarMascot.vue";

defineProps<{
  icon: Component;
  title: string;
  description: string;
  topics: string;
  to: string;
  variant: "basic" | "engineering" | "solution" | "exploration";
}>();
</script>

<template>
  <RouterLink class="home-category-card" :class="`home-category-card--${variant}`" :to="to">
    <div class="home-category-card__top">
      <span class="home-category-card__icon"><component :is="icon" :size="19" :stroke-width="1.8" /></span>
      <span class="card-mascot"><PixelStarMascot :variant="variant" size="sm" /></span>
    </div>
    <span class="home-category-card__content"><strong>{{ title }}</strong><span>{{ description }}</span><small>{{ topics }}</small></span>
    <span class="home-category-card__arrow">探索 <b>→</b></span>
  </RouterLink>
</template>
