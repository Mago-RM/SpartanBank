<template>
  <div class="account-container">
    <h1>Welcome to Spartan Bank</h1>

    <!-- Checking Account Section -->
    <div class="account-section">
      <h2>Checking Account</h2>
      <p>Your current balance is:</p>
      <h3>{{ formatCurrency(getCheckingBalance()) }}</h3>
      <p>Account number: {{ getCheckingAccountNumber() }}</p>
    </div>

    <!-- Savings Account Section -->
    <div class="account-section">
      <h2>Savings Account</h2>
      <p v-if="hasSavingsAccount()">Your current balance is:</p>
      <h3 v-if="hasSavingsAccount()">{{ formatCurrency(getSavingsBalance()) }}</h3>
      <p v-if="hasSavingsAccount()">Account number: {{ getSavingsAccountNumber() }}</p>

      <!-- Conditional Button -->
      <div v-else>
        <p>You do not have a savings account yet.</p>
        <button @click="openSavings" class="open-account-button">Open Savings Account</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  name: "Home",
  setup() {
    const store = useStore();
    return { store };
  },
  methods: {
    openSavings() {
      // Navigate to the "Open Savings Account" form (router-based navigation)
      this.$router.push("/savings");
    },
    getCheckingBalance() {
      return this.store.getters.checkingAccount.balance;
    },
    getCheckingAccountNumber() {
      return this.store.getters.checkingAccount.accountID;
    },
    hasSavingsAccount() {
      return this.store.getters.savingsAccount.accountID != null;
    },
    getSavingsBalance() {
      return this.store.getters.savingsAccount.balance;
    },
    getSavingsAccountNumber() {
      return this.store.getters.savingsAccount.accountID;
    },
    formatCurrency(amount) {
      return `$${Math.abs(amount).toFixed(2)}`;
    },
  },
};
</script>

<style scoped>
.account-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 2rem;
  color: white;
}

h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
}

.account-section {
  background-color: rgba(255, 255, 255, 0.9);
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 300px;
  margin-bottom: 1.5rem;
  color: black;
}

h2 {
  color: #0056b3;
  margin-bottom: 0.5rem;
}

h3 {
  color: #333;
}

.open-account-button {
  background-color: #0056b3;
  color: white;
  border: none;
  padding: 0.7rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
}

.open-account-button:hover {
  background-color: #004094;
}
</style>
