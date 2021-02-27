; Tomas Enrique Botalla - Padron 96356
(require '[clojure.test :refer [is deftest run-tests]])

(load-file "basic.clj")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Funciones a testear
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; (declare palabra-reservada?)              ; IMPLEMENTAR x
;; (declare operador?)                       ; IMPLEMENTAR x
;; (declare anular-invalidos)                ; IMPLEMENTAR x
;; (declare cargar-linea)                    ; IMPLEMENTAR x
;; (declare expandir-nexts)                  ; IMPLEMENTAR x
;; (declare dar-error)                       ; IMPLEMENTAR x
;; (declare variable-float?)                 ; IMPLEMENTAR x
;; (declare variable-integer?)               ; IMPLEMENTAR x
;; (declare variable-string?)                ; IMPLEMENTAR x
;; (declare contar-sentencias)               ; IMPLEMENTAR x
;; (declare buscar-lineas-restantes)         ; IMPLEMENTAR x
;; (declare continuar-linea)                 ; IMPLEMENTAR x
;; (declare extraer-data)                    ; IMPLEMENTAR x
;; (declare ejecutar-asignacion)             ; IMPLEMENTAR x
;; (declare preprocesar-expresion)           ; IMPLEMENTAR x
;; (declare desambiguar)                     ; IMPLEMENTAR x
;; (declare precedencia)                     ; IMPLEMENTAR x
;; (declare aridad)                          ; IMPLEMENTAR x
;; (declare eliminar-cero-decimal)           ; IMPLEMENTAR x
;; (declare eliminar-cero-entero)            ; IMPLEMENTAR x


; user=> (palabra-reservada? 'REM)
; true
; user=> (palabra-reservada? 'SPACE)
; false
(deftest test-palabra-reservada?
   ;; (is (= true (palabra-reservada? 'LOAD)))
   ;; (is (= true (palabra-reservada? 'SAVE)))
   (is (= true (palabra-reservada? 'INPUT)))
   (is (= true (palabra-reservada? 'PRINT)))
   ;; (is (= true (palabra-reservada? '?)))
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
   ;; (is (= true (palabra-reservada? 'ENV)))
   ;; (is (= true (palabra-reservada? 'EXIT)))
   (is (= true (palabra-reservada? 'ATN)))
   (is (= true (palabra-reservada? 'INT)))
   (is (= true (palabra-reservada? 'SIN)))
   (is (= true (palabra-reservada? 'LEN)))
   (is (= true (palabra-reservada? 'MID$)))
   (is (= true (palabra-reservada? 'ASC)))
   (is (= true (palabra-reservada? 'CHR$)))
   (is (= true (palabra-reservada? 'STR$)))
)

; user=> (operador? '+)
; true
; user=> (operador? (symbol "+"))
; true
; user=> (operador? (symbol "%"))
; false
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

; user=> (anular-invalidos '(IF X & * Y < 12 THEN LET ! X = 0))
; (IF X nil * Y < 12 THEN LET nil X = 0)
(deftest test-anular-invalidos?
   (is (= '(IF X nil * Y < 12 THEN LET nil X = 0) (anular-invalidos '(IF X & * Y < 12 THEN LET ! X = 0))))
   (is (= '(IF nil nil nil nil nil nil nil nil) (anular-invalidos '(IF ! & \\ _ \{ \} | \~))))
)

; user=> (cargar-linea '(10 (PRINT X)) [() [:ejecucion-inmediata 0] [] [] [] 0 {}])
; [((10 (PRINT X))) [:ejecucion-inmediata 0] [] [] [] 0 {}]
; user=> (cargar-linea '(20 (X = 100)) ['((10 (PRINT X))) [:ejecucion-inmediata 0] [] [] [] 0 {}])
; [((10 (PRINT X)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}]
; user=> (cargar-linea '(15 (X = X + 1)) ['((10 (PRINT X)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}])
; [((10 (PRINT X)) (15 (X = X + 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}]
; user=> (cargar-linea '(15 (X = X - 1)) ['((10 (PRINT X)) (15 (X = X + 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}])
; [((10 (PRINT X)) (15 (X = X - 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}]
(deftest test-cargar-linea?
   (is (= '[((10 (PRINT X))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(10 (PRINT X)) [() [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(20 (X = 100)) ['((10 (PRINT X))) [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X)) (15 (X = X + 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(15 (X = X + 1)) ['((10 (PRINT X)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X)) (15 (X = X - 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}] (cargar-linea '(15 (X = X - 1)) ['((10 (PRINT X)) (15 (X = X + 1)) (20 (X = 100))) [:ejecucion-inmediata 0] [] [] [] 0 {}])))
)

; user=> (def n (list '(PRINT 1) (list 'NEXT 'A (symbol ",") 'B)))
; #'user/n
; user=> n
; ((PRINT 1) (NEXT A , B))
; user=> (expandir-nexts n)
; ((PRINT 1) (NEXT A) (NEXT B))
(deftest expandir-nexts?
   (def n (list '(PRINT 1) (list 'NEXT 'A (symbol ",") 'B)))
   (is (= '((PRINT 1) (NEXT A) (NEXT B)) (expandir-nexts n)))
   (is (= '((PRINT 1) (NEXT A) (NEXT B) (NEXT C) (PRINT 10) (NEXT D) (NEXT E)) (expandir-nexts '((PRINT 1) (NEXT A , B , C) (PRINT 10) (NEXT D,E)))))
)

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
(deftest test-dar-error?
   ; TODO: ver como armar estos tests para hacer un contains de lo que se printea. Probar siguiente:
   ; (let [printed (with-out-str (dar-error 16 [:ejecucion-inmediata 4]))] (prn (str "PRINTED=" printed)))

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
)

; user=> (variable-float? 'X)
; true
; user=> (variable-float? 'X%)
; false
; user=> (variable-float? 'X$)
; false
(deftest test-variable-float?
   (is (= true (variable-float? 'X)))
   (is (= false (variable-float? 'X%)))
   (is (= false (variable-float? 'X$)))
)

; user=> (variable-integer? 'X%)
; true
; user=> (variable-integer? 'X)
; false
; user=> (variable-integer? 'X$)
; false
(deftest test-variable-integer?
   (is (= true (variable-integer? 'X%)))
   (is (= false (variable-integer? 'X)))
   (is (= false (variable-integer? 'X$)))
)

; user=> (variable-string? 'X$)
; true
; user=> (variable-string? 'X)
; false
; user=> (variable-string? 'X%)
; false
(deftest test-variable-string?
   (is (= true (variable-string? 'X$)))
   (is (= false (variable-string? 'X)))
   (is (= false (variable-string? 'X%)))
)

; user=> (contar-sentencias 10 [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])
; 2
; user=> (contar-sentencias 15 [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])
; 1
; user=> (contar-sentencias 20 [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])
; 2
(deftest contar-sentencias?
   (is (= 2 (contar-sentencias 10 [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])))
   (is (= 1 (contar-sentencias 15 [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])))
   (is (= 2 (contar-sentencias 20 [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])))
)

; user=> (buscar-lineas-restantes [() [:ejecucion-inmediata 0] [] [] [] 0 {}])
; nil
; user=> (buscar-lineas-restantes ['((PRINT X) (PRINT Y)) [:ejecucion-inmediata 2] [] [] [] 0 {}])
; nil
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 2] [] [] [] 0 {}])
; ((10 (PRINT X) (PRINT Y)) (15 (X = X + 1)) (20 (NEXT I , J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])
; ((10 (PRINT Y)) (15 (X = X + 1)) (20 (NEXT I , J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 0] [] [] [] 0 {}])
; ((10) (15 (X = X + 1)) (20 (NEXT I , J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [15 1] [] [] [] 0 {}])
; ((15 (X = X + 1)) (20 (NEXT I , J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [15 0] [] [] [] 0 {}])
; ((15) (20 (NEXT I , J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [] [] [] 0 {}])
; ((20 (NEXT I) (NEXT J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 2] [] [] [] 0 {}])
; ((20 (NEXT I) (NEXT J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 1] [] [] [] 0 {}])
; ((20 (NEXT J)))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 0] [] [] [] 0 {}])
; ((20))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 -1] [] [] [] 0 {}])
; ((20))
; user=> (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [25 0] [] [] [] 0 {}])
; nil
(deftest buscar-lineas-restantes?
   (is (= nil (buscar-lineas-restantes [() [:ejecucion-inmediata 0] [] [] [] 0 {}])))
   (is (= nil (buscar-lineas-restantes ['((PRINT X) (PRINT Y)) [:ejecucion-inmediata 2] [] [] [] 0 {}])))
   (is (= (list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 2] [] [] [] 0 {}])))
   (is (= (list '(10 (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 1] [] [] [] 0 {}])))
   (is (= (list '(10) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [10 0] [] [] [] 0 {}])))
   (is (= (list '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [15 1] [] [] [] 0 {}])))
   (is (= (list '(15) (list 20 (list 'NEXT 'I (symbol ",") 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [15 0] [] [] [] 0 {}])))
   (is (= (list (list 20 (list 'NEXT 'I) (list 'NEXT 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [] [] [] 0 {}])))
   (is (= (list (list 20 (list 'NEXT 'I) (list 'NEXT 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 2] [] [] [] 0 {}])))
   (is (= (list (list 20 (list 'NEXT 'J))) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 1] [] [] [] 0 {}])))
   (is (= (list (list 20)) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 0] [] [] [] 0 {}])))
   (is (= (list (list 20)) (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 -1] [] [] [] 0 {}])))
   (is (= nil (buscar-lineas-restantes [(list '(10 (PRINT X) (PRINT Y)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [25 0] [] [] [] 0 {}])))
)

; user=> (continuar-linea [(list '(10 (PRINT X)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [] [] [] 0 {}])
; 
; ?RETURN WITHOUT GOSUB ERROR IN 20[nil [((10 (PRINT X)) (15 (X = X + 1)) (20 (NEXT I , J))) [20 3] [] [] [] 0 {}]]
; user=> (continuar-linea [(list '(10 (PRINT X)) '(15 (GOSUB 100) (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [[15 2]] [] [] 0 {}])
; [:omitir-restante [((10 (PRINT X)) (15 (GOSUB 100) (X = X + 1)) (20 (NEXT I , J))) [15 1] [] [] [] 0 {}]]
(deftest continuar-linea?
   (is (= (vector nil [(list '(10 (PRINT X)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [] [] [] 0 {}]) (continuar-linea [(list '(10 (PRINT X)) '(15 (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [] [] [] 0 {}])))
   (is (= [:omitir-restante [(list '(10 (PRINT X)) '(15 (GOSUB 100) (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [15 1] [] [] [] 0 {}]] (continuar-linea [(list '(10 (PRINT X)) '(15 (GOSUB 100) (X = X + 1)) (list 20 (list 'NEXT 'I (symbol ",") 'J))) [20 3] [[15 2]] [] [] 0 {}])))
)

; user=> (extraer-data '(()))
; ()
; user=> (extraer-data (list '(10 (PRINT X) (REM ESTE NO) (DATA 30)) '(20 (DATA HOLA)) (list 100 (list 'DATA 'MUNDO (symbol ",") 10 (symbol ",") 20))))
; ("HOLA" "MUNDO" 10 20)
(deftest extraer-data?
   (is (= '() (extraer-data '(()))))
   (is (= '("HOLA" "MUNDO" 10 20) (extraer-data (list '(10 (PRINT X) (REM ESTE NO) (DATA 30)) '(20 (DATA HOLA)) (list 100 (list 'DATA 'MUNDO (symbol ",") 10 (symbol ",") 20))))))
)

; user=> (ejecutar-asignacion '(X = 5) ['((10 (PRINT X))) [10 1] [] [] [] 0 {}])
; [((10 (PRINT X))) [10 1] [] [] [] 0 {X 5}]
; user=> (ejecutar-asignacion '(X = 5) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X 2}])
; [((10 (PRINT X))) [10 1] [] [] [] 0 {X 5}]
; user=> (ejecutar-asignacion '(X = X + 1) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X 2}])
; [((10 (PRINT X))) [10 1] [] [] [] 0 {X 3}]
; user=> (ejecutar-asignacion '(X$ = X$ + " MUNDO") ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X$ "HOLA"}])
; [((10 (PRINT X))) [10 1] [] [] [] 0 {X$ "HOLA MUNDO"}]
(deftest ejecutar-asignacion?
   (is (= '[((10 (PRINT X))) [10 1] [] [] [] 0 {X 5}] (ejecutar-asignacion '(X = 5) ['((10 (PRINT X))) [10 1] [] [] [] 0 {}])))
   (is (= '[((10 (PRINT X))) [10 1] [] [] [] 0 {X 5}] (ejecutar-asignacion '(X = 5) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X 2}])))
   (is (= '[((10 (PRINT X))) [10 1] [] [] [] 0 {X 3}] (ejecutar-asignacion '(X = X + 1) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X 2}])))
   (is (= '[((10 (PRINT X))) [10 1] [] [] [] 0 {X$ "HOLA MUNDO"}] (ejecutar-asignacion '(X$ = X$ + " MUNDO") ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X$ "HOLA"}])))
)

; user=> (preprocesar-expresion '(X$ + " MUNDO" + Z$) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X$ "HOLA"}])
; ("HOLA" + " MUNDO" + "")
; user=> (preprocesar-expresion '(X + . / Y% * Z) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X 5 Y% 2}])
; (5 + 0 / 2 * 0)
(deftest preprocesar-expresion?
   (is (= '("HOLA" + " MUNDO" + "") (preprocesar-expresion '(X$ + " MUNDO" + Z$) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X$ "HOLA"}])))
   (is (= '(5 + 0 / 2 * 0) (preprocesar-expresion '(X + . / Y% * Z) ['((10 (PRINT X))) [10 1] [] [] [] 0 '{X 5 Y% 2}])))
)

; user=> (desambiguar (list '- 2 '* (symbol "(") '- 3 '+ 5 '- (symbol "(") '+ 2 '/ 7 (symbol ")") (symbol ")")))
; (-u 2 * ( -u 3 + 5 - ( 2 / 7 ) ))
; user=> (desambiguar (list 'MID$ (symbol "(") 1 (symbol ",") 2 (symbol ")")))
; (MID$ ( 1 , 2 ))
; user=> (desambiguar (list 'MID$ (symbol "(") 1 (symbol ",") 2 (symbol ",") 3 (symbol ")")))
; (MID3$ ( 1 , 2 , 3 ))
; user=> (desambiguar (list 'MID$ (symbol "(") 1 (symbol ",") '- 2 '+ 'K (symbol ",") 3 (symbol ")")))
; (MID3$ ( 1 , -u 2 + K , 3 ))
(deftest desambiguar? 
   (is (= (list (symbol "-u") 2 '* (symbol "(") (symbol "-u") 3 '+ 5 '- (symbol "(") 2 '/ 7 (symbol ")") (symbol ")")) (desambiguar (list '- 2 '* (symbol "(") '- 3 '+ 5 '- (symbol "(") '+ 2 '/ 7 (symbol ")") (symbol ")")))))
   (is (= (list 'MID$ (symbol "(") 1 (symbol ",") 2 (symbol ")")) (desambiguar (list 'MID$ (symbol "(") 1 (symbol ",") 2 (symbol ")")))))
   (is (= (list 'MID3$ (symbol "(") 1 (symbol ",") 2 (symbol ",") 3 (symbol ")")) (desambiguar (list 'MID$ (symbol "(") 1 (symbol ",") 2 (symbol ",") 3 (symbol ")")))))
   (is (= (list 'MID3$ (symbol "(") 1 (symbol ",") (symbol "-u") 2 '+ 'K (symbol ",") 3 (symbol ")")) (desambiguar (list 'MID$ (symbol "(") 1 (symbol ",") '- 2 '+ 'K (symbol ",") 3 (symbol ")")))))
)


; user=> (precedencia 'OR)
; 1
; user=> (precedencia 'AND)
; 2
; user=> (precedencia '*)
; 6
; user=> (precedencia '-u)
; 7
; user=> (precedencia 'MID$)
; 9
(deftest precedencia?
   (is (= 1 (precedencia 'OR)))
   (is (= 2 (precedencia 'AND)))
   (is (= 6 (precedencia '*)))
   (is (= 7 (precedencia '-u)))
   (is (= 9 (precedencia 'MID$)))
)

; user=> (aridad 'THEN)
; 0
; user=> (aridad 'SIN)
; 1
; user=> (aridad '*)
; 2
; user=> (aridad 'MID$)
; 2
; user=> (aridad 'MID3$)
; 3
(deftest aridad?
   (is (= 0 (aridad 'THEN)))
   (is (= 1 (aridad 'SIN)))
   (is (= 2 (aridad '*)))
   (is (= 2 (aridad 'MID$)))
   (is (= 3 (aridad 'MID3$)))
)


; user=> (eliminar-cero-decimal 1.5)
; 1.5
; user=> (eliminar-cero-decimal 1.50)
; 1.5
; user=> (eliminar-cero-decimal 1.0)
; 1
; user=> (eliminar-cero-decimal 'A)
; A
(deftest eliminar-cero-decimal?
   (is (= 1.5 (eliminar-cero-decimal 1.5)))
   (is (= 1.5 (eliminar-cero-decimal 1.50)))
   (is (= 1 (eliminar-cero-decimal 1.0)))
   (is (= 'A (eliminar-cero-decimal 'A)))

   (is (= 0.5 (eliminar-cero-decimal 0.500)))
)

; user=> (eliminar-cero-entero nil)
; nil
; user=> (eliminar-cero-entero 'A)
; "A"
; user=> (eliminar-cero-entero 0)
; "0"
; user=> (eliminar-cero-entero 1.5)
; "1.5"
; user=> (eliminar-cero-entero 1)
; "1"
; user=> (eliminar-cero-entero -1)
; "-1"
; user=> (eliminar-cero-entero -1.5)
; "-1.5"
; user=> (eliminar-cero-entero 0.5)
; ".5"
; user=> (eliminar-cero-entero -0.5)
; "-.5"
(deftest eliminar-cero-entero?
   (is (= nil (eliminar-cero-entero nil)))
   (is (= "A" (eliminar-cero-entero 'A)))
   (is (= "0" (eliminar-cero-entero 0)))
   (is (= "1.5" (eliminar-cero-entero 1.5)))
   (is (= "1" (eliminar-cero-entero 1)))
   (is (= "-1" (eliminar-cero-entero -1)))
   (is (= "-1.5" (eliminar-cero-entero -1.5)))
   (is (= ".5" (eliminar-cero-entero 0.5)))
   (is (= "-.5" (eliminar-cero-entero -0.5)))
)

(run-tests)