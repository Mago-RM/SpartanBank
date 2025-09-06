import { createStore } from "vuex";
import { listAccounts, fetchTransactions, getCustomerInfo } from "../api.js";

const store = createStore({
  state: {
    user: null, // Store user data here
    isAuthenticated: false, // Track if the user is logged in
    checkingAccount: {"accountID": null, "customerID": null, "accountType": null, "balance": 0},
    savingsAccount: {"accountID": null, "customerID": null, "accountType": null, "balance": 0},
    transactions: [],
    customerInfo: null
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      state.isAuthenticated = true;
    },
    logout(state) {
      state.user = null;
      state.isAuthenticated = false;
    },
    async updateAccounts(state) {
      const accounts = await listAccounts(state.user);

      for (var account of accounts) {
        if (account.accountType == 0) {
          state.checkingAccount = account
        }
        if (account.accountType == 1) {
          state.savingsAccount = account
        }
      }
    },
    async updateTransactions(state) {
      const transactions = await fetchTransactions(state.user);
      
      state.transactions = transactions;
    },
    async updateCustomer(state) {
      const customer = await getCustomerInfo(state.user)

      state.customerInfo = customer;
    }
  },
  actions: {
    login({ commit }, user) {
      commit("setUser", user);
    },
    logout({ commit }) {
      commit("logout");
    },
    updateAccounts({ commit }) {
      commit("updateAccounts");
    },
    updateTransactions({ commit }) {
      commit("updateTransactions");
    },
    updateCustomer({ commit }) {
      commit("updateCustomer");
    }
  },
  getters: {
    isAuthenticated: (state) => state.isAuthenticated,
    currentUser: (state) => state.user,
    checkingAccount: (state) => state.checkingAccount,
    savingsAccount: (state) => state.savingsAccount,
    transactions: (state) => state.transactions,
    customer: (state) => state.customerInfo
  },
});

export default store;
