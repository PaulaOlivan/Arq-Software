#!/usr/bin/env python
import pika
import sys

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='rdp02.cps.unizar.es',credentials=pika.PlainCredentials('alumno','alumno')))
channel = connection.channel()

channel.exchange_declare(exchange='as1', exchange_type='fanout')

message = ' '.join(sys.argv[1:]) or "info: Hello World!"
channel.basic_publish(exchange='as1', routing_key='', body="Juan y Paula")
print(" [x] Sent %r" % message)
connection.close()