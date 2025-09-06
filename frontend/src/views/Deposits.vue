<template>
    <div class="deposit-container">
      <h1>Mobile Deposit</h1>
  
      <!-- Account Selection Menu -->
      <div class="form-group">
        <label for="account">Choose Account:</label>
        <select id="account" v-model="selectedAccount" class="account-select">
          <option value="" disabled>Select an account</option>
          <option v-bind:value="this.store.getters.checkingAccount.accountID">Checking Account</option>
          <option v-if="this.store.getters.savingsAccount.accountID != null" v-bind:value="this.store.getters.savingsAccount.accountID">Savings Account</option>
        </select>
      </div>
  
      <!-- Deposit Amount Input -->
      <div class="form-group">
        <label for="amount">Deposit Amount:</label>
        <input
          type="number"
          id="amount"
          v-model="depositAmount"
          placeholder="Enter deposit amount"
          class="amount-input"
          min="1"
        />
      </div>
  
      <!-- Action Buttons -->
      <div class="button-group">
        <button @click="cancelDeposit" class="cancel-button">Cancel</button>
        <button @click="confirmDeposit" class="confirm-button">Confirm</button>
      </div>
    </div>
  </template>
  
<script>
  import { useStore } from 'vuex';
  import { deposit } from '@/api';

  export default {
    name: "Deposits",
    data() {
      return {
        selectedAccount: "", // Selected account (checking or savings)
        depositAmount: null, // Amount to deposit
      };
    },
    setup() {
      const store = useStore();
      
      return { store };
    },
      methods: {
      async confirmDeposit() {
        if (!this.selectedAccount || !this.depositAmount) {
          alert("Please select an account and enter a valid amount.");
          return;
        }

        // Simulate deposit logic (for now, just log the data)
        console.log(
          `Depositing $${this.depositAmount} to account.`
        );

        try {
          await deposit(this.store.getters.currentUser, this.selectedAccount, this.depositAmount);

          alert(
            `Deposit of $${this.depositAmount} to your account was successful!`
          );

          // Reset fields after deposit
          this.selectedAccount = "";
          this.depositAmount = null;
        } catch (e) {
          alert('Deposit failed.')
          console.log(e)
        }
      },
      cancelDeposit() {
        // Reset the form
        this.selectedAccount = "";
        this.depositAmount = null;

        alert("Deposit canceled.");
      },
    },
  };
</script>
  
  <style scoped>
  .deposit-container {
    max-width: 400px;
    margin: 2rem auto;
    padding: 1.5rem;
    background-color: rgba(255, 255, 255, 0.9); /* Semi-transparent white */
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  h1 {
    color: #0056b3; /* Blue heading */
    margin-bottom: 1.5rem;
  }
  
  .form-group {
    margin-bottom: 1rem;
    text-align: left;
  }
  
  label {
    font-size: 1rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
    display: block;
    color: #333;
  }
  
  .account-select,
  .amount-input {
    width: 100%;
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
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
  