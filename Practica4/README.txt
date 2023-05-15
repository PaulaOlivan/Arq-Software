La ejecución esta pensada para ser realizada de manera distribuida, por lo que se debe ejecutar en diferentes máquinas de Lab1.02,
estas máquinas son: 
    - Broker en Lab102-203
    - Productor en Lab102-205
    - Consumidores en Lab102-206

En primer lugar se deberán subir los archivos a hendrix para poder ser invocados desde el laboratorio. Para ello se deberá ejecutar el comando scp.
Posteriormente se deberá compilar los archivos en hendrix, para ello se deberá ejecutar el comando javac *.java. Esto nos generará los .class
correspondientes en el directorio de hendrix y solo será necesario ejecutarlo desde una de las terminales.

Tres de las terminales existentes actuarán como servidores por tanto en ellas se deberá ejecutar el comando rmiregistry de la siguiente forma:
    - Terminal del Broker(Lab102-203): rmiregistry 32003&
    - Terminal del Consumidor1(Lab102-206): rmiregistry 32006&
    - Terminal del Consumidor2(Lab102-206): rmiregistry 32007&

Finalmente se deberá invocar a todos los objetos .class para ejecutar el sistema, se deberá ejecutar el comando java de la siguiente forma:
    - Terminal del Broker(Lab102-203): java BrokerImpl
    - Terminal del Consumidor1(Lab102-206): java ConsumidorImpl Cons_771 155.210.154.206:32006 Cola_1
    - Terminal del Consumidor2(Lab102-206): java ConsumidorImpl Cons_938 155.210.154.206:32007 Cola_1
    - Terminal del Productor(Lab102-205): java Productor Broker_771 155.210.154.203:32003 Cola_1

Se recomienda invocar a los objetos .class en el orden escrito para evitar fallos en la ejecución debido a que los servidores no estén listos.
En caso de no invocar a las funciones con el número de parámetros adecuado se mostrará un mensaje de error indicando el número de parámetros y
el orden en el que deben ser introducidos.