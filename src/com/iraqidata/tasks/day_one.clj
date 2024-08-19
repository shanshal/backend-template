(ns com.iraqidata.tasks.day-one)


;; 1. Write a function that takes one argument as input and prints that
;; argument.
(defn customPrint [text]
  (println text))

;; Correct, one full point.

;; for usage (customPrint [your text here]


;; 2. Write a function that adds `1` to a number only if the input is odd,
;; otherwise return `:error`.
;; (defn makeEven [number])
;; (
;;  if (odd? number)
;;  number + 1)

(defn makeEven [number]
  (if (odd? number)
    (+ number 1)
    number))

;; This should return `:error`.
;; Half point.

;;usage (makeEven 3)

;; 3. Write a function that takes 3 arguments, `name`, `year-of-birth`, and
;; `current-year`. and returns a map with the following keys: `name`, `age`.

(defn find-age [{:keys [name year-of-birth current-year]}]
  {:name name
   :age (- current-year year-of-birth)})

(find-age {:name "Ali" :year-of-birth 2001 :current-year 2024})
;; => {:name "Ali", :age 23}

;; Correct, one full point.

;; Example run
;; (function-name "Ali" 2001 2024) => {:name "Ali", :age 23}


;; 4. Write a function that takes the output of the above function and returns
;; `true` if the person is allowed to vote (assume the voting age is 18).

(defn is-adult [find-age]
  (>= (:age find-age) 18))

(is-adult {:name "Ali", :age 17})

;; Correct, one full point.

;; Example run
;; (function-name {:name "Ali", :age 23}) => true
;; (function-name "Ali" 2001 2024) => true
;; (function-name {:name "Abbas", :age 17}) => false

;; OPTIONAL FOR BONUS POINTS

;; 5. Modify the function from number 3 to not need the `current-year`.

(import '[java.time LocalDate])

(defn sentiant-find-age [{:keys [name year-of-birth]}]
  (let [current-year (.getYear (LocalDate/now))]
    {:name name
     :age (- current-year year-of-birth)}))

(sentiant-find-age {:name "Ali" :year-of-birth 2001})

;; Correct, 3 full points.

;; Example run
;; (function-name "Ali" 2001) => {:name "Ali", :age 23}
;; If ran in 2025
;; (function-name "Ali" 2001) => {:name "Ali", :age 24}


;; Total points: (+ 1 0.5 1 1 3) => 6.5
