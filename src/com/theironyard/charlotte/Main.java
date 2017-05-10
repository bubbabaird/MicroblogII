package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {
    // currently logged in user to display in html
    static HashMap<String, User> users = new HashMap<>();
    //static User user;
    // multiple messages
    // Create a user

    // create a message

    // People need to be able to add a message
    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    // and try to find the currently-logged-in user from session immediately after.
                    // this will be YOUR username after you login, for you and you only.
                    String name = session.attribute("userName");
                    User user = users.get(name);

                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", user.name);
                            // Doesn't this \/ need to be inserted into the messages arrayList?
                            // It looks like it's being inserted into the m hashMap as a string?
                        m.put("notes", user.messages);
                        return new ModelAndView(m, "home.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name);
                        users.put(name, user);
                    }

                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");
                    return "";
                })
        );
//        ;Spark.get(
//                "/",
//                ((request, response) -> {
//                    // create a hashMap (userNames and messages)
//                    HashMap m = new HashMap();
//                    if (user == null) {
//                        //m.put()
//                        //m.put("pajamas", "HelloOo");
//                        return new ModelAndView(m, "index.html");
//                    } else {
//                        m.put("name", user.name);
//                        m.put("notes", messages);
//                        return new ModelAndView(m, "home.html");
//                    }
//                }),
//                new MustacheTemplateEngine()
//        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name);
                        users.put(name,user);
                    }
                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");
                    return"";
                })
        );
//        Spark.post(
//                "/create-user",
//                ((request, response) -> {
//                    String name = request.queryParams("loginName");
//                    // (name) is the parameter that's expected from the index.html form value
//                    // from loginName.
//                    user = new User(name);
//                    response.redirect("/");
//                    return "";
//                })
//        );
        Spark.post(
                //create an end point to create a new message
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }

                    String message = request.queryParams("message-text");

                    //convert a string into a new message object
                    Message m = new Message(message);

                    user.messages.add(m);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );
    }
}
// names, userNames


// Add multi-user support by storing your users in a HashMap<String, User> and
// putting your ArrayList<Message> inside the User object.
// In the /create-user route, save the username into the session. In the / route, get
// the username out of the session and subsequently get your User object.
// When the homepage is shown, only show the messages belonging to that specific user.