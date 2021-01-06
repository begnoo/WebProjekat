Vue.component("manifestation-form", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-6">
                <form v-on:submit="createManifestation">
                    <div class="form-group">
                        <div class="mb-3">
                            <label for="textField" class="form-label">Name: </label>
                            <input type="text" class="form-control" id="textField" v-model="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="mb-3">
                            <label for="seatsField" class="form-label">Seats: </label>
                            <input type="number" class="form-control" id="seatsField" v-model="seats">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="mb-3">
                            <label for="priceField" class="form-label">Ticket Price: </label>
                            <input type="number" class="form-control" id="priceField" v-model="regularTicketPrice">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-8">
                            <div class="mb-3">
                                <label for="eventDateField" class="form-label">Event Start Date: </label>
                                <input type="date" class="form-control" id="eventDateField" v-model="eventDate">
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="mb-3 mt-2">
                                <label for="eventTimeField" class="form-label"></label>
                                <input type="time" class="form-control" id="eventTimeField" v-model="eventTime">
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-8">
                            <div class="mb-3">
                                <label for="eventEndDateField" class="form-label">Event End Date: </label>
                                <input type="date" class="form-control" id="eventEndDateField" v-model="eventEndDate">
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="mb-3 mt-2">
                                <label for="eventEndTimeField" class="form-label"></label>
                                <input type="time" class="form-control" id="eventEndTimeField" v-model="eventEndTime">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="typeDataSelect">Type:</label>
                        <select id="typeDataSelect" class="form-control" v-model="type">
                            <option v-for="type in types" :key="type" :id="type">{{type}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="locationField">Location:</label>
                        <location-combo-box v-on:location-value-change="updateLocationId"></location-combo-box>
                    </div>
                    <div class="form-group">
                        <div class="d-flex d-flex justify-content-between">
                            <button class="btn btn-primary">Add Manifestation</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    `,
    data: function () {
        return {
            name: null,
            type: null,
            seats: null,
            eventDate: null,
            eventTime: null,
            eventEndDate: null,
            eventEndTime: null,
            regularTicketPrice: null,
            locationId: null,
            types: ["Theater", "Festival", "Concert"],
        };
    },

    methods: {
        createManifestation: function (event) {
            event.preventDefault();
            console.log(this.locationId);
            axios
                .post("/WebProjekat/rest/manifestations", {
                    name: this.name,
                    type: this.type,
                    seats: this.seats,
                    eventDate: this.eventDate + " " + this.eventTime,
                    eventEndDate: this.eventEndDate + " " + this.eventEndTime,
                    regularTicketPrice: this.regularTicketPrice,
                    locationId: this.locationId,
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
        updateLocationId: function (location) {
            this.locationId = location.id;
        },
    },
});
