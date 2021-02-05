(require '[clojure.test :refer [is deftest run-tests]])

; (load-file "basic-a-completar.clj")
(load-file "basic.clj")

(deftest test-palabra-reservada?
   (is (= true (palabra-reservada? 'LOAD)))
   (is (= true (palabra-reservada? 'SAVE)))
   (is (= true (palabra-reservada? 'INPUT)))
   (is (= true (palabra-reservada? 'PRINT)))
   (is (= true (palabra-reservada? '?)))
   (is (= true (palabra-reservada? 'DATA)))
   (is (= true (palabra-reservada? 'READ)))
   (is (= true (palabra-reservada? 'REM)))
   (is (= true (palabra-reservada? 'RESTORE)))
   (is (= true (palabra-reservada? 'CLEAR)))
   (is (= true (palabra-reservada? 'LET)))
   (is (= true (palabra-reservada? 'LIST)))
   (is (= true (palabra-reservada? 'NEW)))
   (is (= true (palabra-reservada? 'RUN)))
   (is (= true (palabra-reservada? 'END)))
   (is (= true (palabra-reservada? 'FOR)))
   (is (= true (palabra-reservada? 'TO)))
   (is (= true (palabra-reservada? 'NEXT)))
   (is (= true (palabra-reservada? 'STEP)))
   (is (= true (palabra-reservada? 'GOSUB)))
   (is (= true (palabra-reservada? 'RETURN)))
   (is (= true (palabra-reservada? 'GOTO)))
   (is (= true (palabra-reservada? 'IF)))
   (is (= true (palabra-reservada? 'ON)))
   (is (= true (palabra-reservada? 'ENV)))
   (is (= true (palabra-reservada? 'EXIT)))
   (is (= true (palabra-reservada? 'ATN)))
   (is (= true (palabra-reservada? 'INT)))
   (is (= true (palabra-reservada? 'SIN)))
   (is (= true (palabra-reservada? 'LEN)))
   (is (= true (palabra-reservada? 'MID$)))
   (is (= true (palabra-reservada? 'ASC)))
   (is (= true (palabra-reservada? 'CHR$)))
   (is (= true (palabra-reservada? 'STR$)))
)

(deftest test-operador?
   (is (= true (operador? '+)))
   (is (= true (operador? (symbol "+"))))
   (is (= false (operador? (symbol "%"))))

   (is (= true (operador? '-)))
   (is (= true (operador? '*)))
   (is (= true (operador? '/)))
   (is (= true (operador? '\^))) 
   (is (= true (operador? '=)))
   (is (= true (operador? '<>)))
   (is (= true (operador? '<)))
   (is (= true (operador? '<=)))
   (is (= true (operador? '>)))
   (is (= true (operador? '>=)))
   (is (= true (operador? 'AND)))
   (is (= true (operador? 'OR)))
)

(deftest test-anular-invalidos?
   (is (= '(IF X nil * Y < 12 THEN LET nil X = 0) (anular-invalidos '(IF X & * Y < 12 THEN LET ! X = 0))))
   (is (= '(IF nil nil nil nil nil nil nil nil) (anular-invalidos '(IF ! & \\ _ \{ \} | \~))))
)

(deftest test-cargar-linea?
   (is (= '[((10 (PRINT X))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(10 (PRINT X)) [() [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(20 (X = 100)) ['((10 (PRINT X))) [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X)) (15 (X = X + 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(15 (X = X + 1)) ['((10 (PRINT X)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X)) (15 (X = X - 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(15 (X = X - 1)) ['((10 (PRINT X)) (15 (X = X + 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}])))
)

(deftest test-dar-error?
   ; TODO: ver como armar estos tests para hacer un contains de lo que se printea
   ; (is (= '?SYNTAX ERRORnil (dar-error 16 [:ejecucion-inmediata 4])))

   ; user=> (dar-error 16 [:ejecucion-inmediata 4])
   ;
   ; ?SYNTAX ERRORnil
   ; user=> (dar-error "?ERROR DISK FULL" [:ejecucion-inmediata 4])
   ;
   ; ?ERROR DISK FULLnil
   ; user=> (dar-error 16 [100 3])
   ;
   ; ?SYNTAX ERROR IN 100nil
   ; user=> (dar-error "?ERROR DISK FULL" [100 3])
   ;
   ; ?ERROR DISK FULL IN 100nil

   ;   (case cod
   ;  0 "?NEXT WITHOUT FOR ERROR"
   ;  6 "FILE NOT FOUND"
   ;  15 "NOT DIRECT COMMAND"
   ;  16 "?SYNTAX ERROR"
   ;  22 "?RETURN WITHOUT GOSUB ERROR"
   ;  42 "?OUT OF DATA ERROR"
   ;  53 "?ILLEGAL QUANTITY ERROR"
   ;  69 "?OVERFLOW ERROR"
   ;  90 "?UNDEF'D STATEMENT ERROR"
   ;  100 "?ILLEGAL DIRECT ERROR"
   ;  133 "?DIVISION BY ZERO ERROR"
   ;  163 "?TYPE MISMATCH ERROR"
   ;  176 "?STRING TOO LONG ERROR"
   ;  200 "?LOAD WITHIN PROGRAM ERROR"
   ;  201 "?SAVE WITHIN PROGRAM ERROR"
   ;  cod)
)

(deftest test-variable-float?
   (is (= true (variable-float? 'X)))
   (is (= false (variable-float? 'X%)))
   (is (= false (variable-float? 'X$)))
)

(run-tests)