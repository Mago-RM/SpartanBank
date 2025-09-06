<template>
  <div class="login-page">
    <div class="login-container">
      <!-- Bank Logo -->
      <img src="../assets/logo.png" alt="Spartan Bank Logo" class="bank-logo" />

      <!-- Bank Name -->
      <h1>Spartan Bank</h1>

      <!-- Login Form -->
      <form @submit.prevent="handleLogin">
        <!-- Username Field -->
        <div class="form-group">
          <label for="email">Email</label>
            <input
              type="text"
              id="email"
              v-model="email"
              placeholder="Enter your email address"
            />
        </div>

        <!-- Password Field -->
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="Enter your password"
          />
        </div>

        <!-- Login Button -->
        <button type="submit" class="login-button">Login</button>
      </form>

      <!-- Create Account Section -->
      <div class="create-account">
        <p>Don't have an account?</p>
        <button @click="goToCreateAccount" class="create-account-button">Create Account</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { login } from "../api.js";


export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      errorMessage: "",
    };
  },
  setup() {
    const store = useStore();
    return { store };
  },
  methods: {
    async handleLogin() {
      this.errorMessage = ""; // Reset any previous error message
      console.log("Login Data:", { email: this.email, password: this.password });

      // encode as UTF-8
      const msgBuffer = new TextEncoder().encode(this.password);                    

      // hash the message
      const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);

      // convert ArrayBuffer to Array
      const hashArray = Array.from(new Uint8Array(hashBuffer));

      // convert bytes to hex string                  
      const hashed_password = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

      const token = btoa(this.email + ':' + hashed_password)

      try {
        // Use the `login` method from api.js
        const response = await login(token);

        console.log("Response Data:", response);

        // If login is successful, store the user and navigate
        const user = response;
        this.store.dispatch("login", user); // Store user in Vuex
        this.store.dispatch("updateAccounts")
        this.$router.push("/home"); // Navigate to home page
      } catch (error) {
        console.error("Login error:", error);
        this.errorMessage = "Invalid email or password."; // Show error to the user
      }
    },
    goToCreateAccount() {
      this.$router.push("/create-account"); // Navigate to Create Account page
    }
  }
};
</script>

<style scoped>

  .login-page {
    margin: 0;
    font-family: Arial, sans-serif;
    background-color: #0056b3; 
    height: 100vh; 
    display: flex;
    justify-content: center;
    align-items: center;
  }

.login-container {
  background-color: white;
  color: black;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 300px;
}


.bank-logo {
  width: 100px;
  height: auto;
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1rem;
  text-align: left;
}

.form-group label {
  font-size: 0.9rem;
  margin-bottom: 0.2rem;
  display: block;
}

.form-group input {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.login-button {
  background-color: #0056b3;
  color: white;
  padding: 0.7rem;
  font-size: 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
}

.login-button:hover {
  background-color: #004094;
}

/* Create Account Section */
.create-account {
  margin-top: 1.5rem;
}

.create-account p {
  margin: 0;
  font-size: 0.9rem;
  color: #333;
}

.create-account-button {
  margin-top: 0.5rem;
  background-color: #ccc;
  color: black;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.create-account-button:hover {
  background-color: #aaa;
}

.reset-account-button {
  margin-top: 0.5rem;
  background-color: #ccc;
  color: black;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.reset-account-button:hover {
  background-color: #aaa;
}
</style>
