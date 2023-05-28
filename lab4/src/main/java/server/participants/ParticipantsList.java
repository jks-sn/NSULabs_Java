package server.participants;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.DuplicateNameException;

import static constants.ErrorConstants.*;

public class ParticipantsList {

    private final HashMap<Integer, String> participants;

    public ParticipantsList() {
        participants = new HashMap<>();
    }

    public void addNewParcipiant(String participant, Integer participantID) throws DuplicateNameException {
        if ((participants.size() == 0 || ! usernameIsRelevant(participant)) && 
            (participants.size() == 0 || ! idIsRelevant(participantID))) {
            participants.put(participantID, participant);
        }
        else throw new DuplicateNameException(userAlreadyConnectedMessage);
    }

    public void removeParticipant(Integer participantID) throws DuplicateNameException {
        if (idIsRelevant(participantID)) {
            participants.remove(participantID);
        }
        else throw new DuplicateNameException(userNoNameAlreadyMessage);
    }

    public ArrayList<String> getPrintableParticipantsNames() {
        return new ArrayList<>(participants.values());
    }

    public String getNameByID(Integer id) throws DuplicateNameException {
        if (! idIsRelevant(id)) throw new DuplicateNameException(userNoIDAlreadyMessage);
        return participants.get(id);
    }

    private boolean idIsRelevant(Integer id) {
        return participants.containsKey(id);
    }

    private boolean usernameIsRelevant(String username) {
        for (String p : participants.values()) {
            if (p.equals(username)) return true;
        }
        return false;
    }
}
