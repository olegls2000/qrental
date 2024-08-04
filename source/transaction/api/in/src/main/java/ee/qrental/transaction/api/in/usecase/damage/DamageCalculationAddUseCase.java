package ee.qrental.transaction.api.in.usecase.damage;


import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;

public interface DamageCalculationAddUseCase {

  void add(final RentCalculationAddRequest request);
}
