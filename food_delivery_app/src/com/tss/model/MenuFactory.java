package com.tss.model;


public class MenuFactory {
    public static IMenu getMenu(String type) {
        return switch (type.toLowerCase()) {
            case "italian" -> new ItalianMenu();
            case "indian" -> new IndianMenu();
//            case "chinese" -> new ChineseMenu();  // Create this class if not done
            default -> throw new IllegalArgumentException("❌ Unknown menu type: " + type);
        };
    }
}
