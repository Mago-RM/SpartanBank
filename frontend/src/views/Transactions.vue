<template>
  <div class="transactions-container">
    <h1>Transaction History</h1>

    <!-- Filters Section -->
    <div class="filters">
      <label for="account">Account:</label>
      <select id="account" v-model="selectedAccount">
        <option value="">All Accounts</option>
        <option v-bind:value="this.store.getters.checkingAccount.accountID">Checking</option>
        <option v-bind:value="this.store.getters.savingsAccount.accountID">Savings</option>
      </select>

      <label for="type">Type:</label>
      <select id="type" v-model="selectedType">
        <option value="">All Types</option>
        <option value="deposit">Deposit</option>
        <option value="withdrawal">Withdrawal</option>
        <option value="transfer">Transfer</option>
      </select>
    </div>

    <!-- Transactions Table -->
    <table class="transactions-table">
      <thead>
        <tr>
          <th>Date</th>
          <th>Type</th>
          <th>From Account #</th>
          <th>To Account #</th>
          <th>Amount</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="transaction in filteredTransactions" :key="transaction.id">
          <td>{{ new Date(transaction.date).toDateString() }}</td>
          <td>{{ formatType(transaction.type) }}</td>
          <td>{{ transaction.from_account }}</td>
          <td>{{ transaction.to_account }}</td>
          <td :class="{ positive: transaction.type === 'deposit', negative: transaction.type === 'withdrawal' }">
            {{ formatCurrency(transaction.amount) }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
  name: "Transactions",
  data() {
    return {
      // Example transactions data; replace with API data later
      selectedAccount: "", // Filter by account type
      selectedType: "", // Filter by transaction type
    };
  },
  setup() {
    const store = useStore();
    
    store.dispatch("updateTransactions")
    
    return { store };
  },
  computed: {
    filteredTransactions() {
      return this.store.getters.transactions.filter((transaction) => {
        const matchesAccount =
          !this.selectedAccount || transaction.to_account === this.selectedAccount || transaction.from_account === this.selectedAccount;
        const matchesType =
          !this.selectedType || this.formatType(transaction.type) === this.selectedType;
        return matchesAccount && matchesType;
      });
    }
  },
  methods: {
    formatCurrency(amount) {
      return `$${Math.abs(amount).toFixed(2)}`;
    },
    formatType(type) {
      if (type == 0) {
        return "transfer"
      } else if (type == 1) {
        return "deposit"
      } else if (type == -1) {
        return "withdrawal"
      }
    }
  },
};
</script>

<style scoped>
.transactions-container {
  max-width: 800px;
  margin: 2rem auto;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  color: #0056b3;
  margin-bottom: 1.5rem;
}

.filters {
  display: flex;
  justify-content: space-around;
  margin-bottom: 1.5rem;
}

label {
  font-weight: bold;
  margin-right: 0.5rem;
}

select {
  padding: 0.5rem;
  font-size: 1rem;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.transactions-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

.transactions-table th,
.transactions-table td {
  border: 1px solid #ddd;
  padding: 0.8rem;
  text-align: left;
}

.transactions-table th {
  background-color: #0056b3;
  color: white;
}

.transactions-table td {
  background-color: #f9f9f9;
}

.transactions-table td.positive {
  color: green;
}

.transactions-table td.negative {
  color: red;
}

.transactions-table tr:hover {
  background-color: #f1f1f1;
}
</style>
