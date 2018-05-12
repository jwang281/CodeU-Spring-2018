package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import codeu.model.data.User;

/**
 *  This servlet is the base for the profile interaction. It requests and
 *  redirects commands to the profile JSP. Every helper servlet for the profile
 *  feature redirects to this end.
 *
 * */

public class ProfileServlet extends HttpServlet{

    private UserStore userStore;

    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
    }

    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

       String requestUrl = request.getRequestURI();
       String profileUserTitle = requestUrl.substring("/profile/".length());

       String username = (String)request.getSession().getAttribute("user");

        if (username == null) {
            // user is not logged in, don't let them see profile page
            response.sendRedirect("/login");
            return;
        }

        User currentUser = userStore.getUser(username); //Current user navigating the app

        User displayUser = userStore.getUser(profileUserTitle); //Display user to show profile
        String userPic = userStore.getUser(profileUserTitle).getProfilePic();

        request.setAttribute("profilePic", userPic);
        request.setAttribute("currentUser", currentUser);
        request.setAttribute("displayUser", displayUser);

        String userBio = userStore.getUser(profileUserTitle).getUserBio();
        request.setAttribute("bio", userBio);

        String userStatus = userStore.getUser(profileUserTitle).getUserStatus();
        request.setAttribute("status", userStatus);

        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("user");
        User user = userStore.getUser(username);
        String status = request.getParameter("status");
        String bio = request.getParameter("bio");

        if(status == null){
            user.setUserBio(bio);
            userStore.updateUserData(user, "bio", bio);}

        if(bio == null){
            user.setUserStatus(status);
            userStore.updateUserData(user, "status", status);}


        response.sendRedirect("/profile/" + username);
    }

}
