package dk.minepay.server.bukkit.classes;

/** Enum representing the status of a request. */
public enum RequestStatus {
    /** The request is pending. */
    pending,

    /** The request has been cancelled. */
    cancelled,

    /** The request has been accepted. */
    accepted
}
