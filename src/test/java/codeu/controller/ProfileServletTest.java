package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileServletTest {
    private ProfileServlet profileServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() throws IOException
    {
        profileServlet = new ProfileServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws IOException, ServletException
    {
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getName()).thenReturn("username_test");
        Mockito.when(mockUser.getProfilePic()).thenReturn("profile_pic");
        Mockito.when(mockUser.getUserBio()).thenReturn("bio");


        UserStore mockUserStore = Mockito.mock(UserStore.class);
        Mockito.when(mockUserStore.getUser("username_test")).thenReturn(mockUser);
        profileServlet.setUserStore(mockUserStore);

        HttpSession mockSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

        Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("username_test");

        profileServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}
