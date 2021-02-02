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
		{ path: "/manifestations/:id/all-comments", component: CommentsPage },
		{ path: "/my-comments", component: MyCommentsPage }

    ],
});

var app = new Vue({
    router,
    el: "#app",
});
