package org.example.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class SessionFact {
    ;
    private static SessionFactory sessionFactory;

    private SessionFact(){
    }

    public static SessionFactory getFactory(){
        if(sessionFactory==null)
            return buildSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        return new MetadataSources(new StandardServiceRegistryBuilder().configure().build()).buildMetadata().buildSessionFactory();
    }

    public static void closeFactory(){
        if(sessionFactory!=null)
            sessionFactory.close();
    }
}
