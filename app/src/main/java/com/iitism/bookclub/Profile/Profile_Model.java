package com.iitism.bookclub.Profile;

public class Profile_Model
{
    String name,admin,branch,email,contact;

    public String getName() {
        return name;
    }

    public String getAdmin() {
        return admin;
    }

    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public Profile_Model(String name, String admin, String branch, String email, String contact) {
        this.name = name;
        this.admin = admin;
        this.branch = branch;
        this.email = email;
        this.contact = contact;
    }
}
