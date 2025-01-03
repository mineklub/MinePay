package dk.minepay.common.classes;

/** Enum representing the status of a vote. */
public enum VoteStatus {
    /** The vote is pending. */
    pending,
    /** The vote has been cancelled. */
    accepted;

    /**
     * Converts a string to a request status.
     *
     * @param status the string to convert
     * @return the request status
     */
    public static VoteStatus fromString(String status) {
        switch (status) {
            case "pending":
                return pending;
            case "accepted":
                return accepted;
            default:
                return null;
        }
    }
}
