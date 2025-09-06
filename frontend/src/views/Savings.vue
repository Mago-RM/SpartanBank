<template>
  <div class="open-savings-container">
    <h1>Open a Savings Account</h1>

    <!-- Deposit Amount Input -->
    <div class="form-group">
      <label for="deposit">Deposit Amount ($):</label>
      <input
        type="number"
        id="deposit"
        v-model="depositAmount"
        placeholder="Enter initial deposit"
        class="input-field"
        min="1"
      />
    </div>

    <!-- Action Buttons -->
    <div class="button-group">
      <button @click="cancelApplication" class="cancel-button">Cancel</button>
      <button @click="showConfirmation" class="confirm-button">Confirm</button>
    </div>

    <!-- Confirmation Modal -->
    <div v-if="isConfirming" class="modal">
      <div class="modal-content">
        <p>Are you sure you want to deposit ${{ depositAmount }} into your new savings account?</p>
        <div class="modal-buttons">
          <button @click="submitApplication" class="confirm-button">Yes</button>
          <button @click="cancelConfirmation" class="cancel-button">No</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { openSavings } from '@/api';
import { useStore } from 'vuex';

export default {
  name: "Savings",
  data() {
    return {
      depositAmount: null, // Amount to deposit
      isConfirming: false, // Tracks if confirmation modal is visible
    };
  },
  setup() {
    const store = useStore();
    return { store };
  },
  methods: {
    showConfirmation() {
      if (!this.depositAmount || this.depositAmount <= 0) {
        alert("Please enter a valid deposit amount.");
        return;
      }
      this.isConfirming = true; // Show the confirmation modal
    },
    cancelConfirmation() {
      this.isConfirming = false; // Close the confirmation modal
    },
    async submitApplication() {
      if (!this.depositAmount || this.depositAmount <= 0) {
        alert("Please enter a valid deposit amount.");
        return;
      }

      try {
        const response = await openSavings(this.store.getters.currentUser, this.depositAmount);

        this.store.dispatch("updateAccounts");

        alert(
          `Savings account created with an initial deposit of $${this.depositAmount}.`
        );

        // Reset data and navigate back to Home
        this.depositAmount = null;
        this.isConfirming = false;
        this.$router.push("/home"); // Redirect to the Home page
      } catch (e) {
        console.log(e)
        alert('Failed to make savings account. Ensure you have enough money in your checking account.')
      }
    },

    cancelApplication() {
      // Reset data and navigate back to Home
      this.depositAmount = null;
      this.isConfirming = false;
      this.$router.push("/home");
    },
  },
};
</script>

<style scoped>
.open-savings-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 1.5rem;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  color: #0056b3;
  margin-bottom: 1.5rem;
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

.input-field {
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

/* Modal Styling */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
  text-align: center;
  max-width: 300px;
  width: 100%;
}

.modal-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 1rem;
}

.modal-buttons .confirm-button {
  background-color: #0056b3;
}

.modal-buttons .cancel-button {
  background-color: #ccc;
}
</style>
