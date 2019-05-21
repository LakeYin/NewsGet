package com.main;

import java.util.Scanner;

public class GetNews {
    private static NewsManager newsManager = new NewsManager();

    private static TextSpeaker textSpeaker = null;

    private static Scanner reader = new Scanner(System.in);

    public static void main(String[] args){
        String storedName = "", storedPass = "";

        boolean loggedIn = false;
        boolean wantToLogout = false;
        boolean wantToQuit = false;

        try {
            textSpeaker = new TextSpeaker();
        } catch (java.lang.Exception e){
            System.out.println("Could not open text to speech");
            e.printStackTrace();
        }

        while (textSpeaker != null) {

            while (!loggedIn) {

                textSpeaker.speak("Welcome! \"login\", \"create\" a profile, or \"quit\".");

                switch (reader.nextLine().toLowerCase()) {

                    case ("login"): {
                        String[] login = promptCredentials();

                        if (newsManager.getIndex(login[0], login[1]) != -1) {
                            loggedIn = true;
                            storedName = login[0];
                            storedPass = login[1];
                        }

                        if (loggedIn == false)
                            textSpeaker.speak("Sorry, but we could not verify your account.");

                        break;
                    }

                    case ("create"): {
                        String create[];

                        while (true) {
                            create = promptCredentials();
                            if(newsManager.checkUniqueAccount(create[0]))
                                break;
                            else
                                textSpeaker.speak("Account name already exists");
                        }

                        newsManager.add(create[0], create[1]);

                        textSpeaker.speak("Profile created");

                        break;
                    }

                    case ("quit"):{
                        wantToQuit = true;

                        break;
                    }

                    default: {
                        textSpeaker.speak("Invalid input");

                        break;
                    }
                }

                if(wantToQuit)
                    break;
            }

            if(wantToQuit)
                break;

            while (!wantToLogout) {

                textSpeaker.speak("\nWould you like to \"add\" a topic, \"view\" your news, or \"logout\"?");

                switch (reader.nextLine().toLowerCase()) {
                    case ("add"):{
                        while(true) {
                            textSpeaker.speak("Enter a new one word topic: ");
                            String newTopic = reader.nextLine();
                            if(!newTopic.contains(" ")) {
                                newsManager.getProfile(newsManager.getIndex(storedName, storedPass)).addTopic(newTopic);
                                break;
                            }
                            else
                                textSpeaker.speak("Topic must be one word");
                        }

                        break;
                    }

                    case ("view"): {
                        textSpeaker.speak("Displaying your news feed\n");
                        String[][][] news = {{{}}};

                        try {
                            news = newsManager.getNews(storedName, storedPass);
                        } catch (java.lang.Exception e){
                            textSpeaker.speak("URL problem\n");
                        }

                        for (String[][] article : news){
                            for (String[] details : article){
                                if (details[0] != null) {
                                    // say the title
                                    textSpeaker.speak(details[0] + "\n");
                                    // display the link
                                    System.out.println(details[1] + "\n");
                                }
                            }
                        }

                        break;
                    }

                    case ("logout"):{
                        wantToLogout = true;
                        loggedIn = false;
                        storedName = "";
                        storedPass = "";
                        textSpeaker.speak("Successfully logged out");

                        break;
                    }

                    default:{
                        textSpeaker.speak("Invalid input");

                        break;
                    }
                }
            }
        }

        System.out.println("Thank you for using GetNews!");
    }

    private static String[] promptCredentials(){
        String[] nameAndPass = new String[2];
        textSpeaker.speak("Enter in your name: ");
        do {
            nameAndPass[0] = reader.nextLine();
        } while (nameAndPass[0].equals(""));

        textSpeaker.speak("Enter in your password: ");
        do {
            nameAndPass[1] = reader.nextLine();
        } while (nameAndPass[1].equals(""));

        return nameAndPass;
    }
}