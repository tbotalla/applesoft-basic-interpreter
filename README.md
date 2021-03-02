# applesoft-basic-interpreter
Applesoft BASIC interpreter in Clojure

### Ejecutar interprete Clojure
rlwrap java -jar clojure-1.8.0.jar
### Uso del interprete en Clojure
```
(load-file "basic.clj")
(driver-loop) # Inicia el interprete de Applesoft BASIC
LOAD "BASIC/FIBO.BAS" # Carga de programa a ejecutar
RUN # Ejecucion de programa
list # Muestra las lineas del programa cargado
env # Muestra el ambiente
exit # Salir del interprete
new # Limpia el ambiente
```

### Uso de spy.clj (debugging)
(load-file "spy.clj")
(do (string-a-tokens (read-line)) true)
Aca escribir las lineas de BASIC que quiera:
12?.5:data1,dos,3,hola mundo:print"hi data:";:data 4,5:rem data6,7

### Uso de tests.clj
Principales palabras reservadas/funciones "nuevas": require, deftest, run-tests
- En el archivo que ponemos en el load-file tenemos que tener las funciónes que queremos probar.
- Uso: (load-file "tests.clj")
