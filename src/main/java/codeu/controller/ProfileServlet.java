package codeu.controller;

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


        String username = (String)request.getSession().getAttribute("user");

        if (username == null) {
            // user is not logged in, don't let them see profile page
            response.sendRedirect("/login");
            return;
        }

        String userPic = userStore.getUser(username).getProfilePic();
        request.setAttribute("profilePic", userPic);

        String userBio = userStore.getUser(username).getUserBio();
        request.setAttribute("bio", userBio);


        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("user");
        User user = userStore.getUser(username);

        String bio = request.getParameter("bio");
        user.setUserBio(bio);
        userStore.updateUserData(user, "bio", bio);

        response.sendRedirect("/profile");
    }

}
