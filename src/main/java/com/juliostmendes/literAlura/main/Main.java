package com.juliostmendes.literAlura.main;

import java.util.Scanner;

public class Main {
    Scanner sc = new Scanner(System.in);

    public void showMenu() {
        System.out.println(
                """
                Choose your option:
                1- Search book by title
                2- List registered books
                3- List registered author
                4- List authors alive by year
                5- List books by language
                0 - Exit
                """);
    }
}
