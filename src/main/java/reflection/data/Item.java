package reflection.data;

import java.util.Date;

public class Item {
	private String name;
	private double price;
	private long quantity;
	private Date expiry;
	private Address address;
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public long getQuantity() {
		return quantity;
	}

	public Date getExpiry() {
		return expiry;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
}