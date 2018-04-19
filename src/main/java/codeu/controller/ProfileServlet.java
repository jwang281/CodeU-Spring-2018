package codeu.controller;

import codeu.model.store.basic.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        String userPic = userStore.getUser(username).getProfilePic();

        request.setAttribute("profilePic", userPic);

        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);

    }

}
