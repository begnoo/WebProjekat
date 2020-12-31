window.onload = function () {
    let loginForm = new Vue({
        el: "#loginApp",
        data: {
            username: null,
            password: null,
            randomData: null,
        },
        methods: {
            reportLogin: function (sumbitEvent) {
                alert(this.username + " " + this.password);
                sumbitEvent.preventDefault();
            },

            getRandomData: function () {
                fetch("https://random-data-api.com/api/beer/random_beer")
                    .then((response) => response.json())
                    .then((data) => (this.randomData = data));
            },
        },
    });

    // let randomDataApp = new Vue({
    //     el: "#randomDataPar",
    //     data: {
    //         randomData: null,
    //     },
    //     methods: {
    //         getRandomData: function () {
    //             fetch("https://random-data-api.com/api/beer/random_beer")
    //                 .then((response) => response.json())
    //                 .then((data) => (this.randomData = data));
    //         },
    //     },
    // });
};
