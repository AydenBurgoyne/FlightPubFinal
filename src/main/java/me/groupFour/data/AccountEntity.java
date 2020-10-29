package me.groupFour.data;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//java bean for storing account. This is the model that will be stored using hibernate.
@Entity
@Table(name = "account")
public class AccountEntity {
    //variables that will be stored

    @Column(name = "first_name")
    @NotEmpty(message = "Please enter your first name")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Please enter your last name")
    private String lastName;
    @Id
    @Column(name = "email")
    @Email
    @Pattern(regexp = ".+@.+\\..+", message = "This email address is not valid")
    private String email;
    @NotEmpty(message = "Please enter your address")
    @Column(name = "address")
    private String address;
    @NotNull(message = "Please enter your date of birth")
    @Column(name = "DateOfBirth")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @NotNull(message = "Please select an option")
    @Column(name = "aType")
    private Boolean type; //if true == business if false == recreational
    @Column(name = "pword")
    @NotNull(message = "Please enter a password")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,10}$", message = "Please provide a valid password")
    private String pword;
    @Transient
    private String AccountRole;
    @OneToMany(mappedBy = "AccountID")
    private List<GroupBookingEntity>  BookingsList;
    private String status;
    @OneToOne(mappedBy = "accountID",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private AdminEntity admin;
    public String getStatus() {
        return status;
    }

    public String getAccountRole() {
        return AccountRole;
    }

    public void setAccountRole(String accountRole) {
        AccountRole = accountRole;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //    //might be redundant || not implemented
//    @ManyToMany
//    @JoinTable(
//            name = "wish_relation",
//            joinColumns = @JoinColumn(name = "accountID"),
//            inverseJoinColumns = @JoinColumn(name = "CountryCode3fk")
//    )
//    private List<CountryEntity> WishList;


//    private String wishListString;
//
//
//    private String wishListItem1;
//    private String wishListItem2;
//    private String wishListItem3;

    @OneToMany(mappedBy = "AccountID")
    private List<BookingEntity> BookingList;
    @OneToMany(mappedBy = "email")
    private List<TravelAgentWorker> TravelAgentID;
/*
    public AccountEntity(String firstName, String lastName, String email, String address, Date dateOfBirth, Boolean type, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.type = type;
//        this.pword = password;
//        this.wishListItem1 = wishListItem1;
//        this.wishListItem2 = wishListItem2;
//        this.wishListItem3 = wishListItem3;
//        this.wishListString = wishListItem1 + wishListItem2 + wishListItem3;
        //WishList = wishList;
        //BookingList = bookingList;
    }
*/
    public AccountEntity() {
    }
    public String getDate(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        String dates = formatter.format(dateOfBirth);
        return dates;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String password) {
        this.pword = password;
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin = admin;
    }

    public String encryptPassword(String password) {
        int strength = 10;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        return encoder.encode(password);
    }

//    public List<CountryEntity> getWishList() {
//        return WishList;
//    }
//
//    public void setWishList(List<CountryEntity> wishList) {
//        WishList = wishList;
//    }

    public List<BookingEntity> getBookingList() {
        return BookingList;
    }

    public void setBookingList(List<BookingEntity> bookingList) {
        BookingList = bookingList;
    }

//    public String getDummyWishList() {
//        return dummyWishList;
//    }
//
//    public void setDummyWishList(String dummyWishList) {
//        this.dummyWishList = dummyWishList;
//    }

//    public String getWishListItem1() {
//        return wishListItem1;
//    }
//
//    public void setWishListItem1(String wishListItem1) {
//        this.wishListItem1 = wishListItem1;
//    }
//
//    public String getWishListItem2() {
//        return wishListItem2;
//    }
//
//    public void setWishListItem2(String wishListItem2) {
//        this.wishListItem2 = wishListItem2;
//    }
//
//    public String getWishListItem3() {
//        return wishListItem3;
//    }
//
//    public void setWishListItem3(String wishListItem3) {
//        this.wishListItem3 = wishListItem3;
//    }
}

