package ee.qrental.transaction.api.in.usecase.rent;


import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;

public interface RentCalculationAddUseCase {

  void add(final RentCalculationAddRequest request);
}
