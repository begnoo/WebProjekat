package services.utils.factories.creators;

import core.domain.models.BuyerType;
import core.repository.IRepository;
import core.service.IBuyerTypeService;
import core.service.IServiceCreator;
import repository.DbContext;
import repository.Repository;
import services.BuyerTypeService;

public class BuyerTypeServiceCreator implements IServiceCreator<IBuyerTypeService> {

	@Override
	public IBuyerTypeService create(DbContext context) {
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);

		return new BuyerTypeService(buyerTypeRepository);
	}

}
