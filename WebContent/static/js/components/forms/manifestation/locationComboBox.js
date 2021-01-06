Vue.component("location-combo-box", {
    template: `
    <div>
        <input ref="location-input" class="form-control" id="locationInput" v-model="locationFilter" v-on:change="filterLocations">
        <select ref="location-select" name="locationSelect" id="locationSelect">
            <option v-for="location in locationsToShow" :key="location.id" :id="location.id">{{getLocationString(location)}}</option>
        </select>
    </div>
    `,
    data: function () {
        return {
            locations: null,
            locationsToShow: null,
            selectedLocation: null,
            locationFilter: null,
        };
    },

    watch: {
        locationFilter: function (newValue, oldValue) {
            this.filterLocations();
        },
    },

    methods: {
        getLocations: function () {
            axios
                .get("/WebProjekat/rest/locations")
                .then((response) => {
                    this.locations = response.data;
                    this.locationsToShow = this.locations;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        getLocationString({ address }) {
            return `${address.street} ${address.houseNumber}, ${address.place} ${address.postalCode}`;
        },
        filterLocations() {
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
        emitLocationValueChanged: function (event) {
            this.$emit("location-value-change", coordinates);
        },
    },
    mounted: function () {
        this.getLocations();
    },
});
