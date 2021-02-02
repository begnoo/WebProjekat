
Vue.component("manifestation-card-list", {
    template: `
	<div>
		<div v-for="manifestation in manifestations" :key="manifestation.id">
			<div class="card mb-2">
				<div class="card-body">
					<div class="container">
					  <div class="row">
					    <div class="col-sm-3 align-self-center">
					    	<img class="img-thumbnail img-fluid" width=150 height=150 :src="manifestation.imagePath">
					    </div>
					    <div class="col-sm-3 align-self-center">
							{{getDateString(manifestation)[0]}} <br>
							{{getDateString(manifestation)[1]}}
					    </div>
					    <div class="col-sm-4 align-self-center">
					      	<b>{{manifestation.name}}</b> <br>
							{{manifestation.type}} <br>
							Regular ticket price: {{manifestation.regularTicketPrice}} rsd
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

    methods: {
		
		getDateString: function({eventDate, eventEndDate}){
			const eventDateParts = eventDate.split(' ');
			const eventEndDateParts = eventEndDate.split(' ');
			if(eventDateParts[0] == eventEndDateParts[0]){
				return [eventDateParts[0], `${eventDateParts[1]} - ${eventEndDateParts[1]}`];
			}
			return [`${eventDate}`, `${eventEndDate}`]
		},
		
		redirectToInfo: function(id){
			this.$router.push("manifestations/" + id);
		}

    },

});
