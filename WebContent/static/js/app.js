const Login = { template: "<login-form></login-form>" };
const Register = { template: "<register-form></register-form>" };
const LocationForm = { template: "<location-form></location-form>" };

const router = new VueRouter({
    mode: "hash",
    routes: [
        { path: "/login", component: Login },
        { path: "/register", component: Register },
        { path: "/add-location", component: LocationForm },
    ],
});

var app = new Vue({
    router,
    el: "#app",
});
