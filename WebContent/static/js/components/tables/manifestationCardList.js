
Vue.component("manifestation-card-list", {
    template: `
	<div>
		<div v-for="manifestation in manifestations" :key="manifestation.id">
			<div class="card mb-2">
				<div class="card-body">
					<div class="container">
					  <div class="row">
					    <div class="col-sm-2 align-self-center">
					    	<img class="img-thumbnail img-fluid" width=150 height=150 :src="manifestation.imagePath">
					    </div>
					    <div v-if="getDateString(manifestation)[0] == 'single'"class="col-sm-3 align-self-center">
							<i class="far fa-calendar-alt"></i> {{getDateString(manifestation)[1]}} <br>
							<i class="far fa-clock"></i> {{getDateString(manifestation)[2]}}
					    </div>
						<div v-if="getDateString(manifestation)[0] == 'double'"class="col-sm-3 align-self-center">
							<i class="far fa-calendar-alt"></i> {{getDateString(manifestation)[1]}}  
							<i class="far fa-clock"></i> {{getDateString(manifestation)[2]}}<br>
							<i class="far fa-calendar-alt"></i> {{getDateString(manifestation)[3]}}
							<i class="far fa-clock"></i> {{getDateString(manifestation)[4]}}
					    </div>
					    <div class="col-sm-3 align-self-center">
					      	<b>{{manifestation.name}}</b> <br>
							{{manifestation.type}} 
							<i v-if="manifestation.type == 'Concert'" class="fas fa-music"></i>
							<i v-if="manifestation.type == 'Theater'" class="fas fa-theater-masks"></i>
							<i v-if="manifestation.type == 'Festival'" class="fas fa-campground"></i>
							<br>

							Regular ticket price: {{manifestation.regularTicketPrice}} rsd
					    </div>
	 					<div class="col-sm-2 align-self-center">
							<i class="far fa-compass"></i>
					      	{{manifestation.location.address.street}} {{manifestation.location.address.houseNumber}}<br>
							<i class="fas fa-city"></i>
							{{manifestation.location.address.place}}, {{manifestation.location.address.postalCode}}<br>
					    </div>
						<div class="col-sm-1 text-center align-self-center">
					      <button v-on:click="redirectToInfo(manifestation.id)" type="button" class="btn btn-primary">Tickets</button>
					    </div>
					  </div>
					</div>
				 </div>
			</div>
		</div>
	</div>
	`,
	
	props: ["manifestations"],
	
	data: function(){
		return {};
	},

    methods: {
		
		getDateString: function({eventDate, eventEndDate}){
			const eventDateParts = eventDate.split(' ');
			const eventEndDateParts = eventEndDate.split(' ');
			if(eventDateParts[0] == eventEndDateParts[0]){
				return [ "single", eventDateParts[0], `${eventDateParts[1]} - ${eventEndDateParts[1]}`];
			}
			return ["double", eventDateParts[0], eventDateParts[1], eventEndDateParts[0], eventEndDateParts[1]];
		},
		
		redirectToInfo: function(id){
			this.$router.push("manifestations/" + id);
			this.$emit("redirection");
		}

    },

});
