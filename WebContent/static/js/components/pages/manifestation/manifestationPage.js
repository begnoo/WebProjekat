Vue.component("manifestation-page", {
	template: `
    <div class="container">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-10" v-if="manifestation">
				<h1>{{this.manifestation.name}}</h1>
				<h3>{{this.manifestation.type}}</h3>
				<ul class="nav nav-tabs" id="myTab" role="tablist">
				  <li class="nav-item">
				    <a class="nav-link active" id="info-tab" data-toggle="tab" href="#info" role="tab" aria-controls="home" aria-selected="true">Info</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" id="tickets-tab" data-toggle="tab" href="#tickets" role="tab" aria-controls="tickets" aria-selected="false">Tickets</a>
				  </li>
		 		  <li class="nav-item" v-if="hasEventEnded()">
				    <a class="nav-link" id="tickets-tab" data-toggle="tab" href="#comments" role="tab" aria-controls="tickets" aria-selected="false">Comments</a>
				  </li>
				</ul>
				<div class="tab-content" id="myTabContent()">
					<div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info-tab">
						<manifestation-info v-bind:manifestation="this.manifestation" v-on:update-success="updatedManifestation => manifestation = updatedManifestation">
						</manifestation-info>
					</div>
					<div class="tab-pane fade" id="tickets" role="tabpanel" aria-labelledby="tickets-tab">
						<manifestation-tickets v-bind:manifestation="this.manifestation"></manifestation-tickets>
					</div>
					<div v-if="hasEventEnded" class="tab-pane fade" id="comments" role="tabpanel" aria-labelledby="tickets-tab">
						<comments-page></comments-page>
					</div>
				</div>
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    `,
	data: function() {
		return {
			manifestation: null,
		}
	},

	methods: {
		getManifestation: function(id) {
			axios(getRestConfig("/WebProjekat/rest/manifestations/" + id))
				.then(response => this.manifestation = response.data)
				.catch(error => toastr.error(error.response.data.errorMessage, ''));
		},
		hasEventEnded: function(){
			const eventEndDate = moment(this.manifestation.eventEndDate, "YYYY-MM-DD hh:mm");
			return eventEndDate <= Date.now();
		},

	},

	mounted: function() {
		let manifestationId = this.$route.params.id;
		this.getManifestation(manifestationId);
	}
});
