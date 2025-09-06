import { listAccounts } from './src/api.js'; 

listAccounts()
  .then(response => {
    console.log("Fetched accounts:", response.data);
  })
  .catch(error => {
    console.error("Error fetching accounts:", error);
  });
