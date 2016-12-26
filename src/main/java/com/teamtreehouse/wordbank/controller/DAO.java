package com.teamtreehouse.wordbank.controller;


import com.teamtreehouse.wordbank.model.Country;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DAO {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @SuppressWarnings("unchecked")
    public static List<Country> fetchAllContacts() {
        List<Country> countries;
        try (Session session = sessionFactory.openSession()) {
            // Create Criteria
            Criteria criteria = session.createCriteria(Country.class);
            countries = criteria.list();
        }
        return countries;
    }

    public static void save(Country country) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(country);

        session.getTransaction().commit();

        session.close();
    }

    public static void update(Country country) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.update(country);

        session.getTransaction().commit();

        session.close();
    }

    public static void delete(Country country) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(country);

        session.getTransaction().commit();

        session.close();
    }

    public static Country findById(String code) {
        Session session = sessionFactory.openSession();

        Country country = session.get(Country.class,code);

        session.close();

        return country;
    }

    public static void printStats() {
        Double minNetUsers = 99.0;
        Double maxNetUsers = 0.0;
        Double minLiteracy = 99.0;
        Double maxLiteracy = 0.0;
        Double averageNetUsers;
        Double averageLiteracy;
        Double totalNetUsers = 0.0;
        Double totalLiteracy = 0.0;
        Double totalNetUsersTimesLiteracy = 0.0;
        Double totalNetUsersSqr = 0.0;
        Double totalLiteracySqr = 0.0;
        int count = 0;
        List<Country> countries = fetchAllContacts();

        for (Country country: countries) {
            Double netUsers = country.getInternetUsers();
            Double literacy = country.getAdultLiteracyRate();
            count ++;

            if (literacy != null && netUsers!= null ) {
                totalNetUsersTimesLiteracy = totalNetUsersTimesLiteracy + (netUsers * literacy);
                totalNetUsersSqr = totalNetUsersSqr + (netUsers * netUsers);
                totalLiteracySqr = totalLiteracySqr + (literacy * literacy);
            }

            // Calculate Net User Stats
            if (netUsers != null){
                if (netUsers > maxNetUsers) {
                    maxNetUsers = country.getInternetUsers();
                }

                if (netUsers < minNetUsers) {
                    minNetUsers = country.getInternetUsers();
                }
            } else {
                netUsers = 50.00;
            }
            totalNetUsers = totalNetUsers + netUsers;

            // Calculate Literacy Stats
            if (literacy != null){
                if (literacy > maxLiteracy) {
                    maxLiteracy = country.getAdultLiteracyRate();
                }

                if (literacy < minLiteracy) {
                    minLiteracy = country.getAdultLiteracyRate();
                }
            } else {
                literacy = 50.00;
            }
            totalLiteracy = totalLiteracy + literacy;


        }

        // Coefficient Calculation
        Double valueOne = count * totalNetUsersTimesLiteracy - (totalNetUsers * totalLiteracy);
        Double valueTwo = (count * totalNetUsersSqr - (totalNetUsers * totalNetUsers)) *
                            (count * totalLiteracySqr - (totalLiteracy * totalLiteracy));

        Double coefficient = valueOne / Math.sqrt(valueTwo);

        averageNetUsers = totalNetUsers / count;
        averageLiteracy = totalLiteracy / count;

        System.out.printf("\nLowest Internet Users : %s", minNetUsers);
        System.out.printf("\nHighest Internet Users : %s", maxNetUsers);
        System.out.printf("\nAverage Internet Users : %s", averageNetUsers);
        System.out.printf("\nLowest Adult Literacy Rate : %s", minLiteracy);
        System.out.printf("\nHighest Adult Literacy Rate : %s", maxLiteracy);
        System.out.printf("\nAverage Adult Literacy Rate : %s", averageLiteracy);
        System.out.printf("\nThe Correlation Coefficient is %s", coefficient);
    }
}
