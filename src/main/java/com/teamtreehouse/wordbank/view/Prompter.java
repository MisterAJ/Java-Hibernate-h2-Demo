package com.teamtreehouse.wordbank.view;


import com.teamtreehouse.wordbank.controller.DAO;
import com.teamtreehouse.wordbank.model.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.teamtreehouse.wordbank.controller.DAO.*;

public class Prompter {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void prompter() {
        int choice = 1;
        while (choice != 6){
            System.out.println("\n------------------\n" +
                    "\nEnter 1 to View Database" +
                    "\nEnter 2 to Edit Database" +
                    "\nEnter 3 to Add To the Database" +
                    "\nEnter 4 to Delete From Database" +
                    "\nEnter 5 to Print Stats" +
                    "\nEnter 6 to Quit.\n");
            choice = promptForNumber();
                switch (choice) {
                    case 1:
                        viewDatabase();
                        break;
                    case 2:
                        editDatabase();
                        break;
                    case 3:
                        newCountry();
                        break;
                    case 4:
                        deleteFromDatabase();
                        break;
                    case 5:
                        printStats();
                        break;
                    case 6:
                        System.out.println("NO NOT THAT BUTTON!!");
                        break;
                    default:
                        System.out.println("Invalid input");
                }
        }

        System.exit(0);
    }

    private void deleteFromDatabase() {
        viewDatabase();
        Country country = findById(prompt("Which Country would you like to delete? - Please use 3 digit Alpha code"));
        delete(country);
    }

    private void editDatabase() {
        viewDatabase();
        String code = prompt("What Country would you like to edit? - Please use 3 digit Alpha code");
        Country country = findById(code);
        int switchValue = 1;
        while (switchValue != 4) {
            System.out.println("\n------------------\n" +
                    "\nEnter 1 to Change Name" +
                    "\nEnter 2 to Change Internet Users Rate" +
                    "\nEnter 3 to Change Adult Literacy Rate" +
                    "\nEnter 4 to Quit\n");
            switchValue = promptForNumber();
            switch (switchValue) {
                case 1:
                    country.setName(prompt("Please enter new Name"));
                    break;
                case 2:
                    country.setInternetUsers(promptForDouble("Please Enter new percentage of Internet Users"));
                    break;
                case 3:
                    country.setAdultLiteracyRate(promptForDouble("Please Enter new Adult Literacy Rate"));
                    break;
                case 4:
                    DAO.update(country);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private void newCountry() {
        String name = prompt("What is the name of Country?");
        Double netUsers = promptForDouble("What percentage of people use internet?");
        Double literacy = promptForDouble("What percentage of adults are literate?");
        Country country = new Country.CountryBuilder(name)
                .withInternetUsers(netUsers)
                .withLiteracy(literacy)
                .build();
        DAO.save(country);
    }

    private void viewDatabase() {
        List<Country> countries = fetchAllContacts();
        System.out.println("ID - Country                       - Internet Users         - Adult Literacy Rate");
        for (Country country: countries) {
            System.out.println(country);
        }

    }

    private static String prompt(String promptString) {
        System.out.println(promptString);
        String input;
        try {
            input = br.readLine();
        }catch (IOException ioe) {
            throw new IllegalArgumentException("Hmm, That doesn't seem to fit", ioe);
        }
        return input;
    }

    private static int promptForNumber() {
        int output = 0;
        String input;
        Integer num = null;
        while (num == null) {
            System.out.println("----");
            try {
                input = br.readLine();
            } catch (IOException ioe) {
                throw new IllegalArgumentException("Hmm, That doesn't seem to fit", ioe);
            }
            if (input.matches("\\d")) {
                num = 1;
                output = Integer.parseInt(input);
            }
    }
        return output;
    }

    private static Double promptForDouble(String promptString) {
        Double output = 0.0;
        String input;
        Integer num = null;
        while (num == null) {
            System.out.println(promptString);
            try {
                input = br.readLine();
            } catch (IOException ioe) {
                throw new IllegalArgumentException("Hmm, That doesn't seem to fit", ioe);
            }
            if (input.matches("\\d+(\\.\\d{1,2})?")) {
                num = 1;
                output = Double.parseDouble(input);
            }
        }
        return output;
    }

}
