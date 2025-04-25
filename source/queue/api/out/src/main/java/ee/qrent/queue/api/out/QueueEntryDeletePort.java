package ee.qrent.queue.api.out;

import ee.qrent.common.out.port.DeletePort;

import java.util.Set;

public interface QueueEntryDeletePort extends DeletePort {
    void deleteAll(final Set<Long> ids);
}
