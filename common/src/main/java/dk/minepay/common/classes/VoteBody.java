package dk.minepay.common.classes;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/** Vote body class. */
@Builder
@Getter
@AllArgsConstructor
public class VoteBody {
    private List<VoteStatus> statuses;
    private UUID mcaccount;

    /** Default constructor for VoteBody. */
    public VoteBody() {}
}
