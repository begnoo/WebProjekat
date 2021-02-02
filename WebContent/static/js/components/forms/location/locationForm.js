Vue.component("location-form", {
	template: `
        <form>
            <location-form-map v-on:update-coordinates="updateCoordinatesHandler" 
				:coordinates="[value.longitude, value.latitude]" 
				:zoom="6" 
				:moovable="true"
				:updateSizeTrigger="updateSizeTrigger"
			></location-form-map>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <div class="mb-3">
                        <label :for="getId('LocationLatitude')" class="form-label">Latitude: </label>
                        <input type="text" class="form-control" :id="getId('LocationLatitude')" v-model="value.latitude">
                    </div>
                </div>
                <div class="form-group col-md-6">
                    <div class="mb-3">
                        <label :for="getId('LocationLongitude')" class="form-label">Longitude: </label>
                        <input type="text" class="form-control" :id="getId('LocationLongitude')" v-model="value.longitude">
                    </div>
                </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-8">
                <div class="mb-3">
                    <label :for="getId('LocationStreet')" class="form-label">Street: </label>
                    <input type="text" class="form-control" :id="getId('LocationStreet')" v-model="value.street">
                </div>
              </div>
              <div class="form-group col-md-4">
                <div class="mb-3">
                    <label :for="getId('LocationHouseNumber')" class="form-label">House Number: </label>
                    <input type="text" class="form-control" :id="getId('LocationHouseNumber')" v-model="value.houseNumber">
                </div>
            </div>
            </div>
            <div class="form-row">
            <div class="form-group col-md-8">
                <div class="mb-3">
                    <label :for="getId('LocationPlace')" class="form-label">Place: </label>
                    <input type="text" class="form-control" :id="getId('LocationPlace')" v-model="value.place">
                </div>
            </div>
            <div class="form-group col-md-4">
                <div class="mb-3">
                    <label :for="getId('LocationPostalCode')" class="form-label">Postal Code: </label>
                    <input type="text" class="form-control" :id="getId('LocationPostalCode')" v-model="value.postalCode">
                </div>
            </div>
            </div>
              <div class="form-group">
                <div class="d-flex d-flex justify-content-between">
                    <button v-on:click="guessLocationFromCoordinates" class="btn btn-primary">Try guessing location</button>
                    <button v-on:click="guessCoordinatesFromLocation" class="btn btn-primary">Try guessing coordinates</button>
                </div>
              </div>
        </form>
    `,

	props:["value", "updateSizeTrigger", "idPrefix"],
	
	watch: {
        "value": function() {
            this.$emit('inputChange', this.value);
        }
    },
	
	data: function() {
		return {};
	},
	
	methods: {
		getId: function(id) {
			return this.idPrefix + id;	
		},
		
		updateCoordinatesHandler: function(coordinates) {
			this.value.longitude = coordinates[0];
			this.value.latitude = coordinates[1];
		},
	
		guessLocationFromCoordinates: function(event) {
			event.preventDefault();
			axios.get("https://nominatim.openstreetmap.org/reverse", {
					params: {
						lat: this.value.latitude,
						lon: this.value.longitude,
						format: "json",
					},
				})
				.then((response) => {
					this.updateFieldsFromResponseData(response.data);
				})
				.catch(function(error) {
					toastr.error('Could not find a location based on given coordinates.', '');
				});
		},
		
		updateFieldsFromResponseData: function(data) {
			const { address } = data;
			if (address) {
				if (address.street) {
					this.value.street = address.street;
				}
				if (address.town) {
					this.value.place = address.town;
				}
				if (address.city) {
					this.value.place = address.city;
				}
				if (address.road) {
					this.value.street = address.road;
				}
				if (address.postCode) {
					this.value.postalCode = address.postCode;
				}
				if (address.postcode) {
					this.value.postalCode = address.postcode;
				}
				if (address["house-number"]) {
					this.value.houseNumber = address["house-number"];
				}
			}
		},
		
		guessCoordinatesFromLocation: function(event) {
			event.preventDefault();
			const url =
				"https://nominatim.openstreetmap.org/search/" +
				this.value.place +
				", " +
				this.value.houseNumber +
				" " +
				this.value.street;
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
						const { lon, lat } = response.data[0];
						this.updateCoordinatesHandler([lon, lat]);
					}
				})
				.catch(function(error) {
					toastr.error('Could not find coordinates based on given info.', '');
				});
		},
	},
});
