package com.tss.model.menu;

import java.io.*;

public class MenuIdGenerator {
    private static final String FILE_NAME = "menu_id.txt";
    private static int currentId = 1;

    static {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                currentId = ois.readInt();
            } catch (Exception e) {
                System.out.println("Could not load ID counter: " + e.getMessage());
            }
        }
    }

    public static synchronized int getNextId() {
        int id = currentId++;
        save();
        return id;
    }

    private static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeInt(currentId);
        } catch (IOException e) {
            System.out.println("Could not save ID counter: " + e.getMessage());
        }
    }
}
