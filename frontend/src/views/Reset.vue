<template>
  <div class="reset-password-container">
    <h1>Reset Your Password</h1>
    <form @submit.prevent="handleResetPassword">
      <!-- Email Input -->
      <div class="form-group">
        <label for="email">Email Address</label>
        <input
          type="email"
          id="email"
          v-model="email"
          placeholder="Enter your registered email"
          required
        />
        <label for="current_password">Current Password</label>
        <input type="password" id="current_password" v-model="current_password" placeholder="Enter your current password" required/>
        <label for="new_password">New Password</label>
        <input type="password" id="new_password" v-model="new_password" placeholder="Enter your new password" required/>
        <label for="confirm_password">Confirm Password</label>
        <input type="password" id="confirm_password" v-model="confirm_password" placeholder="Confirm the new password" required/>
      </div>

      <!-- Submit Button -->
      <button type="submit" class="reset-button">Reset Password</button>

      <!-- Cancel Button -->
      <button type="button" @click="cancelReset" class="cancel-button">
        Cancel
      </button>
    </form>

    <!-- Message Section -->
    <div v-if="message" class="message-box">
      {{ message }}
    </div>
  </div>
</template>

  
<script>
  import { changePassword } from '@/api';

  export default {
    name: "ResetPassword",
    data() {
      return {
        email: "",
        message: "", // Holds the message to display to the user
      };
    },
    methods: {
      async handleResetPassword() {
        if (!this.email) {
          this.message = "Please enter your email address.";
          return;
        }

        // Validate the email format using a regex
        if (!/\S+@\S+\.\S+/.test(this.email)) {
          this.message = "Please enter a valid email address.";
          return;
        }

        if (this.new_password != this.confirm_password) {
          this.message = "New password and confirmation do not match.";
          return;
        }

        async function encodePassword(password) {
          const msgBuffer = new TextEncoder().encode(password);                    
          const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
          const hashArray = Array.from(new Uint8Array(hashBuffer));
          return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
        }

        console.log(await encodePassword(this.current_password));

        const response = await changePassword(this.email, await encodePassword(this.current_password), await encodePassword(this.new_password));
        
        console.log(response)

        // Display the success message and redirect to login
        alert('Successfully reset password, please log back in...')
        this.$router.push("/");
      },
      cancelReset() {
        this.email = ""; // Clear the email input
        this.current_password = "";
        this.new_password = "";
        this.confirm_password = "";
        this.$router.push("/Home"); // Navigate back to the home
      },
    },
  };
  </script>
  
  <style scoped>
  .reset-password-page {
    margin: 0;
    font-family: Arial, sans-serif;
    background-color: #0056b3; 
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .reset-password-container {
    max-width: 400px;
    margin: 2rem auto;
    padding: 1.5rem;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    text-align: left;
  }
  
  h1 {
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
    box-sizing: border-box;
  }
  
  .reset-button {
    display: block;
    width: 100%;
    background-color: #0056b3;
    color: white;
    border: none;
    padding: 0.7rem 1.5rem;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
  }
  
  .reset-button:hover {
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
    margin-top: 1rem;
  }
  
  .cancel-button:hover {
    background-color: #aaa;
  }
  </style>
  