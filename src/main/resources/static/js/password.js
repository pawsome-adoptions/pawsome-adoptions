var passwordInput = document.getElementById("password");
var eightCharacter = document.getElementById("eight-character");
var uppercase = document.getElementById("uppercase");
var lowercase = document.getElementById("lowercase");
var number = document.getElementById("number");
var usernameSame = document.getElementById("username-same");

passwordInput.addEventListener("input", function() {
    let passwordValue = passwordInput.value;
    let containsUppercase = /[A-Z]/.test(passwordValue);
    let containsLowercase = /[a-z]/.test(passwordValue);
    let containsNumber = /\d/.test(passwordValue);
    let hasMinimumLength = passwordValue.length >= 8;

    if (containsUppercase) {
        uppercase.classList.add("green");
    } else {
        uppercase.classList.remove("green");
    }

    if (containsNumber) {
        number.classList.add("green");
    } else {
        number.classList.remove("green");
    }

    if (hasMinimumLength) {
        eightCharacter.classList.add("green");
    } else {
        eightCharacter.classList.remove("green");
    }

    if (containsLowercase) {
        lowercase.classList.add("green");
    } else {
        lowercase.classList.remove("green");
    }
});

//hide buttons when user is in visitor post
let currentURL = window.location.href;
console.log(currentURL);
if (currentURL.indexOf("/visitorpost") !== -1) {
    console.log(currentURL);
    document.getElementById("navbar-buttons").style.display = "none";
}

