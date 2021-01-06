package services;

import java.util.List;
import java.util.stream.Collectors;

import core.domain.models.BuyerType;
import core.repository.IRepository;
import core.service.IBuyerTypeService;

public class BuyerTypeService extends CrudService<BuyerType> implements IBuyerTypeService {
	public BuyerTypeService(IRepository<BuyerType> repository)
	{
		super(repository);
	}

	@Override
	public BuyerType getDefaultBuyerType() {
		return repository.read()
						 .stream()
						 .filter(buyerType -> buyerType.getName().equals("Default"))
						 .collect(Collectors.toList()).get(0);
	}

	@Override
	public BuyerType findAppropriateTypeForPoints(int points) {
		List<BuyerType> buyerTypes = repository.read();
		buyerTypes.sort((buyerType1, buyerType2) -> buyerType1.getMinimumPoints() - buyerType2.getMinimumPoints());
		
		for(BuyerType buyerType : buyerTypes) {
			if(buyerType.getMinimumPoints() <= points) {
				return buyerType;
			}
		}
		
		return null;
	}
}
