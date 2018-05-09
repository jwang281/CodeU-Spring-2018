package codeu.helper;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *  This servlet handles the Blob upload to the datastore
 *  it does a lot of behind the scenes work but it basically
 *  takes the image file and transforms it into a blob with a
 *  unique blobKey. Then it redirects to the Serve servlet
 *  with the key to the uploaded image
 * */

public class UploadChat extends HttpServlet {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;

    /** Store class that gives access to Messages. */
    private MessageStore messageStore;

    /** Store class that gives access to Users. */
    private UserStore userStore;

    /** Set up state for handling chat requests. */
    @Override
    public void init() throws ServletException {
        super.init();
        setConversationStore(ConversationStore.getInstance());
        setMessageStore(MessageStore.getInstance());
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the ConversationStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }

    /**
     * Sets the MessageStore used by this servlet. This function provides a common setup method for
     * use by the test framework or the servlet's init() function.
     */
    void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String requestUrl = request.getRequestURI();
        String conversationTitle = requestUrl.substring("/uploadchat/".length());

        Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
        if (conversation == null) {
            // couldn't find conversation, redirect to conversation list
            System.out.println("Conversation was null: " + conversationTitle);
            response.sendRedirect("/conversations");
            return;
        }

        UUID conversationId = conversation.getId();

        List<Message> messages = messageStore.getMessagesInConversation(conversationId);

        request.setAttribute("conversation", conversation);
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestUrl = request.getRequestURI();
        String conversationTitle = requestUrl.substring("/uploadchat/".length());

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get("myFileFromChat");

        if (blobKeys == null || blobKeys.isEmpty()) {
            response.sendRedirect("/chat/" + conversationTitle);
        } else {

            String username = (String) request.getSession().getAttribute("user");
            if (username == null) {
                // user is not logged in, don't let them add a message
                response.sendRedirect("/login");
                return;
            }

            User user = userStore.getUser(username);
            if (user == null) {
                // user was not found, don't let them add a message
                response.sendRedirect("/login");
                return;
            }

            Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
            if (conversation == null) {
                // couldn't find conversation, redirect to conversation list
                response.sendRedirect("/conversations");
                return;
            }

            BlobKey blobKey = new BlobKey(blobKeys.get(0).getKeyString());

            /**Get the image url*/
            ImagesService imagesService = ImagesServiceFactory.getImagesService();
            ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);
            String servingUrl = imagesService.getServingUrl(servingOptions);

            Message message =
                    new Message(
                            UUID.randomUUID(),
                            conversation.getId(),
                            user.getId(),
                            servingUrl,
                            Instant.now());

            messageStore.addMessage(message);

            // redirect to a GET request
            response.sendRedirect("/chat/" + conversationTitle);
        }
    }
}
