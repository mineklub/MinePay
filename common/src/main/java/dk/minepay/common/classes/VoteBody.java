package dk.minepay.common.classes;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/** Vote body class. */
@Builder
@Getter
public class VoteBody {
    private List<VoteStatus> statuses;
    private UUID mcaccount;
}
