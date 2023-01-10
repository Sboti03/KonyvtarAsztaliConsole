package org.example;

import com.sun.tools.jconsole.JConsoleContext;

import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main {

    public static BookDB bookDB;
    public static List<Konyv> books;

    public static void main(String[] args) {
        try {
            bookDB = new BookDB();
            books = bookDB.readBooks();

            elso();
            masodik();
            harmadik();
            negyedik();
            otodik();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public static void elso() throws SQLException {
        int count = 0;
        for (Konyv k : books) {
            if (k.getPage_count() > 500) {
                count++;
            }
        }

        System.out.println("500 oldalnál hosszabb könyvek száma: " + count);
    }


    public static void masodik() {

        boolean szerepel = false;
        int i = 0;
        while (i < books.size() && !szerepel) {
            if (books.get(i).getPublish_year() < 1950) {
                szerepel = true;
            }
            i++;
        }

        if (szerepel) {
            System.out.println("Van 1950-nél régebbi könyv");
        } else {
            System.out.println("Nincs 1950-nél régebbi könyv");

        }
    }

    public static void harmadik() {
        List<Konyv> sorted = books;
        sorted.sort((a, b) -> b.getPage_count() - a.getPage_count());
        System.out.println("A leghosszabb könyv adatai:");
        System.out.println(sorted.get(0));
    }


    public static void negyedik() {
        Map<String, Integer> bookPerAuthors = new HashMap<>();
        for (Konyv k : books) {
            bookPerAuthors.merge(k.getAuthor(), 1, Integer::sum);
        }

        String author = "";
        int count = 0;
        for (Map.Entry<String, Integer> e : bookPerAuthors.entrySet()) {
            if (count < e.getValue()) {
                author = e.getKey();
                count = e.getValue();
            }
        }
        System.out.println("A legtöbb könyvvel rendelkező: " + author);
    }


    public static void otodik() {
        System.out.print("Adjon meg egy könyv címét: ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        Konyv konyv = books.stream().filter(k-> k.getTitle().equals(input)).findAny().orElse(null);
        if (konyv == null) {
            System.out.println("Nincs ilyen könyv");
        } else {
            System.out.println("A megadott könyv szerzője: " + konyv.getAuthor());
        }
    }



}