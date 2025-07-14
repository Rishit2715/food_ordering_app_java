package com.tss.service;

import com.tss.model.IMenu;
import com.tss.model.MenuItem;

import java.util.List;

public class MenuService {
	private IMenu menu;

	public MenuService(IMenu menu) {
		this.menu = menu;
	}

	public void addItem(MenuItem item) {
	    List<MenuItem> items = menu.getItems();

	    int newId = item.getId();
	    String newName = item.getName().trim().toLowerCase();
	    String newDesc = item.getDescription().trim().toLowerCase();

	    boolean idExists = items.stream().anyMatch(i -> i.getId() == newId);
	    if (idExists) {
	        System.out.println("❌ Item with ID " + newId + " already exists.");
	        return;
	    }

	    boolean nameExists = items.stream()
	        .anyMatch(i -> i.getName().trim().toLowerCase().equals(newName));
	    if (nameExists) {
	        System.out.println("❌ Item with name \"" + item.getName() + "\" already exists.");
	        return;
	    }

	    boolean descExists = items.stream()
	        .anyMatch(i -> i.getDescription().trim().toLowerCase().equals(newDesc));
	    if (descExists) {
	        System.out.println("❌ Item with description \"" + item.getDescription() + "\" already exists.");
	        return;
	    }

	    menu.addItem(item);
	}



	public void editItem(MenuItem newItem) {
		List<MenuItem> items = menu.getItems();

		for (MenuItem existing : items) {
			if (existing.getId() == newItem.getId()) {
				System.out.println("Current Item:");
				System.out.println(existing);

				// Check if new name + description already exists (but not for the same ID)
				boolean duplicate = items.stream()
						.anyMatch(i -> i.getId() != newItem.getId() && i.getName().equalsIgnoreCase(newItem.getName())
								&& i.getDescription().equalsIgnoreCase(newItem.getDescription()));

				if (duplicate) {
					System.out.println("❌ Another item with same name and description exists.");
					return;
				}

				menu.editItem(newItem);
				return;
			}
		}
		System.out.println("❌ Item with ID " + newItem.getId() + " not found.");
	}

	public void displayMenu() {
		menu.displayMenu();
	}
}
