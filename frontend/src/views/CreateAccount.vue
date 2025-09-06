<template>
    <div class="create-account-page">
      <!-- Page Title -->
      <div class="page-title">
      </div>
  
      <!-- Account Creation Form -->
      <div class="create-account-container">
        <h1>Create Your Account</h1>
        <form @submit.prevent="handleCreateAccount">
          <!-- Full Name -->
          <div class="form-group">
            <label for="name">Full Name</label>
            <input
              type="text"
              id="name"
              v-model="name"
              placeholder="Enter your full name"
              required
            />
          </div>
  
          <!-- Address -->
          <div class="form-group">
            <label for="address">Address</label>
            <input
              type="text"
              id="address"
              v-model="address"
              placeholder="Enter your address"
              required
            />
          </div>
  
          <!-- City -->
          <div class="form-group">
            <label for="city">City</label>
            <input
              type="text"
              id="city"
              v-model="city"
              placeholder="Enter your city"
              required
            />
          </div>
  
          <!-- State -->
          <div class="form-group">
            <label for="state">State</label>
            <input
              type="text"
              id="state"
              v-model="state"
              placeholder="Enter your state"
              required
            />
          </div>
  
          <!-- Country -->
          <div class="form-group">
            <label for="country">Country</label>
            <input
              type="text"
              id="country"
              v-model="country"
              placeholder="Enter your country"
              required
            />
          </div>
  
          <!-- Zip Code -->
          <div class="form-group">
            <label for="zip">Zip Code</label>
            <input
              type="text"
              id="zip"
              v-model="zip"
              placeholder="Enter your zip code"
              pattern="[0-9]{5}"
              title="Enter a valid 5-digit zip code"
              required
            />
          </div>
  
          <!-- Email -->
          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="email"
              id="email"
              v-model="email"
              placeholder="Enter your email"
              required
            />
          </div>
  
          <!-- Phone Number -->
          <div class="form-group">
            <label for="phone">Phone Number</label>
            <input
              type="tel"
              id="phone"
              v-model="phone"
              placeholder="Enter your phone number"
              required
            />
          </div>
  
          <!-- Date of Birth -->
          <div class="form-group">
            <label for="dob">Date of Birth</label>
            <input
              type="date"
              id="dob"
              v-model="dob"
              placeholder="Enter your date of birth"
              required
            />
          </div>

  
          <!-- Password -->
          <div class="form-group">
            <label for="password">Password</label>
            <input
              type="password"
              id="password"
              v-model="password"
              placeholder="Enter your password"
              required
            />
          </div>
  
          <!-- Submit Button -->
          <button type="submit" class="create-button">Create Account</button>
          <!-- Cancel Button -->
          <button type="button" @click="cancelCreateAccount" class="cancel-button">Cancel</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
import { useStore } from "vuex";
import { createClient } from '../api.js';
export default {
  name: "CreateAccount",
  data() {
    return {
      name: "",
      dob: "", // Add DOB
      email: "",
      address: "",
      phone: "",
      password: "",
    };
  },
  setup() {
    const store = useStore();
    return { store };
  },
  methods: {
    async handleCreateAccount() {
      if (!this.name || !this.email || !this.address || !this.dob || !this.phone || !this.password) {
        alert("Please fill out all fields.");
        return;
      }

      // encode as UTF-8
      const msgBuffer = new TextEncoder().encode(this.password);                    

      // hash the message
      const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);

      // convert ArrayBuffer to Array
      const hashArray = Array.from(new Uint8Array(hashBuffer));

      // convert bytes to hex string                  
      const hashed_password = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

      try {
        const clientData = {
          name: this.name,
          dob: this.dob, // Make sure `dob` is in `YYYY-MM-DD` format
          address: `${this.address}, ${this.city}, ${this.state}, ${this.country} ${this.zip}`,
          phone: parseInt(this.phone),
          email: this.email,
          password: hashed_password,
        };

        const response = await createClient(clientData);
        console.log("Client Created:", response);

        alert("Account created successfully!");

        const user = response;
        this.store.dispatch("login", user); // Store user in Vuex
        this.store.dispatch("updateAccounts")
        this.$router.push("/home"); // Navigate to home page
      } catch (error) {
        console.error("Error creating account:", error);
        alert("Failed to create account. Please try again.");
      }
    },
    cancelCreateAccount() {
      // Navigate back to the login page
      this.$router.push("/");
    },

    resetForm() {
      this.name = "";
      this.dob = ""; // Reset DOB
      this.email = "";
      this.address = "";
      this.city = "";
      this.state = "";
      this.country = "";
      this.zip = "";
      this.phone = "";
      this.password = "";
    },
  },
};
</script>

  
  
  <style scoped>
  .create-account-page {
    margin: 0;
    font-family: Arial, sans-serif;
    background-color: #0056b3; /* Blue background */
    min-height: 100vh;
    padding: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .page-title {
    margin-bottom: 1rem;
  }
  
  .page-title h1 {
    color: white;
    text-align: center;
    font-size: 1.8rem;
  }
  
  .create-account-container {
    max-width: 400px;
    padding: 1.5rem;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    text-align: left;
  }
  
  .create-account-container h1 {
    color: #0056b3;
    text-align: center;
    margin-bottom: 1.5rem;
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  label {
    display: block;
    font-size: 1rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
    color: #333;
  }
  
  input {
    width: 100%;
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .create-button {
    width: 100%;
    background-color: #0056b3;
    color: white;
    border: none;
    padding: 0.7rem 1.5rem;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    margin-top: 1rem;
  }
  
  .create-button:hover {
    background-color: #004094;
  }

  .cancel-button {
    display: block;
    width: 100%;
    background-color: #ccc;
    color: black;
    border: none;
    padding: 0.7rem 1.5rem;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    margin-top: 1rem; /* Add some space between the buttons */
 }

    .cancel-button:hover {
    background-color: #aaa;
    }

    input[type="date"] {
      font-family: Arial, sans-serif;
      font-size: 1rem;
      padding: 0.5rem;
      border: 1px solid #ccc;
      border-radius: 4px;
      width: 100%;
    }

</style>
  