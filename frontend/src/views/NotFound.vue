<template>
    <div class="not-found">
      <h1>404 - Page Not Found</h1>
      <p>Sorry, the page you're looking for doesn't exist.</p>
      <router-link to="/">Return to Home</router-link>
    </div>
  </template>
  
  <script>
    export default {
    name: "NotFoundView",
    created() {
        console.log("NotFoundView loaded");
    }
    };
  </script>
  
  <style scoped>
  /* Parent container for full-screen centering */
  .not-found {
    display: flex;
    flex-direction: column; /* Stack items vertically */
    justify-content: center; /* Center items vertically */
    align-items: center; /* Center items horizontally */
    height: 100vh; /* Take up full viewport height */
    background: linear-gradient(135deg, #007bff, #0056b3); /* Background theme */
    color: white;
    text-align: center;
    padding: 20px;
    margin: 0;
    box-sizing: border-box; /* Ensure padding does not overflow */
  }
  
  /* Content styling for inner elements */
  .content {
    max-width: 600px;
  }
  
  h1 {
    font-size: 3rem;
    font-weight: bold;
    margin-bottom: 20px;
  }
  
  p {
    font-size: 1.2rem;
    margin-bottom: 30px;
  }
  
  .btn-home {
    padding: 10px 20px;
    font-size: 1rem;
    font-weight: bold;
    color: #007bff;
    background-color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .btn-home:hover {
    background-color: #e0e0e0;
  }
  </style>