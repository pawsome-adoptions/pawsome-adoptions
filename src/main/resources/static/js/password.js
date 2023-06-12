var passwordInput = document.getElementById("password");
var eightCharacter = document.getElementById("eight-character");
var uppercase = document.getElementById("uppercase");
var lowercase = document.getElementById("lowercase");
var number = document.getElementById("number");

passwordInput.addEventListener("input", function() {
    var passwordValue = passwordInput.value;
    var containsUppercase = /[A-Z]/.test(passwordValue);
    var containsLowercase = /[a-z]/.test(passwordValue);
    var containsNumber = /\d/.test(passwordValue);
    var hasMinimumLength = passwordValue.length >= 8;

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
var currentURL = window.location.href;
console.log(currentURL);
if (currentURL.indexOf("/visitorpost") !== -1) {
    console.log(currentURL);
    document.getElementById("navbar-buttons").style.display = "none";
}
