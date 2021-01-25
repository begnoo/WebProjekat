Vue.component("edit-manifestation-page", {
    template: `
    <div class="container">
        <manifestation-form :value="updatedValue" v-on:inputChange="newValue => updatedValue = newValue" v-on:submit-value="updateManifestation"></manifestation-form>
    </div>
    `,
   
	props: ["value"],
	
	data: function(){
		return {
			updatedValue: {}
		}
	},

    methods: {
        updateManifestation: function (event) {
            event.preventDefault();
			const data = {
					id: this.updatedValue.id,
                    name: this.updatedValue.name,
                    type: this.updatedValue.type,
                    seats: this.updatedValue.seats,
                    eventDate: this.updatedValue.eventDate + " " + this.updatedValue.eventTime,
                    eventEndDate: this.updatedValue.eventEndDate + " " + this.updatedValue.eventEndTime,
                    regularTicketPrice: this.updatedValue.regularTicketPrice,
                    locationId: this.updatedValue.locationId,
                };
			console.log(data);
            axios(putRestConfig("/WebProjekat/rest/manifestations", {}, data))
                .then((response) => {
                    alert("Uspesno azurirana manifestacija");
                    console.log(response.data);
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                });
        }
    },
	created: function(){
		this.updatedValue = {...this.value};
		let splitDateTime = this.value.eventDate.split(" ");
		this.updatedValue.eventDate = splitDateTime[0];
		this.updatedValue.eventTime = splitDateTime[1];
		splitDateTime = this.value.eventEndDate.split(" ");
		this.updatedValue.eventEndDate  = splitDateTime[0];
		this.updatedValue.eventEndTime = splitDateTime[1];
	}
});
