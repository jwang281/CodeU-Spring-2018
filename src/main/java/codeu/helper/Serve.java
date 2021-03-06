package codeu.helper;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

/**
 *  This servlet handles the Blob key uploaded to the datastore passed
 *  by the Upload servlet. It retrieves the key and writes the url
 *  back into the User data for profile picture. Then it redirects to
 *  the ProfileServlet.
 * */

public class Serve extends HttpServlet {

    /** Store class that gives access to Users. */
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
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));


        String username = (String) req.getSession().getAttribute("user");

        User user = userStore.getUser(username);

        /**Get the image url and pass it to the User Object*/
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);
        String servingUrl = imagesService.getServingUrl(servingOptions);

        user.setProfilePic(servingUrl);

        userStore.updateUserData(user, "profile_pic", servingUrl);

        res.sendRedirect("/profile/" + username);
    }
}
