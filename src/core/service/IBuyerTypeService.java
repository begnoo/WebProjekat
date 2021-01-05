package core.service;

import core.domain.models.BuyerType;

public interface IBuyerTypeService extends ICrudService<BuyerType> {
	BuyerType getDefaultBuyerType();
	
	BuyerType findAppropriateTypeForPoints(int points);

}
