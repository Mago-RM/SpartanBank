<template>
    <div class="loan-container">
      <h1>Apply For a Loan</h1>
  
      <!-- Loan Application Form -->
      <div class="form-group">
        <label for="loanAmount">Loan Amount ($):</label>
        <input
          type="number"
          id="loanAmount"
          v-model="loanAmount"
          placeholder="Enter loan amount"
          class="input-field"
          min="1"
        />
      </div>
  
      <div class="form-group">
        <label for="loanPurpose">Loan Purpose:</label>
        <select id="loanPurpose" v-model="loanPurpose" class="input-field">
          <option value="" disabled>Select a purpose</option>
          <option value="home">Home</option>
          <option value="auto">Auto</option>
          <option value="education">Education</option>
          <option value="business">Business</option>
          <option value="personal">Personal</option>
        </select>
      </div>
  
      <div class="form-group">
        <label for="repaymentPeriod">Repayment Period (Months):</label>
        <input
          type="number"
          id="repaymentPeriod"
          v-model="repaymentPeriod"
          placeholder="Enter repayment period"
          class="input-field"
          min="1"
        />
      </div>
  
      <div class="form-group">
        <label for="creditAccount">Credit to Account:</label>
        <select id="creditAccount" v-model="creditAccount" class="input-field">
          <option value="" disabled>Select an account</option>
          <option value="checking">Checking Account</option>
          <option value="savings">Savings Account</option>
        </select>
      </div>
  
      <!-- Action Buttons -->
      <div class="button-group">
        <button @click="cancelLoan" class="cancel-button">Cancel</button>
        <button @click="applyForLoan" class="apply-button">Apply</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "Loans",
    data() {
      return {
        loanAmount: null, // Loan amount input
        loanPurpose: "", // Loan purpose (e.g., home, auto, etc.)
        repaymentPeriod: null, // Repayment period in months
        creditAccount: "", // Account to credit
      };
    },
    methods: {
      applyForLoan() {
        // Validate input fields
        if (
          !this.loanAmount ||
          !this.loanPurpose ||
          !this.repaymentPeriod ||
          !this.creditAccount
        ) {
          alert("Please fill out all fields to apply for a loan.");
          return;
        }
  
        // Simulation. To Replace with actual API call later
        alert(
          `Loan Application Submitted!\n\nDetails:\n- Amount: $${this.loanAmount}\n- Purpose: ${this.loanPurpose}\n- Repayment Period: ${this.repaymentPeriod} months\n- Credit to: ${this.creditAccount}`
        );
  
        // Reset form after submission
        this.resetForm();
      },
      cancelLoan() {
        // Reset the form
        this.resetForm();
        alert("Loan application canceled.");
      },
      resetForm() {
        this.loanAmount = null;
        this.loanPurpose = "";
        this.repaymentPeriod = null;
        this.creditAccount = "";
      },
    },
  };
  </script>
  
  <style scoped>
  .loan-container {
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
  
  .apply-button {
    background-color: #0056b3;
    color: white;
    border: none;
    padding: 0.7rem 1.5rem;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .apply-button:hover {
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
  