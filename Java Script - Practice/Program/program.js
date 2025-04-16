document.addEventListener("DOMContentLoaded" , function(){
    
    // 1. Multiplication Table
    let num = 5;
    let table = "";
    for(let i = 1 ; i<=10 ; i++){
        table += num + " x " + i + " = " + (num*i) + "<br>";
    }
    document.getElementById("a1").innerHTML = table;

    // 2. Factorial
    function fact(n){
        if(n==0 || n==1) return 1;
        let res = 1;
        for(let i = 2 ; i<=n ; i++){
            res *= i;
        }
        return res;
    }
    let factorial = fact(5);
    document.getElementById("a2").innerHTML = "Factorial of 5 is " + factorial;

    // 3. Fibonacci series
    function fibo(n){
        let fiboArray = [0 , 1];
        for(let i = 2 ; i< n ; i++){
            fiboArray.push(fiboArray[i-1] + fiboArray[i-2]);
        }
        return fiboArray.join(", ")
    }
    let fibonacci = fibo(10);
    document.getElementById("a3").innerHTML = "Fibonacci Series: " + fibonacci;

    
    // 4. Check Prime Number
    function isPrime(num) {
        if (num <= 1) return false;
        for (let i = 2; i < num; i++) {
            if (num % i === 0) return false;
        }
        return true;
    }
    let prime = isPrime(7) ? "7 is a prime number." : "7 is not a prime number."; 
    document.getElementById("a4").innerHTML = prime;

     // 5. Sum of Digits
     function sumOfDigits(num) {
        let sum = 0;
        while (num) {
            sum += num % 10;
            num = Math.floor(num / 10);
        }
        return sum;
    }
    let sum = sumOfDigits(123);  
    document.getElementById("a5").innerHTML = "Sum of digits of 123 is " + sum;

    // 6. Reverse a String
    let str = "hello";
    let revStr = str.split("").reverse().join("");
    document.getElementById("a6").innerHTML = revStr;

    // 7. Palindrom Checker
    let PalinStr = "madam";
    function isPalin(str){
        resStr = str.split("").reverse().join("");
        if(str == resStr) return true;
        return false;
    }
    let palindrome = isPalin(PalinStr) ? PalinStr + " is a Palindrom" : PalinStr + " is not a Palindrom"; 
    document.getElementById("a7").innerHTML = palindrome;

    // 8. Find Largest Number in Array
    let numArray = [5,10,15,20,25,30,35,40,45,50];
    let max = numArray[0];
    numArray.forEach((i) => {
        if(i > max) max = i;
    })
    document.getElementById("a8").innerHTML = max;

    // 9. Count Vowels in a String
    function countVowels(str) {
        let c = 0;
        let vowels = "aeiouAEIOU";
        for (let i = 0; i < str.length; i++) {
            let ch = str.charAt(i);
            for (let j = 0; j < vowels.length; j++) {
                if (ch === vowels.charAt(j)) {
                    c++;
                    break;
                }
            }
        }
        return c;
    }
    let vowelCount = countVowels(str);
    document.getElementById("a9").innerHTML = `Number of vowels in "${str}" is ${vowelCount}`;

    // 10. Remove Duplicates from Array
    function removeDuplicates(arr) {
        let unique = [];
        for (let i = 0; i < arr.length; i++) {
            if (!unique.includes(arr[i])) {
                unique.push(arr[i]);
            }
        }
        return unique;
    }
    let arr = [1, 2, 3, 2, 4, 1, 5];
    let uniqueArr = removeDuplicates(arr);
    document.getElementById("a10").innerHTML =
        `Original : [${arr}]<br>Without Duplicates : [${uniqueArr}]`;
})

