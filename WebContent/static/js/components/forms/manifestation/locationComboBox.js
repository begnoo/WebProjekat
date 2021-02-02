Vue.component("location-combo-box", {
    template: `
    <div>
	    <label :for="getId('ManifestationLocation')">Location:</label>
	    <div class="dropdown">
	        <input v-if="Object.keys(selectedLocation).length === 0" 
	                :id="getId('ManifestationLocation')"
	                ref="locationInput" 
	                class="form-control" 
	                v-model="locationFilter" 
	                v-on:change="filterLocations"
	                autocomplete="off">
	        
	        <div v-else v-on:click="resetLocation" class="dropdown-selected">
	            <input :value="getLocationString(selectedLocation)" class="form-control" disabled>
	        </div>
	        <div v-show="locationFilter && !locationSelected" class="dropdown-list">
	            <div class="dropdown-item"
	                v-on:click="selectLocation(location)"
	                v-for="location in locationsToShow" 
	                :key="location.id" 
	                :id="location.id"
	                >{{getLocationString(location)}}
	            </div>
	        </div>
	    </div>
    </div>
    `,

	props: ["initialLocationId", "idPrefix"],
	
    data: function () {
        return {
            locations: null,
            locationsToShow: null,
            selectedLocation: {},
            locationFilter: null,
            locationSelected: false,
        };
    },

    watch: {
        locationFilter: function (newValue) {
            this.filterLocations();
        },
    },

    methods: {
    	getId: function(id) {
			return this.idPrefix + id;
		},
		
        getLocations: function () {
            axios(getRestConfig("/WebProjekat/rest/locations"))
                .then((response) => {
                    this.locations = response.data;
                    this.locationsToShow = this.locations;
					if(this.initialLocationId){
						propsLocation = this.locations.find(location => this.initialLocationId == location.id);
						this.selectLocation(propsLocation)
					}
                })
                .catch((error) => {
  	              	toastr.error(`Can't load locations because you are not logged in.`, '');
                });
        },
        getLocationString: function ({ address }) {
            return `${address.street} ${address.houseNumber}, ${address.place} ${address.postalCode}`;
        },
        filterLocations: function () {
            if (!this.locationFilter) {
                this.locationsToShow = this.locations;
            } else {
                this.locationsToShow = this.locations.filter((location) =>
                    this.getLocationString(location)
                        .toLowerCase()
                        .includes(this.locationFilter.toLowerCase())
                );
            }
        },
        selectLocation: function (location) {
            this.selectedLocation = location;
            this.locationSelected = true;
            this.$emit("location-value-change", this.selectedLocation);
        },
        resetLocation: function () {
            this.selectedLocation = {};
            this.locationSelected = false;
            this.$emit("location-value-change", this.selectedLocation);
        },
    },
    mounted: function () {
        this.getLocations();
		
    },
});
