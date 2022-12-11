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

    public void make(Request requestType, Object data) throws IOException {
        outputStream.writeObject(requestType);

        switch (requestType) {
            case login -> outputStream.writeObject(data);
            case getUserById -> outputStream.writeObject(data);
            case getUserByLogin -> outputStream.writeObject(data);
            case insertUser -> outputStream.writeObject(data);
            case deleteById -> outputStream.writeObject(data);
            case updateUser -> outputStream.writeObject(data);
        }
    }

    public <T> T receive() throws IOException, ClassNotFoundException {
        return (T) inputStream.readObject();
    }

    public void configure(ObjectInputStream in, ObjectOutputStream out) {
        this.inputStream = in;
        this.outputStream = out;
    }
}