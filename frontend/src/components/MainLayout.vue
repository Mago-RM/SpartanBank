<template>
    <div class="layout">
      <!-- Top Menu -->
      <div class="top-menu">
        <ul class="menu-list">
          <li><router-link to="/Home">Home</router-link></li>
          <li><router-link to="/Deposits">Make a Deposit</router-link></li>
          <li><router-link to="/Transfer">Transfer Funds</router-link></li>
          <li><router-link to="/Transactions">View Transactions</router-link></li>
          <li><router-link to="/Savings">Savings</router-link></li>
          <li><router-link to="/Loans">Loans</router-link></li>
          <li><router-link to="/Settings">Account Settings</router-link></li>
          <li><router-link to="/">Log Out</router-link></li>
        </ul>
      </div>
  
      <!-- Router View for Child Content -->
      <div class="content">
        <router-view></router-view> <!-- This renders the child view content -->
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "MainLayout",
  };
  </script>
  
  <style scoped>
  /* Styles for MainLayout */
  .layout {
    display: flex;
    flex-direction: column;
    height: 100vh;
    background-image: url('../assets/campus.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
  }
  
  .top-menu {
    width: 100%;
    background-color: rgba(0, 86, 179, 0.9);
    padding: 1rem 0;
  }
  
  .menu-list {
    display: flex;
    justify-content: center;
    gap: 2rem;
    list-style: none;
  }
  
  .menu-list a {
    color: white;
    text-decoration: none;
    font-weight: bold;
  }
  
  .content {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  </style>
  