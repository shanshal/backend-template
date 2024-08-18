(ns com.iraqidata.tasks.day-one)


;; 1. Write a function that takes one argument as input and prints that
;; argument.






;; 2. Write a function that adds `1` to a number only if the input is odd,
;; otherwise return `:error`.

;; Tip, use the `odd?` function to check if a number is odd.







;; 3. Write a function that takes 3 arguments, `name`, `year-of-birth`, and
;; `current-year`. and returns a map with the following keys: `name`, `age`.

;; Example run
;; (function-name "Ali" 2001 2024) => {:name "Ali", :age 23}





;; 4. Write a function that takes the output of the above function and returns
;; `true` if the person is allowed to vote (assume the voting age is 18).

;; Example run
;; (function-name {:name "Ali", :age 23}) => true
;; (function-name "Ali" 2001 2024) => true
;; (function-name {:name "Abbas", :age 17}) => false




;; OPTIONAL FOR BONUS POINTS

;; 5. Modify the function from number 3 to not need the `current-year`.
;; Example run
;; (function-name "Ali" 2001) => {:name "Ali", :age 23}
;; If ran in 2025
;; (function-name "Ali" 2001) => {:name "Ali", :age 24}
