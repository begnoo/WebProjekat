Vue.component("manifestation-form", {
    template: `
        <form>
            <div class="form-group">
                <div class="mb-3">
                    <label :for="getId('ManifestationName')" class="form-label">Name: </label>
                    <input type="text" class="form-control" :id="getId('ManifestationName')" v-model="value.name">
                </div>
            </div>
            <div class="form-group">
                <div class="mb-3">
                    <label :for="getId('ManifestationSeats')" class="form-label">Seats: </label>
                    <input type="number" class="form-control" :id="getId('ManifestationSeats')" v-model="value.seats">
                </div>
            </div>
            <div class="form-group">
                <div class="mb-3">
                    <label :for="getId('ManifestationPrice')" class="form-label">Ticket Price: </label>
                    <input type="number" class="form-control" :id="getId('ManifestationPrice')" v-model="value.regularTicketPrice">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-8">
                    <div class="mb-3">
                        <label :for="getId('ManifestationStartDate')" class="form-label">Event Start Date: </label>
                        <input type="date" class="form-control" :id="getId('ManifestationStartDate')" v-model="value.eventDate">
                    </div>
                </div>
                <div class="form-group col-md-4">
                    <div class="mb-3 mt-2">
                        <label class="form-label"></label>
                        <label :for="getId('ManifestationStartTime')" class="form-label" hidden>Event Start Time: </label>
                        <input type="time" class="form-control" :id="getId('ManifestationStartTime')" v-model="value.eventTime">
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-8">
                    <div class="mb-3">
                        <label :for="getId('ManifestationEndDate')" class="form-label">Event End Date: </label>
                        <input type="date" class="form-control" :id="getId('ManifestationEndDate')" v-model="value.eventEndDate">
                    </div>
                </div>
                <div class="form-group col-md-4">
                    <div class="mb-3 mt-2">
                    	<label class="form-label"></label>
                        <label :for="getId('ManifestationEndTime')" class="form-label" hidden>Event End Time: </label>
                        <input type="time" class="form-control" :id="getId('ManifestationEndTime')" v-model="value.eventEndTime">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label :for="getId('ManifestationType')">Type:</label>
                <select :id="getId('ManifestationType')" class="form-control" v-model="value.type">
                    <option v-for="type in types" :key="type" :id="type">{{type}}</option>
                </select>
            </div>
            <div class="form-group">
                <location-combo-box :idPrefix='idPrefix' v-on:location-value-change="updateLocationId" :initialLocationId="value.locationId"></location-combo-box>
            </div>

			<manifestation-image-form :manifestationId="value.id" 
									  :trigger="trigger"
									  v-on:update-success="manifestation => this.$emit('update-success', manifestation)"></manifestation-image-form>
        </form>
    `,
    props: ["value", "trigger", "idPrefix"],

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
		getId: function(id) {
			return this.idPrefix + id;
		},
		
		emitSubmit: function(event){
			this.$emit('submit-value', event);
		},
        updateLocationId: function (location) {
            this.value.locationId = location.id;
        },
    },
});
