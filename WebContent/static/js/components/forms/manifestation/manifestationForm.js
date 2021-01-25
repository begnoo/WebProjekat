Vue.component("manifestation-form", {
    template: `
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-6">
                <form v-on:submit="emitSubmit">
                    <div class="form-group">
                        <div class="mb-3">
                            <label for="textField" class="form-label">Name: </label>
                            <input type="text" class="form-control" id="textField" v-model="value.name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="mb-3">
                            <label for="seatsField" class="form-label">Seats: </label>
                            <input type="number" class="form-control" id="seatsField" v-model="value.seats">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="mb-3">
                            <label for="priceField" class="form-label">Ticket Price: </label>
                            <input type="number" class="form-control" id="priceField" v-model="value.regularTicketPrice">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-8">
                            <div class="mb-3">
                                <label for="eventDateField" class="form-label">Event Start Date: </label>
                                <input type="date" class="form-control" id="eventDateField" v-model="value.eventDate">
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="mb-3 mt-2">
                                <label for="eventTimeField" class="form-label"></label>
                                <input type="time" class="form-control" id="eventTimeField" v-model="value.eventTime">
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-8">
                            <div class="mb-3">
                                <label for="eventEndDateField" class="form-label">Event End Date: </label>
                                <input type="date" class="form-control" id="eventEndDateField" v-model="value.eventEndDate">
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="mb-3 mt-2">
                                <label for="eventEndTimeField" class="form-label"></label>
                                <input type="time" class="form-control" id="eventEndTimeField" v-model="value.eventEndTime">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="typeDataSelect">Type:</label>
                        <select id="typeDataSelect" class="form-control" v-model="value.type">
                            <option v-for="type in types" :key="type" :id="type">{{type}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="locationField">Location:</label>
                        <location-combo-box v-on:location-value-change="updateLocationId" :initialLocationId="value.locationId"></location-combo-box>
                    </div>

					<manifestation-image-form :manifestationId="value.id"></manifestation-image-form>

                    <div class="form-group">
                        <div class="d-flex d-flex justify-content-between">
                            <button class="btn btn-primary">Add Manifestation</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    `,
    props: ["value"],

	data: function(){
		return {
			types: ["Concert", "Festival", "Theater"],
		}
	},

 	watch: {
        "value": function() {
            this.$emit('inputChange', this.value);
        }
    },
	
    methods: {
		emitSubmit: function(event){
			this.$emit('submit-value', event);
		},
        updateLocationId: function (location) {
            this.value.locationId = location.id;
        },
    },
});
