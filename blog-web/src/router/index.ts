import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Article from "../views/Article.vue";
import Categories from "../views/Categories.vue";
import Tags from "../views/Tags.vue";
import Login from "../views/admin/Login.vue";
import Articles from "../views/admin/Articles.vue";
import Editor from "../views/admin/Editor.vue";
import AdminCategories from "../views/admin/Categories.vue";
import AdminTags from "../views/admin/Tags.vue";
export default createRouter({ history: createWebHistory(), routes: [
  { path: "/", component: Home }, { path: "/articles/:slug", component: Article }, { path: "/categories", component: Categories }, { path: "/tags", component: Tags },
  { path: "/admin/login", component: Login }, { path: "/admin/articles", component: Articles }, { path: "/admin/articles/new", component: Editor }, { path: "/admin/articles/:id/edit", component: Editor },
  { path: "/admin/categories", component: AdminCategories }, { path: "/admin/tags", component: AdminTags },
] });
