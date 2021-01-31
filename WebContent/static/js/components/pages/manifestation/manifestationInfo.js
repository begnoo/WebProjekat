Vue.component("manifestation-info", {
	template: `
    <div class="container">
        <div class="row mt-3 no-gutters">
            <div class="col">
				<img :src="this.manifestation.imagePath" width=400 height=400>
            </div>
			<div class="col align-self-center">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal">Edit</button>
				<p>
					<a v-show="this.manifestation.seats == 0" style="color: red"><b>SOLD OUT</b></a> <br>
					<span v-if="getEventStatus() == 'EVENT AVAILABLE'">
						<a style="color: green"><b>EVENT AVAILABLE</b></a> <br>
					</span>
					<span v-if="getEventStatus() == 'EVENT ENDED'">
						<a style="color: red"><b>EVENT ENDED</b></a> <br>
					</span>
					<span v-if="getEventStatus() == 'EVENT STARTED'">
						<a style="color: red"><b>EVENT STARTED</b></a> <br>
					</span>
					Event start: {{this.manifestation.eventDate}} <br>
					Event end: {{this.manifestation.eventEndDate}} <br>
					Number of seats left: {{this.manifestation.seats}} <br>
					Regular ticket price: {{this.manifestation.regularTicketPrice}} rsd <br>
					Seller: {{this.manifestation.seller.name}} {{this.manifestation.seller.surname}}
				</p>
				<p>
					{{this.manifestation.location.address.street}} {{this.manifestation.location.address.houseNumber}} <br>
					{{this.manifestation.location.address.place}}, {{this.manifestation.location.address.postalCode}}<br>
				</p>
				
				<br/>
		        <p v-on:click="redirectToInfo(manifestation.id)">Comment me</p>
				
			</div>
        </div>
		<div class="row mt-3">
			<div class="col align-self-center">
				<location-form-map :coordinates="this.getCoordinates()" :zoom="15"></location-form-map>
			</div>
		</div>
		<custom-modal modalName="editModal" title="Edit manifestation">
			<edit-manifestation-page :value="manifestation" 
									  v-on:update-success="manifestation => this.$emit('update-success', manifestation)">
			</edit-manifestation-page>
		</custom-modal>
    </div>
    `,
	props: ['manifestation'],

	methods: {
		getEventStatus: function() {
			//ruzna html realizacija, menjati ako ostane vremena
			const eventDate = moment(this.manifestation.eventDate, "YYYY-MM-DD hh:mm");
			const eventEndDate = moment(this.manifestation.eventEndDate, "YYYY-MM-DD hh:mm");
			if (eventDate > Date.now() && this.manifestation.seats > 0) {
				return "EVENT AVAILABLE";
			}
			if (eventEndDate <= Date.now()) {
				return "EVENT ENDED";
			}
			else if (eventDate <= Date.now()) {
				return "EVENT STARTED";
			}
		},
		getCoordinates: function() {
			return [this.manifestation.location.longitude, this.manifestation.location.latitude];
		},
		redirectToInfo: function(id) {
			this.$router.push("../manifestations/" + id + "/comment");
		} // removeLater

	},


});