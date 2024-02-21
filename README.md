# Compiler Final Project
---
Final project for the Compiler's course offered by Universidade Federal do ABC (UFABC).


### Summary
The goal was to create a compiler for a simple programming language with a syntax written in Portuguese (Brazil) compiled to Java. Language's example:
```
inicio
{
    declare int a,b;
    
    a = 5;
    c = 2.5;
    
    se (a < c) entao {
        escreva(a);
    }
}
fim
```

A complete example program used in the tests can be found in the `input.in` file of this repository.


### Technology Used
The compiler was written in `Java` and uses [ANTLR](https://www.antlr.org/) as parser generator.


### How to test
- Execute in an environment with java installed: `compila.sh`
- This will read the `input.in` file with the portuguese syntax code and compile it to java, the output can be found at `Compilado.java`
- The `gramatica.g` contains the antlr specifications for the parser generator
