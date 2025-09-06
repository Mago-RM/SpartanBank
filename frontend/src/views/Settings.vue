<template>
  <div class="settings-container">
    <h1>Account Settings</h1>

    <!-- Personal Information -->
    <section class="settings-section" v-if="this.store.getters.customer != null">
      <h2>Personal Information</h2>
      <div class="form-group">
        <label for="name">Full Name</label>
        <input type="text" id="name" v-model="this.store.getters.customer.name" placeholder="Enter your name" />
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="this.store.getters.customer.email" placeholder="Enter your email" />
      </div>
      <button @click="updatePersonalInfo" class="save-button">Save Changes</button>
    </section>

    <!-- Security Settings -->
    <section class="settings-section">
      <h2>Security Settings</h2>
      <button class="security-button" @click="changePassword">Change Password</button>
      <button class="security-button" @click="deleteAccount">Delete Account</button>
    </section>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { deleteAccount, updateCustomerInfo } from '@/api';

export default {
  name: "Settings",
  setup() {
    const store = useStore();
    
    store.dispatch("updateCustomer");

    return { store };
  },
  methods: {
    async updatePersonalInfo() {
      const response = await updateCustomerInfo(this.store.getters.currentUser, this.store.getters.customer.name, this.store.getters.customer.email);
      alert("Personal information updated!");
    },
    changePassword() {
      this.$router.push("/reset");
    },
    async deleteAccount() {
      const response = await deleteAccount(this.store.getters.currentUser);
      alert("Account deleted");
      this.$router.push("/");
    }
  },
};
</script>

<style scoped>
.settings-container {
  max-width: 600px;
  margin: 2rem auto;
  padding: 1.5rem;
  background-color: rgba(255, 255, 255, 0.9); /* Semi-transparent white */
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  color: #0056b3;
  margin-bottom: 1.5rem;
}

.settings-section {
  margin-bottom: 2rem;
}

.settings-section h2 {
  color: #333;
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

input {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 0.7rem 1.5rem;
  font-size: 1rem;
  color: white;
  background-color: #0056b3;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #004094;
}
</style>
