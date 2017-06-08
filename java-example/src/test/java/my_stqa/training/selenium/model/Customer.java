package my_stqa.training.selenium.model;

public class Customer {
  private String taxId;
  private String firstname;
  private String lastname;
  private String address1;
  private String postcode;
  private String city;
  private String phone;

  public Customer(String taxId, String firstname, String lastname, String address1, String postcode, String city, String phone) {
    this.taxId = taxId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address1 = address1;
    this.postcode = postcode;
    this.city = city;
    this.phone = phone;
  }

  public Customer(Customer customer) {
  }

  public Customer() {

  }

  public Customer withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public Customer withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public Customer withTaxId(String taxId) {
    this.taxId = taxId;
    return this;
  }

  public Customer withAddress1(String address1) {
    this.address1 = address1;
    return this;
  }

  public Customer withPostcode(String postcode) {
    this.postcode = postcode;
    return this;
  }

  public Customer withCity(String city) {
    this.city = city;
    return this;
  }

  public Customer withPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getTaxId() {
    return taxId;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress1() {
    return address1;
  }

  public String getPostcode() {
    return postcode;
  }

  public String getCity() {
    return city;
  }

  public String getPhone() {
    return phone;
  }

}
