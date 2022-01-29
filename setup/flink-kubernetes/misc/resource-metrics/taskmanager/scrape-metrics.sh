#!/bin/bash

for i in {1..20}
do
  curl localhost:8081/taskmanagers/172.18.0.16:38553-709d7d/metrics?get=Status.JVM.Memory.Heap.Used >> Status.JVM.Memory.Heap.Used.txt
  echo "" >> Status.JVM.Memory.Heap.Used.txt
  curl localhost:8081/taskmanagers/172.18.0.16:38553-709d7d/metrics?get=Status.JVM.Memory.NonHeap.Used >> Status.JVM.Memory.NonHeap.Used.txt
  echo "" >> Status.JVM.Memory.NonHeap.Used.txt
  curl localhost:8081/taskmanagers/172.18.0.16:38553-709d7d/metrics?get=Status.JVM.Memory.Metaspace.Used >> Status.JVM.Memory.Metaspace.Used.txt
  echo "" >> Status.JVM.Memory.Metaspace.Used.txt
  curl localhost:8081/taskmanagers/172.18.0.16:38553-709d7d/metrics?get=Status.JVM.Memory.Direct.MemoryUsed >> Status.JVM.Memory.Direct.MemoryUsed.txt
  echo "" >> Status.JVM.Memory.Direct.MemoryUsed.txt
  sleep 5   
done
