import { createRouter, createWebHistory } from "vue-router";
import calculatorview from "../views/calculatorview.vue";

const routes = [
  {
    path: "/",
    name: "calculatorview",
    component: calculatorview,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
