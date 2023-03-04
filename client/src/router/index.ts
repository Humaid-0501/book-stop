import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CategoryView from "@/views/CategoryView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
import CartView from "@/views/CartView.vue";
import ConfirmationView from "@/views/ConfirmationView.vue";
import NotFound from "@/views/NotFound.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/home",
    name: "home-view",
    component: HomeView,
    alias: ["/", "/index.html"],
  },
  {
    path: "/category",
    redirect: "/category/Bestseller",
  },
  {
    path: "/category/:name",
    name: "category-view",
    component: CategoryView,
    props: true,
  },
  {
    path: "/cart",
    name: "cart-view",
    component: CartView,
  },
  {
    path: "/checkout",
    name: "checkout-view",
    component: CheckoutView,
  },
  {
    path: "/confirmation",
    name: "confirmation-view",
    component: ConfirmationView,
  },
  {
    path: "/:pathMatch(.*)*",
    name: "not-found",
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
