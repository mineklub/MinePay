package dk.minepay.server.bukkit.classes;

/** Enum representing the status of a request. */
public enum RequestStatus {
    /** The request is pending. */
    pending,
    /** The request has been cancelled. */
    cancelled,
    /** The request has been accepted. */
    accepted;

    /**
     * Converts a string to a request status.
     *
     * @param status the string to convert
     * @return the request status
     */
    public static RequestStatus fromString(String status) {
        switch (status) {
            case "pending":
                return pending;
            case "cancelled":
                return cancelled;
            case "accepted":
                return accepted;
            default:
                return null;
        }
    }
}
