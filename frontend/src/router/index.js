import { createRouter, createWebHistory } from "vue-router";
import { useStore } from "vuex"; // Import Vuex to check authentication state

import Login from "@/views/Login.vue";
import CreateAccount from "@/views/CreateAccount.vue";
import MainLayout from "@/components/MainLayout.vue";
import Home from "@/views/Home.vue";
import Deposits from "@/views/Deposits.vue";
import Transfer from "@/views/Transfer.vue";
import Transactions from "@/views/Transactions.vue";
import Savings from "@/views/Savings.vue";
import Loans from "@/views/Loans.vue";
import Settings from "@/views/Settings.vue";
import ResetPassword from "@/views/Reset.vue";
import NotFoundView from "@/views/NotFound.vue";

// Define routes
const routes = [
  { path: "/", name: "Login", component: Login },
  { path: "/create-account", name: "CreateAccount", component: CreateAccount },
  {
    path: "/",
    component: MainLayout,
    children: [
      { path: "home", name: "Home", component: Home, meta: { requiresAuth: true } },
      { path: "deposits", name: "Deposits", component: Deposits, meta: { requiresAuth: true } },
      { path: "transfer", name: "Transfer", component: Transfer, meta: { requiresAuth: true } },
      { path: "transactions", name: "Transactions", component: Transactions, meta: { requiresAuth: true } },
      { path: "savings", name: "Savings", component: Savings, meta: { requiresAuth: true }, beforeEnter: checkHasSavingsAccount },
      { path: "loans", name: "Loans", component: Loans, meta: { requiresAuth: true } },
      { path: "settings", name: "Settings", component: Settings, meta: { requiresAuth: true } },
      { path: "/reset", name: "ResetPassword", component: ResetPassword, meta: { requiresAuth: true } },
    ],
  },
  { path: "/:catchAll(.*)", component: NotFoundView }, // Catch-all for invalid paths
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Add a global navigation guard
router.beforeEach((to, from, next) => {
  const store = useStore(); // Access Vuex store

  store.dispatch("updateAccounts");

  // Check if the route requires authentication
  if (to.meta.requiresAuth && !store.getters.isAuthenticated) {
    next("/"); // Redirect to login page if not authenticated
  } else {
    next(); // Proceed to the route
  }
});

// prevent people with savings accounts from opening a new one
function checkHasSavingsAccount(to, from, next) {
  const store = useStore(); // Access Vuex store

  if (store.getters.savingsAccount.accountID != null) {
    next("/Home"); // Redirect to home if they already have a checking account
  } else {
    next(); // Proceed to the route
  }
}

export default router;
