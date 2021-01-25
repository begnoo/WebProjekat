const Login = { template: "<login-form></login-form>" };
const RegisterPage = { template: "<registration-page></registration-page>" };
const ChangePassword = { template: "<change-password-form></change-password-form>" };
const Comment = { template: "<comment-form></comment-form>" };
const Comments = { template: "<comments-table></comments-table>" };
const LocationForm = { template: "<location-form></location-form>" };
const ManifestationForm = { template: "<manifestation-form></manifestation-form>" };
const ManifestationImageForm = {
    template: "<manifestation-image-form></manifestation-image-form>",
};
const Users = { template: "<users-page></users-page>" };
const HomePage = {template: "<home-page></home-page>"};
const ManifestationPage = {template: "<manifestation-page></manifestation-page>"};
const AccountPage = {template: "<account-page></account-page>"};
const OrderTable = {template: "<order-table></order-table>"};
const BuyerTicketsPage = {template: "<buyer-tickets-page></buyer-tickets-page>"};


const router = new VueRouter({
    mode: "hash",
    routes: [
		{ path: "/", component: HomePage },
        { path: "/login", component: Login },
        { path: "/register", component: RegisterPage },
        { path: "/add-location", component: LocationForm },
        { path: "/add-manifestation", component: ManifestationForm },
        { path: "/add-manifestation-image", component: ManifestationImageForm },
        { path: "/users", component: Users },
		{ path: "/manifestations/:id", component: ManifestationPage },
		{ path: "/manifestations/:id/comment", component: Comment },
		{ path: "/manifestations/:id/comments", component: Comments },
		{ path: "/cart", component: OrderTable },
		{ path: "/buyer-tickets/", component: BuyerTicketsPage },
		{ path: "/buyer-tickets/:id", component: BuyerTicketsPage },
		{ path: "/change-password", component: ChangePassword },
		{ path: "/account", component: AccountPage }
    ],
});

var app = new Vue({
    router,
    el: "#app",
});
