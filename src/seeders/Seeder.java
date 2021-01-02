package seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import core.domain.enums.Gender;
import core.domain.models.Address;
import core.domain.models.Administrator;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.Ticket;
import repository.DbContext;
import repository.Repository;
import repository.UserRepository;

public class Seeder {
	
	private DbContext context;
	
	public Seeder(DbContext context) {
		this.context = context;
	}
	
	public void run() {
		
		
		Repository<Location> locationRepository = new Repository<>(context, Location.class);
		Location location1 = new Location("45.2470805144055", "19.845354677817618",
				new Address("Sutjeska", "2", " Novi Sad", "21000"));
		Location location2 = new Location("45.245030232018622", "19.84960348355249",
				new Address("Dr Ilije Djuricica", "3", "Novi Sad", "402912"));
		Location location3 = new Location("44.81433635371563", "20.421267940534413",
				new Address("Bulevar Arsenija Carnojevica", " 58", "Beograd", "11070"));
		Location location4 = new Location("45.255476227223596", "9.861740782873927",
				new Address("Vinogradska", "47", "Novi Sad", "21132"));
		
		locationRepository.create(location1);
		locationRepository.create(location2);
		locationRepository.create(location3);
		locationRepository.create(location4);
		
		//TODO: za datume je potrebno koristiti DateTimeFormatter
		UserRepository userRepository = new UserRepository(context);
		
		Administrator admin1 = new Administrator("admin", "admin", "Mirko", "Mirkovic", Gender.Male, LocalDateTime.now());
		Administrator admin2 = new Administrator("djumbir", "cajodistog", "Djakic", "Markovic", Gender.Male, LocalDateTime.now());

		userRepository.create(admin1);
		userRepository.create(admin2);
		
		Repository<BuyerType> buyerTypeRepository = new Repository<>(context, BuyerType.class);
		BuyerType goldType = new BuyerType("Gold", 10, 2000);
		BuyerType silverType = new BuyerType("Silver", 5, 1500);
		BuyerType bronzeType = new BuyerType("Bronze", 3, 1000);
		BuyerType defaultType = new BuyerType("Default", 0, 0);
		
		goldType = buyerTypeRepository.create(goldType);
		silverType = buyerTypeRepository.create(silverType);
		bronzeType = buyerTypeRepository.create(bronzeType);
		defaultType = buyerTypeRepository.create(defaultType);
		
		Buyer buyer1 = new Buyer("paneze", "panic", "Milos", "Panic", Gender.Male,
				LocalDateTime.now(), new ArrayList<Ticket>(), 100, defaultType.getId(), defaultType);
		Buyer buyer2 = new Buyer("majoneze", "huawei_ruter", "Dalibor", "Malic", Gender.Male,
				LocalDateTime.now(), new ArrayList<Ticket>(), 1000, bronzeType.getId(), bronzeType);
		Buyer buyer3 = new Buyer("sljuxa", "markocar123", "Marko", "Suljak", Gender.Male,
				LocalDateTime.now(), new ArrayList<Ticket>(), 1800, silverType.getId(), silverType);
		Buyer buyer4 = new Buyer("nadxa", "harry_styles", "Nadezda", "Seratlic", Gender.Female,
				LocalDateTime.now(), new ArrayList<Ticket>(), 1800, goldType.getId(), goldType);
		Buyer buyer5 = new Buyer("zocalez", "patos", "Zoran", "Jankov", Gender.Male,
				LocalDateTime.now(), new ArrayList<Ticket>(), 500, defaultType.getId(), defaultType);
		
		userRepository.create(buyer1);
		userRepository.create(buyer2);
		userRepository.create(buyer3);
		userRepository.create(buyer4);
		userRepository.create(buyer5);
		
		Seller seller1 = new Seller("skc", "skc123", "Simfonije", "Gvozdimirovic",
				Gender.Male, LocalDateTime.now(), new ArrayList<Manifestation>());
		Seller seller2 = new Seller("spens", "spens021", "Kavkaz", "Majovic",
				Gender.Male, LocalDateTime.now(), new ArrayList<Manifestation>());
		Seller seller3 = new Seller("jeftin_exit", "exit021", "Glorija", "Petrov",
				Gender.Female, LocalDateTime.now(), new ArrayList<Manifestation>());
		
		userRepository.create(seller1);
		userRepository.create(seller2);
		userRepository.create(seller3);

	}
	
	
}
