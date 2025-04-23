package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createNewMessage(Message msg) {
        return this.messageDAO.createMessage(msg);
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return this.messageDAO.getMessageByID(id);
    }

    public Message deleteMessageById(int id) {
        return this.messageDAO.deleteMessageByID(id);
    }

    public Message updateMessageById(Message msg) {
        return this.messageDAO.updateMessageByID(msg);
    }

    public List<Message> getAllMessagesFromUser(int id) {
        return this.messageDAO.getAllMessagesFromUser(id);
    }
}
