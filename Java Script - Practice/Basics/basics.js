// Wait for the DOM to be fully loaded before running the code
document.addEventListener('DOMContentLoaded', function() {

    // 1. Write a program to print Hello, World! in the console.
    document.getElementById("a1").innerHTML = "Hello, World!";

    // 2. Declare a variable to store your name and print it.
    let name = "Reshmika";
    document.getElementById("a2").innerHTML = name;

    // 3. Declare two numbers and print their sum, difference, product, and quotient.
    let num1 = 5;
    let num2 = 10;
    let operator = prompt("Enter Operator (+,-,*,/)");
    let result3 = "";
    
    if (operator === "+") result3 = "Sum is " + (num1 + num2);
    else if (operator === "-") result3 = "Difference is " + (num2 - num1);
    else if (operator === "*") result3 = "Product is " + (num1 * num2);
    else if (operator === "/") result3 = "Division is " + (num2 / num1);
    else result3 = "Invalid operator";
    
    document.getElementById("a3").innerHTML = result3;

    // 4. Take a number and check whether it is odd or even.
    let num = prompt("Enter a number:");
    let result4 = (num % 2 === 0) ? num + " is Even" : num + " is Odd";
    document.getElementById("a4").innerHTML = result4;

    // 5. Take three numbers and find the largest among them.
    let num1_3 = prompt("Enter first number:");
    let num2_3 = prompt("Enter second number:");
    let num3 = prompt("Enter third number:");
    
    let largestNum = "";
    if (num1_3 >= num2_3 && num1_3 >= num3) {
        largestNum = num1_3 + " is the largest number.";
    } else if (num2_3 >= num1_3 && num2_3 >= num3) {
        largestNum = num2_3 + " is the largest number.";
    } else {
        largestNum = num3 + " is the largest number.";
    }
    
    document.getElementById("a5").innerHTML = largestNum;

});
