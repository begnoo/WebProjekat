Vue.component("location-form", {
    template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-6">
                <form v-on:submit="createLocation">
                    <location-form-map v-on:update-coordinates="updateCoordinatesHandler"></location-form-map>
                    <div class="mb-3">
                        <label for="textField" class="form-label">Latitude: </label>
                        <input type="text" class="form-control" id="textField" v-model="latitude">
                    </div>
                    <div class="mb-3">
                        <label for="textField" class="form-label">Longitude: </label>
                        <input type="text" class="form-control" id="textField" v-model="longitude">
                    </div>
                    <div class="mb-3">
                        <label for="textField" class="form-label">Street: </label>
                        <input type="text" class="form-control" id="textField" v-model="street">
                    </div>
                    <div class="mb-3">
                        <label for="textField" class="form-label">House Number: </label>
                        <input type="text" class="form-control" id="textField" v-model="houseNumber">
                    </div>
                    <div class="mb-3">
                        <label for="textField" class="form-label">Place: </label>
                        <input type="text" class="form-control" id="textField" v-model="place">
                    </div>
                    <div class="mb-3">
                        <label for="textField" class="form-label">Postal Code: </label>
                        <input type="text" class="form-control" id="textField" v-model="postalCode">
                    </div>
                    <div class="d-flex d-flex justify-content-between">
                        <button class="btn btn-primary">Add Location</button>
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
            latitude: null,
            longitude: null,
            street: null,
            houseNumber: null,
            place: null,
            postalCode: null,
        };
    },
    methods: {
        updateCoordinatesHandler: function (coordinates) {
            this.latitude = coordinates[0];
            this.longitude = coordinates[1];
        },
        createLocation: function (event) {
            event.preventDefault();
            axios
                .post("/WebProjekat/rest/locations", {
                    latitude: this.latitude,
                    longitude: this.longitude,
                    address: {
                        street: this.street,
                        houseNumber: this.houseNumber,
                        place: this.place,
                        postalCode: this.postalCode,
                    },
                })
                .then((response) => {
                    console.log(response.data);
                    alert("Uspesno");
                })
                .catch(function (error) {
                    alert(error.response.data.errorMessage);
                });
        },
        guessLocationFromCoordinates: function (event) {
            event.preventDefault();
            axios
                .get("https://nominatim.openstreetmap.org/reverse", {
                    params: {
                        lat: this.latitude,
                        lon: this.longitude,
                        format: "json",
                    },
                })
                .then((response) => {
                    this.updateFieldsFromResponseData(response.data);
                })
                .catch(function (error) {
                    alert(
                        "Could not find a location based on given coordinates"
                    );
                });
        },
        updateFieldsFromResponseData: function (data) {
            const { address } = data;
            if (address) {
                if (address.street) {
                    this.street = address.street;
                }
                if (address.town) {
                    this.place = address.town;
                }
                if (address.city) {
                    this.place = address.city;
                }
                if (address.road) {
                    this.street = address.road;
                }
                if (address.postCode) {
                    this.postalCode = address.postCode;
                }
                if (address.postcode) {
                    this.postalCode = address.postcode;
                }
                if (address["house-number"]) {
                    this.houseNumber = address["house-number"];
                }
            }
        },
        guessCoordinatesFromLocation: function (event) {
            event.preventDefault();
            const url =
                "https://nominatim.openstreetmap.org/search/" +
                this.place +
                ", " +
                this.houseNumber +
                " " +
                this.street;
            axios
                .get(url, {
                    params: {
                        format: "json",
                        limit: 1,
                        "accept-language": "en",
                    },
                })
                .then((response) => {
                    if (response.data && response.data.lenght != 0) {
                        console.log(response.data);
                        const { lon, lat } = response.data[0];
                        this.updateCoordinatesHandler([lon, lat]);
                    }
                })
                .catch(function (error) {
                    alert("Could not find coordinates based on given info");
                });
        },
    },
});
