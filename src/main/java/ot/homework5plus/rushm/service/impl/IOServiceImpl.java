package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.service.IOService;

import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {
    private Scanner sc = new Scanner(System.in);

    @Override
    public void write(String text) {
        System.out.println(text);
    }

    @Override
    public String read() {
        return sc.nextLine();
    }

    @Override
    public Integer readInt() {
        int count = sc.nextInt();
        sc.nextLine();
        return count;
    }

    @Override
    public void write(long count) {
        System.out.println(count);
    }
}
