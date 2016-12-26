package com.teamtreehouse.wordbank.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.DecimalFormat;

@Entity
public class Country {
    @Id
    private String code;

    @Column
    private String name;

    @Column
    private Double internetUsers;

    @Column
    private Double adultLiteracyRate;

    public Country() {
    }

    Country(CountryBuilder builder) {
        this.code = builder.code;
        this.name = builder.name;
        this.internetUsers = builder.internetUsers;
        this.adultLiteracyRate = builder.literacy;
    }

    private String getId() {
        return code;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Double getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(Double internetUsers) {
        this.internetUsers = internetUsers;
    }

    public Double getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(Double literacy) {
        this.adultLiteracyRate = literacy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        return code != null ? code.equals(country.code) : country.code == null;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        String name = String.format("%1$-" + 30 + "s", this.name);
        DecimalFormat df = new DecimalFormat("##.##");

        String netUsers;
        if (this.internetUsers != null) {
            double internetUsers = this.internetUsers;
            netUsers = df.format(internetUsers);
        } else {
            netUsers = "--";
        }

        String literacy;
        if (this.adultLiteracyRate != null ){
            double adultLiteracyRate = this.adultLiteracyRate;
             literacy = df.format(adultLiteracyRate);
        } else {
            literacy = "--";
        }

        netUsers = String.format("%1$-" + 6 + "s", netUsers);
        literacy = String.format("%1$-" + 6 + "s", literacy);

        return getId() + " " + name +
                ", Internet Users = " + netUsers +
                ", literacy = " + literacy;
    }

    public static class CountryBuilder {
        private String code;
        private String name;
        private Double internetUsers;
        private Double literacy;

        public CountryBuilder(String name) {
            this.name = name;
            this.code = String.format("%s%s%s"
                    ,name.toUpperCase().charAt(0)
                    ,name.toUpperCase().charAt(1)
                    ,name.toUpperCase().charAt(2));
        }

        public CountryBuilder withInternetUsers(Double internetUsers) {
            this.internetUsers = internetUsers;
            return this;
        }

        public CountryBuilder withLiteracy(Double literacy) {
            this.literacy = literacy;
            return this;
        }

        public Country build() {
            return new Country(this);
        }

}   }
