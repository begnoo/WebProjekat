Vue.component("manifestation-form", {
    template: "",
    data: function () {
        return {
            name: null,
            type: null,
            seats: null,
            eventDate: null,
            eventEndDate: null,
            regularTicketPrice: null,
            locationId: null,
        };
    },

    methods: {
        createManifestation: function (event) {
            event.preventDefault();

            axios
                .post("/WebProjekat/rest/manifesations", {
                    name: this.name,
                    type: this.type,
                    seats: this.seats,
                    eventDate: this.eventDate,
                    eventEndDate: this.eventEndDate,
                    regularTicketPrice: this.regularTicketPrice,
                    locationId: locationId,
                    sellerId: window.localStorage.getObject("loggedUser").user
                        .id,
                })
                .then((response) => {
                    alert("Uspesno");
                    console.log(response);
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                });
        },
    },
});
