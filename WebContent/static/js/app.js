const Login = { template: "<login-form></login-form>" };
const RegisterPage = { template: "<registration-page></registration-page>" };
const ChangePassword = { template: "<change-password-form></change-password-form>" };
const Comment = { template: "<comment-form></comment-form>" };
const CommentsBigTable = { template: "<comments-big-table></comments-big-table>" };
const CommentsPage = { template: "<comments-page></comments-page>" };
const LocationsPage = { template: "<locations-page></locations-page>" };
const Users = { template: "<users-page></users-page>" };
const HomePage = {template: "<home-page></home-page>"};
const ManifestationPage = {template: "<manifestation-page></manifestation-page>"};
const ManifestationsPage = {template: "<manifestations-page></manifestations-page>"};
const AccountPage = {template: "<account-page></account-page>"};
const OrderTable = {template: "<order-table></order-table>"};
const BuyerTicketsPage = {template: "<buyer-tickets-page></buyer-tickets-page>"};
const UserTicketsPage = {template: "<user-tickets-page></user-tickets-page>"};
const DistrustfulBuyersPage = {template: "<distrustful-buyers-page></distrustful-buyers-page>"};
const MyCommentsPage = {template: "<my-comments-page></my-comments-page>"};



const router = new VueRouter({
    mode: "hash",
    routes: [
		{ path: "/", component: HomePage },
        { path: "/login", component: Login },
        { path: "/register", component: RegisterPage },
        { path: "/locations", component: LocationsPage },
        { path: "/users", component: Users },
		{ path: "/manifestations/:id", component: ManifestationPage },
		{ path: "/manifestations/:id/comment", component: Comment },
		{ path: "/cart", component: OrderTable },
		{ path: "/buyer-tickets/", component: BuyerTicketsPage },
		{ path: "/buyer-tickets/:id", component: UserTicketsPage },
		{ path: "/distrustful-buyers/", component: DistrustfulBuyersPage },
		{ path: "/change-password", component: ChangePassword },
		{ path: "/account", component: AccountPage },
		{ path: "/my-comments", component: MyCommentsPage },
		{ path: "/manifestations", component: ManifestationsPage },

    ],
});

const routeAllowedRoles = {
	"": ["Administrator", "Buyer", "Seller", ""],
	"/login": [""],
	"/register": [""],
	"/users": ["Administrator"],
	"/locations": ["Administrator", "Seller"],
	"/manifestations": ["Administrator", "Buyer", "Seller", ""],
	"/manifestations/:id": ["Administrator", "Buyer", "Seller", ""],
	"/cart": ["Buyer"],
	"/buyer-tickets": ["Buyer"],
	"/buyer-tickets/:id": ["Administrator"],
	"/distrustful-buyers": ["Administrator"],
	"/change-password": ["Administrator", "Buyer", "Seller"],
	"/account": ["Administrator", "Buyer", "Seller"],
	"/my-comments": ["Buyer"],
}

const isUserAllowed = function(to, from, next){
	if(to.matched.length == 0){
		next(from);
	}
	else if(localStorage.isLoggedUserRole(routeAllowedRoles[to.matched[0].path])){
		next();
	}
	else{
		next(from);
	}
}

router.beforeEach(isUserAllowed);

var app = new Vue({
    router,
    el: "#app",
});
