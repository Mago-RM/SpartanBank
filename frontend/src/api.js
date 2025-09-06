const apiBaseUrl = "http://localhost:8080/BankingApp_war_exploded/api"; // Reads from .env
console.log("API Base URL:", apiBaseUrl);


export const createClient = async (clientData) => {
  const requestOptions = {
    method: 'PUT',
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clientData)
  }

  const response = await fetch(`${apiBaseUrl}/customers`, requestOptions);

  return response.json();
};

export const login = async (credentials) => {
  const requestOptions = {
    method: 'POST',
    headers: {
      'Authorization': credentials
    }
  }

  const response = await fetch(`${apiBaseUrl}/login`, requestOptions);

  return response.json();
};

export const listAccounts = async (credentials) => {
  const requestOptions = {
    method: 'GET',
    headers: {
      'Authorization': credentials
    }
  }

  const response = await fetch(`${apiBaseUrl}/accounts`, requestOptions);

  return response.json();
};

export const openSavings = async (credentials, amount) => {
  const requestOptions = {
    method: 'POST',
    headers: {
      'Authorization': credentials,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({"balance": parseInt(amount)})
  }

  const response = await fetch(`${apiBaseUrl}/accounts`, requestOptions);

  return response;
}

export const fetchTransactions = async (credentials) => {
  const requestOptions = {
    method: 'GET',
    headers: {
      'Authorization': credentials
    }
  }

  const response = await fetch(`${apiBaseUrl}/transactions`, requestOptions)

  return response.json();
}

export const deposit = async (credentials, account, value) => {
  const requestOptions = {
    method: 'PUT',
    headers: {
      'Authorization': credentials,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({'accountID': account, 'amount': parseFloat(value)})
  }

  const response = await fetch(`${apiBaseUrl}/transactions/deposit`, requestOptions)
}

export const balanceTransfer = async(credentials, from, to, amount) => {
  const requestOptions = {
    method: 'POST',
    headers: {
      'Authorization': credentials,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({'from': parseInt(from), 'to': parseInt(to), 'amount': parseFloat(amount)})
  }

  const response = await fetch(`${apiBaseUrl}/transactions/transfer`, requestOptions)

  return response
}

export const getCustomerInfo = async(credentials) => {
  const requestOptions = {
    method: 'GET',
    headers: {
      'Authorization': credentials
    }
  }

  const response = await fetch(`${apiBaseUrl}/customers`, requestOptions)

  return response.json()
}

export const updateCustomerInfo = async(credentials, name, email) => {
  const requestOptions = {
    method: 'POST',
    headers: {
      'Authorization': credentials,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({"name": name, "email": email})
  }

  const response = await fetch(`${apiBaseUrl}/customers`, requestOptions)

  return response.json()
}

export const changePassword = async(email, oldPassword, newPassword) => {
  const requestOptions = {
    method: 'POST',
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({"email": email, "oldPassword": oldPassword, "newPassword": newPassword})
  }

  const response = await fetch(`${apiBaseUrl}/customers/reset_password`, requestOptions);

  return response.json();
}

export const deleteAccount = async(credentials) => {
  const requestOptions = {
    method: 'DELETE',
    headers: {
      'Authorization': credentials
    }
  }

  const response = await fetch(`${apiBaseUrl}/customers/delete`, requestOptions);
}