Vue.component("manifestation-info", {
    template: `
    <div class="container">
        <div class="row mt-3 no-gutters">
            <div class="col">
			<img :src="this.manifestation.imagePath" width=400 height=400>
            </div>
			<div class="col align-self-center">
				<p>
					<a v-show="this.manifestation.seats == 0" style="color: red"><b>SOLD OUT</b></a> <br>
					<a style="color: red"><b>{{this.getEventStatus()}}</b></a> <br>
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
			</div>
        </div>
		<div class="row mt-3">
			<div class="col align-self-center">
				<location-form-map :coordinates="this.getCoordinates()" :zoom="15"></location-form-map>
			</div>
		</div>
    </div>
    `,
	props: ['manifestation'],
	
	methods: {
		getEventStatus: function(){
			const eventDate = moment(this.manifestation.eventDate, "YYYY-MM-DD hh:mm");
			const eventEndDate = moment(this.manifestation.eventDate, "YYYY-MM-DD hh:mm");
			if(eventEndDate <= Date.now()){
				return "EVENT ENDED";
			}
			else if(eventDate <= Date.now()){
				return "EVENT STARTED";
			}
		},
		getCoordinates: function(){
			return [this.manifestation.location.longitude, this.manifestation.location.latitude];
		}
	},
	

});
