package ee.qrent.common.in.query;

import java.util.List;

public interface BaseGetQuery<U, R> {

  List<R> getAll();

  R getById(final Long id);

  String getObjectInfo(final Long id);

  U getUpdateRequestById(final Long id);
}
