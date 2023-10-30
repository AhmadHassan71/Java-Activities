package Bank;
public class Customer {

	private int customerId;
	private String name;
	private String address;
	private String phoneNumber;

	public Customer(int customerId, String name, String address, String phoneNumber) {
		this.customerId = customerId;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getName(){
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String phoneNumber() {
		return this.phoneNumber;
	}
	public int customerId() {
		return this.customerId;
	}

	public void setName(String Name) {
		this.name=Name;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

}
