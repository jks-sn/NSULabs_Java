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
        if ((participants.isEmpty() || ! usernameIsRelevant(participant)) &&
            (participants.isEmpty() || ! idIsRelevant(participantID))) {
            participants.put(participantID, participant);
        }
        else throw new DuplicateNameException(USER_ALREADY_CONNECTED_MESSAGE);
    }

    public void removeParticipant(Integer participantID) throws DuplicateNameException {
        if (idIsRelevant(participantID)) {
            participants.remove(participantID);
        }
        else throw new DuplicateNameException(USER_NO_NAME_ALREADY_MESSAGE);
    }

    public ArrayList<String> getPrintableParticipantsNames() {
        return new ArrayList<>(participants.values());
    }

    public String getNameByID(Integer id) throws DuplicateNameException {
        if (! idIsRelevant(id)) throw new DuplicateNameException(USER_NO_ID_ALREADY_MESSAGE);
        return participants.get(id);
    }

    private boolean idIsRelevant(Integer id) {
        return participants.containsKey(id);
    }

    private boolean usernameIsRelevant(String username) {
        for (String participant : participants.values()) {
            if (participant.equals(username)) return true;
        }
        return false;
    }
}
