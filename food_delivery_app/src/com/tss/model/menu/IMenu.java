package com.tss.model.menu;

import java.util.List;

public interface IMenu {
	void addItem(MenuItem item);

	void removeItem(int id);

	void editItem(MenuItem item);

	void displayMenu();

	List<MenuItem> getItems();
}
