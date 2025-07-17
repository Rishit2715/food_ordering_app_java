package com.tss.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMenu implements IMenu {
	protected List<MenuItem> items;
	private final String fileName;
	private final String menuName;

	@SuppressWarnings("unchecked")
	public AbstractMenu(String fileName, String menuName) {
		this.fileName = fileName;
		this.menuName = menuName;
		items = new ArrayList<>();
		File file = new File(fileName);
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				items = (List<MenuItem>) ois.readObject();
				System.out.println("Loaded menu from file: " + fileName);
			} catch (Exception e) {
				System.out.println("Could not load menu: " + e.getMessage());
			}
		}
	}

	private void saveToFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(items);
		} catch (IOException e) {
			System.out.println("Could not save menu: " + e.getMessage());
		}
	}

	@Override
	public void addItem(MenuItem item) {
		items.add(item);
		saveToFile();
		System.out.println(item.getName() + " added to " + menuName + " Menu.");
	}

	@Override
	public void removeItem(int id) {
		//used predicate which returns boolean
		boolean removed = items.removeIf(i -> i.getId() == id);
		if (removed) {
			System.out.println("Item with ID " + id + " removed from " + menuName + " Menu.");
			saveToFile();
		} else {
			System.out.println("Item with ID " + id + " not found in " + menuName + " Menu.");
		}
	}

	@Override
	public void editItem(MenuItem newItem) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getId() == newItem.getId()) {
				items.set(i, newItem);
				System.out.println("✅ " + newItem.getName() + " updated in " + menuName + " Menu.");
				saveToFile();
				return;
			}
		}
		System.out.println("Item with ID " + newItem.getId() + " not found for update in " + menuName + " Menu.");
	}

	@Override
	public void displayMenu() {
		System.out.println("------ " + menuName + " Menu ------");
		if (items.isEmpty()) {
			System.out.println("No items in menu.");
		} else {
			System.out.printf("%-5s | %-25s | %-10s | %-40s%n", "ID", "Name", "Price", "Description");
			System.out.println(
					"-------------------------------------------------------------------------------------------");

			for (MenuItem item : items) {
				System.out.printf("%-5d | %-25s | ₹%-9.2f | %-40s%n", item.getId(), item.getName(), item.getPrice(),
						item.getDescription());
			}
		}
	}

	@Override
	public List<MenuItem> getItems() {
		return items;
	}
}
