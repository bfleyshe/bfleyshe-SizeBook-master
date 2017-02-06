/*
 * Copyright (c) 2017. Created by bfleyshe for the purpose of CMPUT 301. May not be redistributed or used without permission.
 */

package com.example.bfleyshe.bfleyshe_sizebook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by bfleyshe on 2/1/17.
 */
public class Person implements Parcelable{

    private String name;
    private String date;
    private Float neck = 0f;
    private Float bust = 0f;
    private Float chest = 0f;
    private Float waist = 0f;
    private Float hip = 0f;
    private Float inseam = 0f;
    private String comment;

    /**
     * Instantiates a new Person with a Name.
     *
     * @param Name the name of the person
     */
    public Person(String Name) throws NameTooLongException{
        this.name = Name;
        setDate(new Date().toString());
    }

    /**
     * Gets name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the name of the person
     */
    public void setName(String name) throws NameTooLongException{
        if (name.length() > 30)  {
            throw new NameTooLongException();
        }
        if (name.length() < 1)  {
            throw new NameTooLongException();   // doesn't allow the user to enter an empty string, instead keeping whatever value is already there
        }
        this.name = name;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        if(date != null) {
            this.date = date;
        }
        else{
            this.date = "1/01/2017";
        }
    }

    /**
     * Gets neck circumference in inches.
     *
     * @return the neck circumference in inches
     */
    public Float getNeck() {
        return neck;
    }

    /**
     * Sets neck circumference in inches.
     *
     * @param neck measurement of circumference in inches
     */
    public void setNeck(Float neck) {
        this.neck = (float)Math.round(neck * 10) / 10;
    }

    /**
     * Gets bust circumference in inches.
     *
     * @return the bust circumference in inches
     */
    public Float getBust() {
        return bust;
    }

    /**
     * Sets bust circumference in inches.
     *
     * @param bust measurement of circumference in inches
     */
    public void setBust(Float bust) {
        this.bust =(float)Math.round(bust * 10) / 10;
    }

    /**
     * Gets chest circumference in inches.
     *
     * @return the chest circumference in inches
     */
    public Float getChest() {
        return chest;
    }

    /**
     * Sets chest circumference in inches.
     *
     * @param chest measurement of circumference in inches
     */
    public void setChest(Float chest) {
        this.chest = (float)Math.round(chest * 10) / 10;
    }

    /**
     * Gets waist measurement in inches.
     *
     * @return the waist circumference in inches
     */
    public Float getWaist() {
        return waist;
    }

    /**
     * Sets waist circumference in inches.
     *
     * @param waist measurement of circumference in inches
     */
    public void setWaist(Float waist) {
        this.waist = (float)Math.round(waist * 10) / 10;;
    }

    /**
     * Gets hip circumference in inches.
     *
     * @return the hip circumference in inches
     */
    public Float getHip() {
        return hip;
    }

    /**
     * Sets hip circumference.
     *
     * @param hip measurement of circumference in inches
     */
    public void setHip(Float hip) {
        this.hip = (float)Math.round(hip * 10) / 10;;
    }

    /**
     * Gets inseam measurement of length in inches.
     *
     * @return the inseam length in inches
     */
    public Float getInseam() {
        return inseam;
    }

    /**
     * Sets inseam length in inches.
     *
     * @param inseam measurement of length in inches
     */
    public void setInseam(Float inseam) {
        this.inseam = (float)Math.round(inseam * 10) / 10;;
    }

    /**
     * Gets comment about person.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment a comment about the person
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString(){
        return name;
    }


    //Passing parcel object for intent, gotten Feb 3rd, 17:27
    //http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents

    @Override
    public int describeContents() {
        return 0;
    }

    // write the object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getName());
        out.writeString(getDate());
        out.writeFloat(getNeck());
        out.writeFloat(getBust());
        out.writeFloat(getChest());
        out.writeFloat(getWaist());
        out.writeFloat(getHip());
        out.writeFloat(getInseam());
        out.writeString(getComment());
    }

    // this is used to regenerate the object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    //  constructor that takes a Parcel and gives an object populated with it's values
    private Person(Parcel in) {
        name = in.readString();
        date = in.readString();
        neck = in.readFloat();
        bust = in.readFloat();
        chest = in.readFloat();
        waist = in.readFloat();
        hip = in.readFloat();
        inseam = in.readFloat();
        comment = in.readString();
    }
}
