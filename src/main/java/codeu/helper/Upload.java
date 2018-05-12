package codeu.helper;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  This servlet handles the Blob upload to the datastore
 *  it does a lot of behind the scenes work but it basically
 *  takes the image file and transforms it into a blob with a
 *  unique blobKey. Then it redirects to the Serve servlet
 *  with the key to the uploaded image
 * */

public class Upload extends HttpServlet{

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
            List<BlobKey> blobKeys = blobs.get("myFile");

            String username = (String)request.getSession().getAttribute("user");

            if (blobKeys == null || blobKeys.isEmpty()) {
                response.sendRedirect("/profile/" + username);
            } else {
                response.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
            }
        }
}
