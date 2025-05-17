package message;

import data.Pair;

import java.util.List;

public class StartMessage extends Message {

    private final List<Pair> diceList;

    public StartMessage(List<Pair> diceList) {
        super(MessageType.START);
        this.diceList = diceList;
    }

    public List<Pair> getDiceList() {
        return diceList;
    }
}
