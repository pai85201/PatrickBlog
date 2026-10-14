<script setup lang="ts">
import { computed } from "vue";

const props = withDefaults(
  defineProps<{
    variant?: "basic" | "engineering" | "solution" | "exploration";
    size?: "sm" | "md";
    decorative?: boolean;
  }>(),
  {
    variant: "basic",
    size: "md",
    decorative: true,
  },
);

const label = computed(() => `${props.variant} original pixel star mascot`);
</script>

<template>
  <span
    class="pixel-star-mascot"
    :class="[`pixel-star-mascot--${size}`, `pixel-star-mascot--${variant}`]"
    :aria-hidden="decorative"
    :aria-label="decorative ? undefined : label"
    role="img"
  >
    <svg viewBox="0 0 96 72" shape-rendering="crispEdges" xmlns="http://www.w3.org/2000/svg">
      <ellipse class="pixel-star-mascot__shadow" cx="49" cy="61" rx="33" ry="5" />

      <!-- A deliberately squat, leaning star silhouette: an original blog mascot. -->
      <path
        class="pixel-star-mascot__outline"
        d="M30 12h10v5h8v6h8v-5h10v8h7v9h-8v6h10v10H61v-6h-8v10H39v-8H27v5H14V42h8v-8H8V24h13v-6h9z"
      />
      <path
        class="pixel-star-mascot__body"
        d="M31 16h8v5h8v7h11v-5h5v8h7v3h-9v11h10v4H58v-6h-9v11h-7v-8H29v4H18v-5h8v-9H12v-8h13v-7h6z"
      />
      <path class="pixel-star-mascot__shade" d="M18 42h11v4h13v8H29v-4H18zm40-14h5v10h-5z" />
      <path class="pixel-star-mascot__cheek" d="M29 34h5v4h-5zm18 0h5v4h-5z" />
      <g class="pixel-star-mascot__face">
        <rect x="34" y="29" width="4" height="6" />
        <rect x="45" y="29" width="4" height="6" />
        <rect x="39" y="38" width="6" height="3" />
        <rect x="41" y="41" width="2" height="2" />
      </g>
      <g class="pixel-star-mascot__highlight">
        <rect x="35" y="29" width="1" height="2" />
        <rect x="46" y="29" width="1" height="2" />
      </g>

      <!-- Variant details keep the decoration contextual without overpowering it. -->
      <g v-if="variant === 'engineering'" class="pixel-star-mascot__laptop">
        <rect x="65" y="40" width="14" height="9" />
        <rect x="67" y="42" width="10" height="5" />
        <rect x="63" y="49" width="18" height="3" />
      </g>
      <g v-else-if="variant === 'solution'" class="pixel-star-mascot__bulb">
        <rect x="67" y="12" width="7" height="8" />
        <rect x="69" y="9" width="3" height="3" />
        <rect x="69" y="20" width="3" height="4" />
        <rect x="65" y="14" width="2" height="4" />
        <rect x="74" y="14" width="2" height="4" />
      </g>
      <g v-else-if="variant === 'exploration'" class="pixel-star-mascot__sparkle">
        <rect x="69" y="14" width="3" height="13" />
        <rect x="64" y="19" width="13" height="3" />
        <rect x="67" y="17" width="7" height="7" />
        <rect x="79" y="29" width="3" height="3" />
      </g>
      <g v-else class="pixel-star-mascot__sparkle">
        <rect x="17" y="13" width="3" height="11" />
        <rect x="13" y="17" width="11" height="3" />
      </g>
    </svg>
  </span>
</template>

<style scoped>
.pixel-star-mascot {
  display: inline-flex;
  line-height: 0;
  filter: drop-shadow(0 5px 4px rgb(68 83 151 / 20%));
  transform-origin: 50% 70%;
  animation: pixel-star-float 3.8s ease-in-out infinite;
}

.pixel-star-mascot--sm {
  width: 66px;
  height: 54px;
}

.pixel-star-mascot--md {
  width: 104px;
  height: 82px;
}

svg {
  width: 100%;
  height: 100%;
  overflow: visible;
}

.pixel-star-mascot__shadow { fill: rgb(69 85 160 / 20%); }
.pixel-star-mascot__outline { fill: #5d5788; }
.pixel-star-mascot__body { fill: #ff9fbd; }
.pixel-star-mascot__shade { fill: #e978a2; }
.pixel-star-mascot__cheek { fill: #f284aa; }
.pixel-star-mascot__face { fill: #443653; }
.pixel-star-mascot__highlight { fill: #fff9fb; }
.pixel-star-mascot__laptop { fill: #6375c9; }
.pixel-star-mascot__laptop rect:nth-child(2) { fill: #d9efff; }
.pixel-star-mascot__bulb { fill: #f3bf52; }
.pixel-star-mascot__sparkle { fill: #70b9ff; }
.pixel-star-mascot--exploration .pixel-star-mascot__sparkle { fill: #8d7ff3; }

@keyframes pixel-star-float {
  0%,
  100% { transform: translateY(0) rotate(-1deg); }
  50% { transform: translateY(-3px) rotate(1deg); }
}

@media (prefers-reduced-motion: reduce) {
  .pixel-star-mascot { animation: none; }
}
</style>