
Vue.component("manifestation-table", {
    template: `
	<div>
		<div v-for="manifestation in manifestationsToShow" :key="manifestation.id">
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
		<nav aria-label="Page navigation example">
		  <ul class="pagination">
		    <li class="page-item"><a v-on:click="this.getPreviousPage" class="page-link" href="#">Previous</a></li>
		    <li class="page-item"><a class="page-link" href="#">{{this.currentPage}}</a></li>
		    <li class="page-item"><a v-on:click="this.getNextPage" class="page-link" href="#">Next</a></li>
		  </ul>
		</nav>
	</div>
	`,
    data: function () {
        return {
            manifestations: [],
			manifestationsToShow: [],
			numOfElements: 3,
			currentPage: 0
        };
    },

    methods: {
        getManifestationsOrderedByDate: function(){
			axios.get("/WebProjekat/rest/manifestations", 
			{
				params: {
					"order-by-date" : true
				}
			}
			).then(response => {
				this.manifestations = response.data;
				this.getNextPage();
			})
			.catch(error => alert(error));
		},
		getDateString: function({eventDate, eventEndDate}){
			const eventDateParts = eventDate.split(' ');
			const eventEndDateParts = eventEndDate.split(' ');
			if(eventDateParts[0] == eventEndDateParts[0]){
				return [eventDateParts[0], `${eventDateParts[1]} - ${eventEndDateParts[1]}`];
			}
			return [`${eventDate}`, `${eventEndDate}`]
		},
		getNextPage: function(event){
			if(event != null){
				event.preventDefault();
			}
			const begin = Math.min(this.manifestations.length, this.numOfElements*this.currentPage);
			const end = Math.min(this.manifestations.length, this.numOfElements*(this.currentPage+1));
			if(end != begin){
				this.manifestationsToShow = this.manifestations.slice(begin, end);
				++this.currentPage;
			}

		},
		getPreviousPage: function(){
			event.preventDefault();
			const begin = Math.max(0, this.manifestations.length - (this.numOfElements*(this.currentPage-1)));
			const end = Math.max(0, this.numOfElements*(this.currentPage));
			console.log(begin, end);
			if(end > begin){
				this.manifestationsToShow = this.manifestations.slice(begin, end);
				--this.currentPage;
			}
		},
		redirectToInfo: function(id){
			this.$router.push("manifestations/" + id);
		}
		
		
    },

	mounted: function(){
		this.getManifestationsOrderedByDate();
	}
});
