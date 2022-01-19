#!/bin/bash

for i in {1..20}
do
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Heap.Used >> Status.JVM.Memory.Heap.Used.txt
  echo "" >> Status.JVM.Memory.Heap.Used.txt 
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Heap.Committed >> Status.JVM.Memory.Heap.Committed.txt
  echo "" >> Status.JVM.Memory.Heap.Committed.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.NonHeap.Used >> Status.JVM.Memory.NonHeap.Used.txt
  echo "" >> Status.JVM.Memory.NonHeap.Used.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.NonHeap.Committed >> Status.JVM.Memory.NonHeap.Committed.txt
  echo "" >> Status.JVM.Memory.NonHeap.Committed.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Metaspace.Used >> Status.JVM.Memory.Metaspace.Used.txt
  echo "" >> Status.JVM.Memory.Metaspace.Used.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Metaspace.Committed >> Status.JVM.Memory.Metaspace.Committed.txt
  echo "" >> Status.JVM.Memory.Metaspace.Committed.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Direct.MemoryUsed >> Status.JVM.Memory.Direct.MemoryUsed.txt
  echo "" >> Status.JVM.Memory.Direct.MemoryUsed.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Mapped.MemoryUsed >> Status.JVM.Memory.Mapped.MemoryUsed.txt
  echo "" >> Status.JVM.Memory.Mapped.MemoryUsed.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Managed.Used >> Status.JVM.Memory.Managed.Used.txt
  echo "" >> Status.JVM.Memory.Managed.Used.txt
  curl localhost:8081/jobmanager/metrics?get=Status.JVM.Memory.Managed.Total >> Status.JVM.Memory.Managed.Total.txt
  echo "" >> Status.JVM.Memory.Managed.Total.txt
  sleep 5   
done
