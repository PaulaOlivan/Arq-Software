/*
* Autores: Paula Oliván (771938) & Juan Pellicer (818138)
* Fecha de creación: 17/02/2023
* Comentario: Clase Subject. Clase abstracta que define la interfaz de los sujetos.   
*/
#pragma once

#include <iostream>
#include <list>
#include "observer.hpp"



using namespace std;

//Forward declaration
class Observer;

const int MAX_OBSERVERS = 5;
//Clase subject
class Subject
{
protected:
    //Atributos
    string miNombre;
    
    //Lista que contiene los observers
    list<Observer *> observers;
    
    int numObservers = 0;

public:
    //Constructor
    Subject(string nombre): miNombre(nombre){};
    
    //Function that notifies the observer so it updates its state
    void notify();

    void attach(Observer *o);

    void detach(Observer *o);
};
