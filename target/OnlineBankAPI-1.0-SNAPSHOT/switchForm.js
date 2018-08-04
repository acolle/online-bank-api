function displaySignUpForm() {

    document.getElementById("log-in-form").style.display = "none";
    document.getElementById("sign-up-form").style.display = "block";

}

function displayLogInForm() {

    document.getElementById("log-in-form").style.display = "block";
    document.getElementById("sign-up-form").style.display = "none";

}

function goToProfile() {
    
    window.location.href = "profile.html";
    
}
