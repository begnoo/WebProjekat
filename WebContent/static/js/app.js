const Login = { template: "<login-form></login-form>" };
const Register = { template: "<register-form></register-form>" };
const LocationForm = { template: "<location-form></location-form>" };
const ManifestationForm = { template: "<manifestation-form></manifestation-form>" };
const ManifestationImageForm = {
    template: "<manifestation-image-form></manifestation-image-form>",
};
const Users = { template: "<users-table></users-table>" };

const router = new VueRouter({
    mode: "hash",
    routes: [
        { path: "/login", component: Login },
        { path: "/register", component: Register },
        { path: "/add-location", component: LocationForm },
        { path: "/add-manifestation", component: ManifestationForm },
        { path: "/add-manifestation-image", component: ManifestationImageForm },
        { path: "/users", component: Users },
    ],
});

var app = new Vue({
    router,
    el: "#app",
});
