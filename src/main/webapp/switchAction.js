/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Display Existing User or New User Views - DEPRECATED
 function displayExistingUser() {
     document.getElementById("existingUser").style.display = "block";
     document.getElementById("newUser").style.display = "none";
 }

 function displayNewUser() {
   document.getElementById("existingUser").style.display = "none";
   document.getElementById("newUser").style.display = "block";
 }

// Display Selected Action
function displayTransfer() {
    document.getElementById("transfer-form").style.display = "block";
    document.getElementById("transaction-history").style.display = "none";
    document.getElementById("new-account-form").style.display = "none";
}

function displayTransactionHistory() {
  document.getElementById("transfer-form").style.display = "none";
  document.getElementById("transaction-history").style.display = "block";
  document.getElementById("new-account-form").style.display = "none";
}

// Deprecated as split into 2 files
function displayNewAccount() {
  document.getElementById("transfer-form").style.display = "none";
  document.getElementById("transaction-history").style.display = "none";
  document.getElementById("new-account-form").style.display = "block";
}

function displayNewAccount2() {
  document.getElementById("new-account-form-2").style.display = "block";
}

// Display or hide recipient field based on transaction type
function displayRecipient() {
  document.getElementById("recipient-field").style.display = "block";
}

function hideRecipient() {
  document.getElementById("recipient-field").style.display = "none";
}

// Go from New User profile to Existing User Profile
function goToExistingProfile() {
  window.console.log(window.location.href);
  window.location.href = 'existing_profile.html';
  window.console.log(window.location.href);
}
