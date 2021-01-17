package repository.utils.seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import core.domain.enums.CommentStatus;
import core.domain.enums.Gender;
import core.domain.enums.ManifestationType;
import core.domain.enums.TicketStatus;
import core.domain.enums.TicketType;
import core.domain.models.Address;
import core.domain.models.Administrator;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Comment;
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

		// Locations
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

		// BuyerTypes
		Repository<BuyerType> buyerTypeRepository = new Repository<>(context, BuyerType.class);
		BuyerType goldType = new BuyerType("Gold", 10, 2000);
		BuyerType silverType = new BuyerType("Silver", 5, 1500);
		BuyerType bronzeType = new BuyerType("Bronze", 3, 1000);
		BuyerType defaultType = new BuyerType("Default", 0, 0);

		goldType = buyerTypeRepository.create(goldType);
		silverType = buyerTypeRepository.create(silverType);
		bronzeType = buyerTypeRepository.create(bronzeType);
		defaultType = buyerTypeRepository.create(defaultType);

		// Administrators
		UserRepository userRepository = new UserRepository(context);

		Administrator admin1 = new Administrator("admin", "admin", "Mirko", "Mirkovic", Gender.Male,
				LocalDateTime.now());
		Administrator admin2 = new Administrator("djumbir", "cajodistog", "Djakic", "Markovic", Gender.Male,
				LocalDateTime.now());

		userRepository.create(admin1);
		userRepository.create(admin2);

		// Buyers
		Buyer buyer1 = new Buyer("paneze", "panic", "Milos", "Panic", Gender.Male, LocalDateTime.now().minusYears(21),
				new ArrayList<Ticket>(), 100, defaultType.getId(), defaultType);
		Buyer buyer2 = new Buyer("majoneze", "huawei_ruter", "Dalibor", "Malic", Gender.Male, LocalDateTime.now().minusYears(50),
				new ArrayList<Ticket>(), 1000, bronzeType.getId(), bronzeType);
		Buyer buyer3 = new Buyer("sljuxa", "markocar123", "Marko", "Suljak", Gender.Male, LocalDateTime.now().minusYears(13),
				new ArrayList<Ticket>(), 1800, silverType.getId(), silverType);
		Buyer buyer4 = new Buyer("nadxa", "harry_styles", "Nadezda", "Seratlic", Gender.Female, LocalDateTime.now().minusYears(90),
				new ArrayList<Ticket>(), 2800, goldType.getId(), goldType);
		Buyer buyer5 = new Buyer("zocalez", "patos", "Zoran", "Jankov", Gender.Male, LocalDateTime.now().minusYears(21),
				new ArrayList<Ticket>(), 500, defaultType.getId(), defaultType);

		userRepository.create(buyer1);
		userRepository.create(buyer2);
		userRepository.create(buyer3);
		userRepository.create(buyer4);
		userRepository.create(buyer5);

		// Sellers
		Seller seller1 = new Seller("skc", "skc123", "Simfonije", "Gvozdimirovic", Gender.Male, LocalDateTime.now().minusYears(21),
				new ArrayList<Manifestation>());
		Seller seller2 = new Seller("spens", "spens021", "Kavkaz", "Majovic", Gender.Male, LocalDateTime.now().minusYears(31),
				new ArrayList<Manifestation>());
		Seller seller3 = new Seller("jeftin_exit", "exit021", "Glorija", "Petrov", Gender.Female, LocalDateTime.now().minusYears(23),
				new ArrayList<Manifestation>());

		// Manifestations
		Repository<Manifestation> manifestationRepository = new Repository<>(context, Manifestation.class);

		Manifestation manifestation1 = new Manifestation("MF DOOM Memorial", ManifestationType.Concert, 5000,
				LocalDateTime.now().minusHours(3), LocalDateTime.now().plusMinutes(100), 1000, true, location1.getId(), location1,
				seller1.getId(), seller1, "../WebProjekat/rest/images/doom.jpg");
		Manifestation manifestation2 = new Manifestation("Bajaga i instuktori, previse godina",
				ManifestationType.Concert, 500, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusMinutes(20), 400, true,
				location2.getId(), location2, seller2.getId(), seller2, "../WebProjekat/rest/images/default.jpg");
		Manifestation manifestation3 = new Manifestation("Ujka Vanja", ManifestationType.Theater, 200,
				LocalDateTime.now(), LocalDateTime.now().plusMinutes(300), 800, true, location4.getId(), location4,
				seller2.getId(), seller2, "../WebProjekat/rest/images/default.jpg");
		Manifestation manifestation4 = new Manifestation("Exit Festival", ManifestationType.Festival, 10000,
				LocalDateTime.now().minusHours(33), LocalDateTime.now().plusMinutes(10), 1400, true, location3.getId(), location3,
				seller3.getId(), seller3, "../WebProjekat/rest/images/default.jpg");

		manifestationRepository.create(manifestation1);
		manifestationRepository.create(manifestation2);
		manifestationRepository.create(manifestation3);
		manifestationRepository.create(manifestation4);

		seller1.getManifestations().add(manifestation1);
		seller2.getManifestations().add(manifestation2);
		seller2.getManifestations().add(manifestation3);
		seller3.getManifestations().add(manifestation4);

		userRepository.create(seller1);
		userRepository.create(seller2);
		userRepository.create(seller3);

		// Tickets
		Repository<Ticket> ticketRepository = new Repository<>(context, Ticket.class);

		Ticket ticket1 = new Ticket("c8cENsRLNO", manifestation1.getId(), manifestation1, manifestation1.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer1.getId(), buyer1, TicketStatus.Reserved,
				TicketType.Regular);
		Ticket ticket2 = new Ticket("KeVXbpB9LW", manifestation3.getId(), manifestation3, manifestation3.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer2.getId(), buyer2, TicketStatus.Reserved,
				TicketType.Regular);
		Ticket ticket3 = new Ticket("gKane57z4T", manifestation3.getId(), manifestation3, manifestation3.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer3.getId(), buyer3, TicketStatus.Canceled,
				TicketType.Regular);
		Ticket ticket4 = new Ticket("8O6VymZCSg", manifestation2.getId(), manifestation2, manifestation2.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer3.getId(), buyer3, TicketStatus.Reserved,
				TicketType.Regular);
		Ticket ticket5 = new Ticket("rByJcHHPGk", manifestation4.getId(), manifestation4, manifestation4.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer4.getId(), buyer4, TicketStatus.Reserved,
				TicketType.Regular);
		Ticket ticket6 = new Ticket("UzZxSs156B", manifestation1.getId(), manifestation1, manifestation1.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer4.getId(), buyer4, TicketStatus.Reserved,
				TicketType.Regular);
		Ticket ticket7 = new Ticket("x8QY6WvhQk", manifestation2.getId(), manifestation2, manifestation2.getEventDate(),
				manifestation1.getRegularTicketPrice() * TicketType.FanPit.getModifier(), buyer4.getId(), buyer4,
				TicketStatus.Canceled, TicketType.FanPit);
		Ticket ticket8 = new Ticket("IC9aeB4E7j", manifestation3.getId(), manifestation3, manifestation3.getEventDate(),
				manifestation1.getRegularTicketPrice() * TicketType.Vip.getModifier(), buyer4.getId(), buyer4,
				TicketStatus.Reserved, TicketType.Vip);

		ticketRepository.create(ticket1);
		ticketRepository.create(ticket2);
		ticketRepository.create(ticket3);
		ticketRepository.create(ticket4);
		ticketRepository.create(ticket5);
		ticketRepository.create(ticket6);
		ticketRepository.create(ticket7);
		ticketRepository.create(ticket8);

		Repository<Comment> commentRepository = new Repository<>(context, Comment.class);

		Comment comment1 = new Comment(buyer1.getId(), buyer1, manifestation1.getId(), manifestation1,
				"It was pretty good", 4, CommentStatus.Approved);
		Comment comment2 = new Comment(buyer2.getId(), buyer2, manifestation3.getId(), manifestation3,
				"Could have been better, but still alright", 3, CommentStatus.Approved);
		Comment comment3 = new Comment(buyer3.getId(), buyer3, manifestation2.getId(), manifestation2,
				"It was pretty good", 1, CommentStatus.Approved);
		Comment comment4 = new Comment(buyer4.getId(), buyer4, manifestation4.getId(), manifestation4,
				"Best festival I've been to!!!", 5, CommentStatus.Approved);
		Comment comment5 = new Comment(buyer1.getId(), buyer3, manifestation2.getId(), manifestation2,
				"The absoulte worst thing I have ever seen", 1, CommentStatus.Refused);

		commentRepository.create(comment1);
		commentRepository.create(comment2);
		commentRepository.create(comment3);
		commentRepository.create(comment4);
		commentRepository.create(comment5);

	}

}
