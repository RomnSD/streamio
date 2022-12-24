### Streamio 

###### A simple buffer library for Java.

In summary, Streamio allocates memory according to the needs, separating its content in memory blocks, for example:

Heap memory:

```java
    Streamio stream = Streamio.heap(100, 16);
    stream.put(1, (byte) 5); // 1 allocation (from 0, to 15)

    byte[] content = stream.array(0, stream.size()); // contains 0, 5, 0, 0, 0, 0...

```
<br>

Direct memory:
```java
    Streamio stream = Streamio.direct(100, 16);
    stream.put(1, (byte) 5); // 1 allocation (from 0, to 15)

    byte[] content = stream.array(0, stream.size()); // contains 0, 5, 0, 0, 0, 0..
```
<br>

If you would like to transfer the content to a ByteBuffer:
```java
    Streamio stream = Streamio.heap(50, 25);
    stream.put(1, (byte) 255);
    ... 

    ByteBuffer buffer = ByteBuffer.allocate(100);
    stream.writeTo(buffer, 0, stream.size());

```

<br>

> Streamio tries to follow the concept of a resizable ByteBuffer, but with a taste quite different.

<br>

<p>Obviously Streamio does not have the same performance speed as ByteBuffer but this concept is interesting for me (and hoping for you too), perhaps, you can benchmark them and see what I mean.</p>

<p>Currently Streamio is more efficient while using a sequencial access than a random one. <sup>(Thanks to internal cache system)</sup><p>

<p>Streamio is still in progress, but if you would like to help, you can do it by submitting a pull request. I really would appreacite that.</p>

##### Improvements to implement: 
- Checks parameters when trying creating a stream.
- Choose the correct lookup table when calling a factory method.