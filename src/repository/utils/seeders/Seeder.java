package repository.utils.seeders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
		Location location2 = new Location("45.25318675266331", "19.84244351316218",
				new Address("Narodnih heroja", "3", "Novi Sad", "21000"));
		Location location3 = new Location("44.81433635371563", "20.421267940534413",
				new Address("Bulevar Arsenija Carnojevica", " 58", "Beograd", "11000"));
		Location location4 = new Location("45.255476227223596", "16.861740782873927",
				new Address("Cara Dusana", "8", "Beograd", "11000"));
		Location location5 = new Location("45.25232069530534", "19.864217762093517",
				new Address("Cajkovskog", "8", "Novi Sad", "21000"));
		Location location6 = new Location("45.24467634915695", "19.847757594244282",
				new Address("Doktora Ilije Djuricica", "11", "Novi Sad", "21000"));

		locationRepository.create(location1);
		locationRepository.create(location2);
		locationRepository.create(location3);
		locationRepository.create(location4);
		locationRepository.create(location5);
		locationRepository.create(location6);



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

		Administrator admin1 = new Administrator("admin", "673ad054dc93970e2bd9e779c34a55df45fc0a323a3535f2788cba758a82c34c", "iipgjsvlhr", "Mirko", "Mirkovic", Gender.Male,
				LocalDateTime.now());
		Administrator admin2 = new Administrator("djumbir", "ea8d2ec0a4ccb37282bafc7684213a036fefba00a751f306d77496467da1cf72", "ikyqpkzorg", "Djakic", "Markovic", Gender.Male,
				LocalDateTime.now());

		userRepository.create(admin1);
		userRepository.create(admin2);

		// Buyers
		Buyer buyer1 = new Buyer("paneze", "3c403feaa682d4187c0d305e1b4fc41bfeb86375898e1f5820875c60774d4c87", "npnhgcjtqd", "Milos", "Panic", Gender.Male, LocalDateTime.now().minusYears(21),
				new ArrayList<Ticket>(), 100, defaultType.getId(), defaultType);
		Buyer buyer2 = new Buyer("majoneze", "f769c72cccca176947dd0a95da60912e2b1665d1a1be12f2632b23ffde463643", "yyjzmhgnsc", "Dalibor", "Malic", Gender.Male, LocalDateTime.now().minusYears(50),
				new ArrayList<Ticket>(), 950, bronzeType.getId(), bronzeType);
		Buyer buyer3 = new Buyer("sljuxa", "380bcdb605215810a942bc3276469b3efd03def53f93570d8f4850983a6bdb03", "mhvahaapiy", "Marko", "Suljak", Gender.Male, LocalDateTime.now().minusYears(13),
				new ArrayList<Ticket>(), 1600, silverType.getId(), silverType);
		Buyer buyer4 = new Buyer("nadxa", "e5f449ea84b08e6cdd236a83d0b37fba2255cd4dc32548bfa8576d72dc904d7f", "qjabyspexs", "Nadezda", "Seratlic", Gender.Female, LocalDateTime.now().minusYears(90),
				new ArrayList<Ticket>(), 2800, goldType.getId(), goldType);
		Buyer buyer5 = new Buyer("zocalez", "ec49cebd3de09ae74e737eff274ed658ba6438ef78a99e42e1225cf2b3599737", "eqzafpzyqj", "Zoran", "Jankov", Gender.Male, LocalDateTime.now().minusYears(21),
				new ArrayList<Ticket>(), 500, defaultType.getId(), defaultType);

		userRepository.create(buyer1);
		userRepository.create(buyer2);
		userRepository.create(buyer3);
		userRepository.create(buyer4);
		userRepository.create(buyer5);

		// Sellers
		Seller seller1 = new Seller("skc", "e741ee26d2cd55ef0dcb1351b85c9e9b44269c2bdd1b4a833b63e94270803b7e", "gttslyzgkm", "Simfonije", "Gvozdimirovic", Gender.Male, LocalDateTime.now().minusYears(21),
				new ArrayList<Manifestation>());
		Seller seller2 = new Seller("spens", "8dad5cbe06d9a9776e60832132a19ed3851c834faa7a31c25f2ab391c7d4f10d", "mmtarvnpdo", "Kavkaz", "Majovic", Gender.Male, LocalDateTime.now().minusYears(31),
				new ArrayList<Manifestation>());
		Seller seller3 = new Seller("jeftin_exit", "8bd709c3a1ef5dc35ef5373061603c0a438a1d16415773145e8d6b61d8c39da8", "olsomuenjh", "Glorija", "Petrov", Gender.Female, LocalDateTime.now().minusYears(23),
				new ArrayList<Manifestation>());

		// Manifestations
		Repository<Manifestation> manifestationRepository = new Repository<>(context, Manifestation.class);

		Manifestation manifestation1 = new Manifestation("MF DOOM Memorial", ManifestationType.Concert, 5000,
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 8), LocalTime.of(20, 0)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 9), LocalTime.of(2, 0)),
				1000, true, location1.getId(), location1,
				seller1.getId(), seller1, "../WebProjekat/rest/images/doom.jpg");
		Manifestation manifestation2 = new Manifestation("Bajaga i instuktori, previse godina",
				ManifestationType.Concert, 500, 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 10), LocalTime.of(18, 0)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 10), LocalTime.of(23, 0)),
				400, true, location3.getId(), location3, seller2.getId(), seller2, "../WebProjekat/rest/images/bajaga.jpg");
		Manifestation manifestation3 = new Manifestation("Ujka Vanja", ManifestationType.Theater, 200,
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 5), LocalTime.of(20, 0)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 5), LocalTime.of(21, 0)),
				800, true, location4.getId(), location4,
				seller2.getId(), seller2, "../WebProjekat/rest/images/ujka_vanja.jpg");
		Manifestation manifestation4 = new Manifestation("Exit Festival", ManifestationType.Festival, 50000,
				LocalDateTime.of(LocalDate.of(2020, Month.JULY, 5), LocalTime.of(19, 0)), 
				LocalDateTime.of(LocalDate.of(2020, Month.JULY, 10), LocalTime.of(21, 0)),
				5000, true, location5.getId(), location5,
				seller3.getId(), seller3, "../WebProjekat/rest/images/exit.png");
		Manifestation manifestation5 = new Manifestation("Mastodon in Belgrade", ManifestationType.Concert, 1,
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 13), LocalTime.of(19, 45)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 14), LocalTime.of(1, 30)),
				4000, true, location4.getId(), location4,
				seller3.getId(), seller3, "../WebProjekat/rest/images/mastodon.jpg");
		Manifestation manifestation6 = new Manifestation("Hamlet", ManifestationType.Theater, 1000,
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 15), LocalTime.of(19, 15)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 15), LocalTime.of(22, 15)),
				1500, false, location1.getId(), location1,
				seller1.getId(), seller1, "../WebProjekat/rest/images/hamlet.jpg");
		Manifestation manifestation7 = new Manifestation("Fesival kulinarstva", ManifestationType.Festival, 1000,
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 17), LocalTime.of(13, 0)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 20), LocalTime.of(13, 0)),
				1500, false, location3.getId(), location3,
				seller2.getId(), seller2, "../WebProjekat/rest/images/default.jpg");
		Manifestation manifestation8 = new Manifestation("Odbrana projekta", ManifestationType.Festival, 1000,
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 6), LocalTime.of(12, 0)), 
				LocalDateTime.of(LocalDate.of(2021, Month.FEBRUARY, 6), LocalTime.of(15, 0)),
				1500, false, location6.getId(), location6,
				seller1.getId(), seller1, "../WebProjekat/rest/images/default.jpg");

		manifestationRepository.create(manifestation1);
		manifestationRepository.create(manifestation2);
		manifestationRepository.create(manifestation3);
		manifestationRepository.create(manifestation4);
		manifestationRepository.create(manifestation5);
		manifestationRepository.create(manifestation6);
		manifestationRepository.create(manifestation7);
		manifestationRepository.create(manifestation8);


		seller1.getManifestations().add(manifestation1);
		seller2.getManifestations().add(manifestation2);
		seller2.getManifestations().add(manifestation3);
		seller3.getManifestations().add(manifestation4);
		seller1.getManifestations().add(manifestation5);

		
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
		Ticket ticket9 = new Ticket("KLyGfH99Gk", manifestation4.getId(), manifestation4, manifestation4.getEventDate(),
				manifestation1.getRegularTicketPrice(), buyer1.getId(), buyer1, TicketStatus.Reserved,
				TicketType.Regular);

		ticketRepository.create(ticket1);
		ticketRepository.create(ticket2);
		ticketRepository.create(ticket3);
		ticketRepository.create(ticket4);
		ticketRepository.create(ticket5);
		ticketRepository.create(ticket6);
		ticketRepository.create(ticket7);
		ticketRepository.create(ticket8);
		ticketRepository.create(ticket9);

		Repository<Comment> commentRepository = new Repository<>(context, Comment.class);

		Comment comment1 = new Comment(buyer1.getId(), buyer1, manifestation1.getId(), manifestation1,
				"It was pretty good", 4, CommentStatus.Approved);
		Comment comment2 = new Comment(buyer2.getId(), buyer2, manifestation3.getId(), manifestation3,
				"Could have been better, but still alright", 3, CommentStatus.Approved);
		Comment comment3 = new Comment(buyer3.getId(), buyer3, manifestation2.getId(), manifestation2,
				"It was pretty good, i just didn't enjoy the crowd", 4, CommentStatus.Approved);
		Comment comment4 = new Comment(buyer4.getId(), buyer4, manifestation4.getId(), manifestation4,
				"Best festival I've been to!!!", 5, CommentStatus.Approved);
		Comment comment5 = new Comment(buyer3.getId(), buyer3, manifestation2.getId(), manifestation2,
				"The absoulte worst thing I have ever seen", 1, CommentStatus.Refused);
		Comment comment6 = new Comment(buyer1.getId(), buyer1, manifestation4.getId(), manifestation4,
				"I should have never let my friends convince me to go", 2, CommentStatus.Approved);

		commentRepository.create(comment1);
		commentRepository.create(comment2);
		commentRepository.create(comment3);
		commentRepository.create(comment4);
		commentRepository.create(comment5);
		commentRepository.create(comment6);


	}

}
