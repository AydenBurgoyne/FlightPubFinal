package me.groupFour.data;

import javax.persistence.*;

@Entity
@Table(name="admintable")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int adminID;
    @OneToOne()
    @JoinColumn(name="email")
    AccountEntity accountID;
    String Description;

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public AccountEntity getAccountID() {
        return accountID;
    }

    public void setAccountID(AccountEntity accountID) {
        this.accountID = accountID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
