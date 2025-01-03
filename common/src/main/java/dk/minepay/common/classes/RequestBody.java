package dk.minepay.common.classes;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/** Request body class. */
@Builder
@Getter
public class RequestBody {
    private List<RequestStatus> statuses;
    private List<RequestStatus> serverStatuses;
}
