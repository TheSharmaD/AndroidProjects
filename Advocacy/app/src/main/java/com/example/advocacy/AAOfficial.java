package com.example.advocacy;

/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class AAOfficial {
    private String address_official;
    //String: to store the address of the official.
    private String position_official;
    //String: to store the position of the official.
    private String part_official;
    //String: to store the party of the official.
    private String email_official;
    //String: to store the email address of the official.
    private String website_official;
    //String: to store the website of the official.
    private String contact_official;
    //String: to store the contact information of the
    private String photoURL_official;
    //String: to store the URL of the photo of the official.
    private String name_official;
    //String: to store the name of the official.
    private String[] social_media = new String[3];
    //String[]: to store an array of social media links of the official.

    public AAOfficial(String po, String n, String pa, String ph, String em,
                      String addr, String web, String photo, String[] social) {
        social_media = social;
        setName_official(n);
        System.out.println("I'm here");
        setAddress_official(addr);
        System.out.println("I'm here");
        setEmail_official(em);
        System.out.println("I'm here");
        setWebsite_official(web);
        setPart_official(pa);
        System.out.println("I'm here");
        setPhotoURL_official(photo);
        setPosition_official(po);
        System.out.println("I'm here");
        setContact_official(ph);

    }
    //getters



    // to get the value of the website_of_the_official variable.
    public String getWebsite_official() {
        return website_official;
    }

    //to get the value of the position_of_the_official variable.
    public String getPosition_official() {
        return position_official;
    }

    // to get the value of the array_of_social_media variable.
    public String[] getSocialLink() {
        return social_media;
    }

    //to get the value of the part_of_the_official variable.
    public String getPart_official() {
        return part_official;
    }

    //to get the value of the photoURL_of_the_official variable.
    public String getPhotoURL_official() {
        return photoURL_official;
    }

    // to get the value of the name_of_the_official variable.
    public String getName_official() {
        return name_official;
    }

    // to get the value of the contact_of_the_official variable.
    public String getContact_official() {
        return contact_official;
    }

    //to get the value of the address_of_the_official variable.
    public String getAddress_official() {
        return address_official;
    }

    // to get the value of the email_of_the_official variable.
    public String getEmail_official() {
        return email_official;
    }


    //setters

    // to set the value of the photoURL_of_the_official variable.
    public void setPhotoURL_official(String photoURL_of_the_official) {
        System.out.println("Debugger at Officail");
        this.photoURL_official = photoURL_of_the_official;
    }

    //to set the value of the name_of_the_official variable.
    public void setName_official(String name_of_the_official) {
        System.out.println("Debugger at 1");
        this.name_official = name_of_the_official;
    }

    //to set the value of the contact_of_the_official variable.
    public void setContact_official(String contact_of_the_official) {
        System.out.println("Debugger at 2");
        this.contact_official = contact_of_the_official;
    }

    // to set the value of the address_of_the_official variable.
    public void setAddress_official(String address_of_the_official) {
        this.address_official = address_of_the_official;
        System.out.println("1 at 2");
    }

    //to set the value of the email_of_the_official variable.
    public void setEmail_official(String email_of_the_official) {
        this.email_official = email_of_the_official;
        System.out.println("Debugger at 3");
    }

    //to set the value of the website_of_the_official variable.
    public void setWebsite_official(String website_of_the_official) {
        System.out.println("Debugger at 8");
        this.website_official = website_of_the_official;
    }

    // to set the value of the position_of_the_official variable.
    public void setPosition_official(String position_of_the_official) {
        this.position_official = position_of_the_official;
    }

    //to set the value of the part_of_the_official variable.
    public void setPart_official(String part_of_the_official) {
        this.part_official = "(" + part_of_the_official + ")";
    }

    //This toString() method is overridden to return a string representation of the Official object.
    @Override
    public String toString() {
        return "Official{" +
                "position='" + position_official + '\'' +
                ", name='" + name_official + '\'' +
                ", party='" + part_official + '\'' +
                '}';
    }
}

