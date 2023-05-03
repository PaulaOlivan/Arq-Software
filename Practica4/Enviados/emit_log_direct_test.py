#!/usr/bin/env python
import pika
import sys

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='rdp02.cps.unizar.es',credentials=pika.PlainCredentials('alumno','alumno')))
channel = connection.channel()

channel.exchange_declare(exchange='as2', exchange_type='direct')

severity = sys.argv[1] if len(sys.argv) > 1 else 'info'
routing = ' '.join(sys.argv[1:2])
message = ' '.join(sys.argv[2:])

channel.basic_publish(
    exchange='as2', routing_key=routing, body=message)
print(" [x] Sent %r:%r" % (routing, message))
connection.close()