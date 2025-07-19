package dk.minepay.common.classes;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/** Request body class. */
@Builder
@Getter
@AllArgsConstructor
public class RequestBody {
    private List<RequestStatus> statuses;
    private List<RequestStatus> serverStatuses;

    /** Default constructor for RequestBody. */
    public RequestBody() {}
}
