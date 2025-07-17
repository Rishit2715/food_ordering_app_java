package com.tss.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.tss.exception.DeliveryPartnerLimitExceededException;
import com.tss.exception.DeliveryPartnerMustNotBeEmptyException;
import com.tss.model.DeliveryPartner;

public class DeliveryPartnerService {
	private static final String FILE = "delivery_partners.txt";
	private static final int MAX_PARTNERS = 3;
	private final List<DeliveryPartner> partners;

	@SuppressWarnings("unchecked")
	public DeliveryPartnerService() {
		List<DeliveryPartner> loaded = null;
		File f = new File(FILE);
		if (f.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				loaded = (List<DeliveryPartner>) ois.readObject();
			} catch (Exception ignored) {
			}
		}
		partners = (loaded != null) ? loaded : new ArrayList<>();
	}

	private void save() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
			oos.writeObject(partners);
		} catch (IOException e) {
			System.out.println("Could not save delivery partners: " + e.getMessage());
		}
	}

	public List<DeliveryPartner> getAll() {
		return partners;
	}

	public void addPartner(DeliveryPartner partner) {
		if (partners.size() >= MAX_PARTNERS) {
			throw new DeliveryPartnerLimitExceededException(MAX_PARTNERS);
		}
		boolean dup = partners.stream()
				.anyMatch(p -> p.getId() == partner.getId() || p.getName().equalsIgnoreCase(partner.getName()));
		if (dup) {
			System.out.println("Partner with same ID or name already exists.");
			return;
		}
		partners.add(partner);
		save();
		System.out.println("Delivery partner added.");
	}

	public boolean removePartnerById(int id) {
		if (partners.size() == 1) {
			throw new DeliveryPartnerMustNotBeEmptyException(
					"At least one delivery partner must remain. Cannot remove the last one.");
		}

		boolean removed = partners.removeIf(p -> p.getId() == id);
		if (removed) {
			save();
		}
		return removed;
	}

	public Optional<DeliveryPartner> getRandomPartner() {
		if (partners.isEmpty())
			return Optional.empty();
		return Optional.of(partners.get(new Random().nextInt(partners.size())));
	}

	public void listPartners() {
		if (partners.isEmpty()) {
			System.out.println("No delivery partners configured.");
			return;
		}

		System.out.println("\nRegistered Delivery Partners:");
		System.out.printf("%-5s | %-20s | %-15s%n", "ID", "Name", "Phone");
		System.out.println("-----------------------------------------------------");

		for (DeliveryPartner partner : partners) {
			System.out.printf("%-5d | %-20s | %-15s%n", partner.getId(), partner.getName(),
					partner.getPhone().isEmpty() ? "N/A" : partner.getPhone());
		}
	}

}
