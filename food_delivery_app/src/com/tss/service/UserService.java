package com.tss.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.tss.model.User;

public class UserService {
	private static final String FILE_PATH = "users.txt";

	public boolean signUp(User newUser) {
		List<User> users = loadUsers();

		if (users.contains(newUser)) {
			return false;
		}

		users.add(newUser);
		saveUsers(users);
		return true;
	}

	public boolean login(String username, String password) {
		List<User> users = loadUsers();

		return users.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
	}

	private List<User> loadUsers() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
			return (List<User>) ois.readObject();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}
	}

	private void saveUsers(List<User> users) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
			oos.writeObject(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
