package ee.qrental.callsign.adapter.adapter;

import ee.qrental.callsign.adapter.mapper.CallSignLinkAdapterMapper;

import ee.qrental.callsign.adapter.repository.CallSignLinkRepository;
import ee.qrental.callsign.api.out.CallSignLinkAddPort;
import ee.qrental.callsign.api.out.CallSignLinkDeletePort;
import ee.qrental.callsign.api.out.CallSignLinkUpdatePort;
import ee.qrental.callsign.domain.CallSignLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkPersistenceAdapter
    implements CallSignLinkAddPort, CallSignLinkUpdatePort, CallSignLinkDeletePort {

    private final CallSignLinkRepository repository;
    private final CallSignLinkAdapterMapper mapper;

    @Override
    public CallSignLink add(final CallSignLink domain) {
        return mapper.mapToDomain(
                repository.save(
                        mapper.mapToEntity(domain)
                ));
    }

    @Override
    public CallSignLink update(final CallSignLink domain) {
        return mapper.mapToDomain(
                repository.save(
                        mapper.mapToEntity(domain)
                ));
    }

    @Override
    public void delete(final Long id) {
        repository.deleteById(id);
    }
}
