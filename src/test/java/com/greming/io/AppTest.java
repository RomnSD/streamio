package com.greming.io;

import org.junit.Assert;
import org.junit.Test;

import com.broman.streamio.Streamio;

public class AppTest {

    @Test
    public void shouldCreateAStreamAndPut2Bytes() {
        byte v0 = (byte) 1;
        byte v1 = (byte) 2;

        Streamio stream = Streamio.heap(2, 2);
        stream.put(0, v0);
        stream.put(1, v1);

        Assert.assertEquals(v0, stream.get(0));
        Assert.assertEquals(v1, stream.get(1));
    }

    @Test
    public void shouldCreateAStreamAndReturnAByteArray() {
        byte[] bytes = new byte[15];
        java.util.Arrays.fill(bytes, (byte) 1);

        Streamio stream = Streamio.heap(50, 50);
        for (int i = 0; i < bytes.length; i++) {
            stream.put(i, bytes[i]);
        }

        Assert.assertArrayEquals(bytes, stream.array(0, bytes.length));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void shouldThrowExceptionWithInvalidIndexRange() {
        Streamio stream = Streamio.heap(5, 5);
        stream.get(6);
    }

    @Test(expected=NegativeArraySizeException.class)
    public void shouldThrowExceptionWithInvalidByteArraySize() {
        Streamio stream = Streamio.heap(5, 5);
        stream.array(1, 0);
    }

}
