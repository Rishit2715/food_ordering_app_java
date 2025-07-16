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
		if (isDuplicate(item, false)) return;
		menu.addItem(item);
	}

	public void editItem(MenuItem newItem) {
		List<MenuItem> items = menu.getItems();

		for (MenuItem existing : items) {
			if (existing.getId() == newItem.getId()) {
				System.out.println("Current Item:");
				System.out.println(existing);

				if (isDuplicate(newItem, true)) return;

				menu.editItem(newItem);
				return;
			}
		}
		System.out.println("❌ Item with ID " + newItem.getId() + " not found.");
	}

	private boolean isDuplicate(MenuItem item, boolean isEdit) {
		List<MenuItem> items = menu.getItems();
		int id = item.getId();
		String name = item.getName().trim().toLowerCase();
		String desc = item.getDescription().trim().toLowerCase();

		if (!isEdit) {
			boolean idExists = items.stream().anyMatch(i -> i.getId() == id);
			if (idExists) {
				System.out.println("❌ Item with ID " + id + " already exists.");
				return true;
			}
		}

		boolean nameExists = items.stream()
				.anyMatch(i -> (!isEdit || i.getId() != id) &&
						i.getName().trim().equalsIgnoreCase(name));
		if (nameExists) {
			System.out.println("❌ Item with name \"" + item.getName() + "\" already exists.");
			return true;
		}

		boolean descExists = items.stream()
				.anyMatch(i -> (!isEdit || i.getId() != id) &&
						i.getDescription().trim().equalsIgnoreCase(desc));
		if (descExists) {
			System.out.println("❌ Item with description \"" + item.getDescription() + "\" already exists.");
			return true;
		}

		return false;
	}

	public IMenu getMenu() {
		return menu;
	}

	public MenuItem getItemById(int id) {
		return menu.getItems().stream()
				.filter(i -> i.getId() == id)
				.findFirst()
				.orElse(null);
	}

	public void displayMenu() {
		menu.displayMenu();
	}
}
