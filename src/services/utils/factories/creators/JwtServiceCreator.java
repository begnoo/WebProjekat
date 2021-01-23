package services.utils.factories.creators;


import core.service.IJwtService;
import core.service.IServiceCreator;
import repository.DbContext;
import services.JwtService;

public class JwtServiceCreator implements IServiceCreator<IJwtService> {

	@Override
	public IJwtService create(DbContext context) {
		return new JwtService();
	}

}
