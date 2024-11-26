package com.softserve.edu06arch;

import io.github.cdimascio.dotenv.Dotenv;

public class App {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.out.println("dotenv.get('browser') = " + dotenv.get("browser"));
        System.out.println("dotenv.get('status') = " + dotenv.get("status"));
    }
}