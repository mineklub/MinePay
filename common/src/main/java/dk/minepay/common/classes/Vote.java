package dk.minepay.common.classes;

import java.util.UUID;
import lombok.Getter;

/** Class that represents a vote. */
@Getter
public class Vote {
    private String _id;
    private String uuid;
    private VoteStatus status;

    /** Constructor for Vote. */
    public Vote() {}

    /**
     * Constructor for Vote.
     *
     * @param _id the unique identifier of the vote
     * @param uuid the uuid of the player that voted
     * @param status the status of the vote
     */
    public Vote(String _id, String uuid, VoteStatus status) {
        this._id = _id;
        this.uuid = uuid;
        this.status = status;
    }

    /**
     * Get the uuid of the player that voted.
     *
     * @return the uuid of the player that voted
     */
    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    @Override
    public String toString() {
        return "Vote{"
                + "_id='"
                + _id
                + '\''
                + ", uuid='"
                + uuid
                + '\''
                + ", status='"
                + status
                + '\''
                + '}';
    }
}
