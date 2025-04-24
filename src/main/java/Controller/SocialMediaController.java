package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static org.mockito.ArgumentMatchers.matches;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccount);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verified = accountService.userLogin(account);
        if(verified != null) {
            ctx.json(mapper.writeValueAsString(verified));
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message created = messageService.createNewMessage(msg);
        if(created != null) {
            ctx.json(mapper.writeValueAsString(created));
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesHandler(Context ctx) {
        List<Message> msgs = messageService.getAllMessages();
        ctx.json(msgs);
    }

    private void getMessageIdHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageById(id);
        if(msg != null) {
            ctx.json(msg);
        } else {
            ctx.status(200);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = messageService.deleteMessageById(id);
        if(message != null) {
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(200);
        }
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageById(id);
        if(msg != null) {
            msg = mapper.readValue(ctx.body(), Message.class);
            msg.setMessage_id(id);
            Message temp = messageService.updateMessageById(msg);
            if(temp != null) {
                ctx.json(mapper.writeValueAsString(temp));
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesByAccount(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> msgs = messageService.getAllMessagesFromUser(id);
        ctx.json(msgs);
    }
    //making sure it committed
}