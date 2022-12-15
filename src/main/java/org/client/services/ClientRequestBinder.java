package org.client.services;

import org.classes.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientRequestBinder {
    public static ClientRequestBinder instance;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientRequestBinder() {
        instance = this;
    }

    public void make(Request requestType) throws IOException { make(requestType, null); }

    public void make(Request requestType, Object data) throws IOException {
        outputStream.writeObject(requestType);
        if (data == null) return;

        outputStream.writeObject(data);
    }

    public <T> T receive() throws IOException, ClassNotFoundException { return (T) inputStream.readObject(); }

    public void configure(ObjectInputStream in, ObjectOutputStream out) {
        this.inputStream = in;
        this.outputStream = out;
    }
}