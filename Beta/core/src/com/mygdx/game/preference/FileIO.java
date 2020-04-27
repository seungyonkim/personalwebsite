package com.mygdx.game.preference;

import com.mygdx.game.board.Board;

import java.io.*;

public class FileIO {
    private static final String DEFAULT_BOARD = "comp361_BoardLog.txt";
    private static final String DEFAULT_HERO = "comp361_HeroLog.txt";

    public static void WriteObjectToFile(Object obj) {
        try {
            FileOutputStream fo;

            if(obj instanceof Board) {
                fo = new FileOutputStream(DEFAULT_BOARD);
            } else {
                fo = new FileOutputStream(DEFAULT_HERO);
            }

            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(obj);
            oo.close();
//            System.out.println("Success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object ReadBoardFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream(DEFAULT_BOARD);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            Object obj = objIn.readObject();

            System.out.println("The Board Object has been read from file!");
            objIn.close();

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object ReadHeroFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream(DEFAULT_HERO);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            Object obj = objIn.readObject();

            System.out.println("The Hero Object has been read from file!");
            objIn.close();

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}