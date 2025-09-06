<template>
  <div class="transfer-container">
    <h1>Mobile Transfers</h1>

    <!-- Tab Navigation -->
    <div class="tabs">
      <button
        class="tab-button"
        :class="{ active: activeTab === 'own' }"
        @click="activeTab = 'own'"
      >
        Between My Accounts
      </button>
      <button
        class="tab-button"
        :class="{ active: activeTab === 'friend' }"
        @click="activeTab = 'friend'"
      >
        Another Person
      </button>
    </div>

    <!-- Transfer Between Accounts -->
    <div v-if="activeTab === 'own'" class="form-section">
      <h2>Transfer Between My Accounts</h2>
      <div class="form-group">
        <label for="fromAccount">From Account:</label>
        <select id="fromAccount" v-model="fromAccount" class="account-select">
          <option value="" disabled>Select an account</option>
          <option v-bind:value="this.store.getters.checkingAccount.accountID">Checking Account</option>
          <option v-bind:value="this.store.getters.savingsAccount.accountID">Savings Account</option>
        </select>
      </div>
      <div class="form-group">
        <label for="toAccount">To Account:</label>
        <select id="toAccount" v-model="toAccount" class="account-select">
          <option value="" disabled>Select an account</option>
          <option v-bind:value="this.store.getters.checkingAccount.accountID">Checking Account</option>
          <option v-bind:value="this.store.getters.savingsAccount.accountID">Savings Account</option>
        </select>
      </div>
      <div class="form-group">
        <label for="amount">Transfer Amount:</label>
        <input
          type="number"
          id="amount"
          v-model="transferAmount"
          placeholder="Enter transfer amount"
          class="amount-input"
          min="1"
        />
      </div>
      <div class="button-group">
        <button @click="confirmOwnTransfer" class="confirm-button">Confirm</button>
        <button @click="cancelTransfer" class="cancel-button">Cancel</button>
      </div>
    </div>

    <!-- Transfer to Friend -->
    <div v-if="activeTab === 'friend'" class="form-section">
      <h2>Send Money to Another Person</h2>
      <div class="form-group">
        <div class="form-group">
          <label for="fromAccount">From Account:</label>
          <select id="fromAccount" v-model="fromAccount" class="account-select">
            <option value="" disabled>Select an account</option>
            <option v-bind:value="this.store.getters.checkingAccount.accountID">Checking Account</option>
            <option v-bind:value="this.store.getters.savingsAccount.accountID">Savings Account</option>
          </select>
        </div>
        <label for="friendAccount"> Account Number:</label>
        <input
          type="text"
          id="friendAccount"
          v-model="friendAccount"
          placeholder="Enter account number"
          class="input-field"
        />
      </div>
      <div class="form-group">
        <label for="amount">Transfer Amount:</label>
        <input
          type="number"
          id="amount"
          v-model="transferAmount"
          placeholder="Enter transfer amount"
          class="amount-input"
          min="1"
        />
      </div>
      <div class="button-group">
        <button @click="cancelTransfer" class="cancel-button">Cancel</button>
        <button @click="confirmFriendTransfer" class="confirm-button">
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { balanceTransfer } from '@/api';

export default {
  name: "Transfer",
  data() {
    return {
      activeTab: "own", // 'own' for between accounts, 'friend' for to a friend's account
      fromAccount: "",
      toAccount: "",
      transferAmount: null,
      friendName: "",
      friendEmail: "",
      friendAccount: "",
    };
  },
  setup() {
    const store = useStore();
    
    store.dispatch("updateTransactions")
    
    return { store };
  },
  methods: {
    async confirmOwnTransfer() {
      if (!this.fromAccount || !this.toAccount || !this.transferAmount) {
        alert("Please fill out all fields for the transfer.");
        return;
      }
      if (this.fromAccount === this.toAccount) {
        alert("You cannot transfer to the same account.");
        return;
      }

      try {
        await balanceTransfer(this.store.getters.currentUser, this.fromAccount, this.toAccount, this.transferAmount);
      
        alert(
          `Successfully transferred $${this.transferAmount}.`
        );
        this.resetForm();
      } catch (e) {
        alert('Balance transfer failed. Verify you have enough money in your account.')
      }
    },
    async confirmFriendTransfer() {
      if (
        !this.fromAccount ||
        !this.friendAccount ||
        !this.transferAmount
      ) {
        alert("Please fill out all fields to complete transfer.");
        return;
      }
      try {
        await balanceTransfer(this.store.getters.currentUser, this.fromAccount, this.friendAccount, this.transferAmount);
      
        alert(
          `Transferred $${this.transferAmount} to ${this.friendAccount}.`
        );
        this.resetForm();
      } catch (e) {
        alert('Balance transfer failed. Verify you have enough money in your account, and that you are transferring to a valid account.')
      }
      this.resetForm();
    },
    cancelTransfer() {
      this.resetForm();
      alert("Transfer canceled.");
    },
    resetForm() {
      this.fromAccount = "";
      this.toAccount = "";
      this.friendName = "";
      this.friendEmail = "";
      this.friendAccount = "";
      this.transferAmount = null;
    },
  },
};
</script>

<style scoped>
.transfer-container {
  max-width: 500px;
  margin: 2rem auto;
  padding: 1.5rem;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  color: #0056b3;
  margin-bottom: 1rem;
}

.tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 1.5rem;
}

.tab-button {
  padding: 0.7rem 1.5rem;
  border: none;
  background-color: #ccc;
  color: black;
  font-size: 1rem;
  cursor: pointer;
  margin: 0 0.5rem;
  border-radius: 4px;
}

.tab-button.active {
  background-color: #0056b3;
  color: white;
}

.form-group {
  margin-bottom: 1rem;
  text-align: left;
}

label {
  font-weight: bold;
  margin-bottom: 0.5rem;
  display: block;
}

.input-field,
.account-select,
.amount-input {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 1.5rem;
}

.confirm-button {
  background-color: #0056b3;
  color: white;
  border: none;
  padding: 0.7rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
}

.confirm-button:hover {
  background-color: #004094;
}

.cancel-button {
  background-color: #ccc;
  color: black;
  border: none;
  padding: 0.7rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button:hover {
  background-color: #aaa;
}
</style>
