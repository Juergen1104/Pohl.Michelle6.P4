package message;

public class DisconnectMessage extends Message{
	private static final long serialVersionUID = 1L;
	
	public DisconnectMessage() {
		super(MessageType.DISCONNECT);
	}
	
}