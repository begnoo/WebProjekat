Vue.component("add-manifestation-page", {
    template: `
    <div class="container">
        <manifestation-form :value="value" v-on:inputChange="newValue => value = newValue" v-on:submit-value="createManifestation"></manifestation-form>
    </div>
    `,
    data: function () {
        return {
			value:{
				name: null,
	            type: null,
	            seats: null,
	            eventDate: null,
	            eventTime: null,
	            eventEndDate: null,
	            eventEndTime: null,
	            regularTicketPrice: null,
	            locationId: null,
				id: "",
			},
        };
    },

    methods: {
        createManifestation: function (event) {
            event.preventDefault();
            axios(postRestConfig("/WebProjekat/rest/manifestations", {}, {
                    name: this.value.name,
                    type: this.value.type,
                    seats: this.value.seats,
                    eventDate: this.value.eventDate + " " + this.value.eventTime,
                    eventEndDate: this.value.eventEndDate + " " + this.value.eventEndTime,
                    regularTicketPrice: this.value.regularTicketPrice,
                    locationId: this.value.locationId,
                    sellerId: window.localStorage.getObject("loggedUser").user
                        .id,
                }))
                .then((response) => {
					this.value.id = response.data.id;
                    alert("Uspesno dodata manifestacija");
                    console.log(response.data);
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                });
        }
    },
});
