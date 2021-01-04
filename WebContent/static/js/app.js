const Login = { template: '<login-form></login-form>' }
const Register = { template: '<register-form></register-form>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
    { path: '/login', component: Login },
    { path: '/register', component: Register }
    ]
});

var app = new Vue({
    router,
    el: '#app'
});