package ot.homework5plus.rushm.service;

public interface IOService {

    void write(String text);

    String read();

    Integer readInt();

    void write(long count);
}
