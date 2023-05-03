#!/usr/bin/env python
import pika

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='rdp02.cps.unizar.es',credentials=pika.PlainCredentials('alumno','alumno')))
channel = connection.channel()

channel.queue_declare(queue='task_as', durable=True)

channel.basic_publish(exchange='', routing_key='task_as', body='Somos Juan y Paula ...', durable=True)
print(" [x] Sent 'Message'")
connection.close()