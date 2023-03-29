package ee.qrental.common.core.out.mapper;

public interface DomainMapper<D, E> {
  D mapToDomain(final E jpaEntity);

  E mapToEntity(final D domain);
}
