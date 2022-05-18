package com.epam.training.sportsbetting;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppSpring {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        App app = context.getBean(App.class);
        app.play();
    }
}
