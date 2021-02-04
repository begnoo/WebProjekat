Vue.component("manifestation-table", {
    template: `
    	<div class="container">
	        <table class="table">
				<thead class="thead-dark">
					  <tr>
							<th scope="col">Name</th>
							<th scope="col">Start</th>
							<th scope="col">End</th>
							<th scope="col">Seats left</th>
							<th scope="col">Price(RSD)</th>
							<th scope="col"></th>
							<th scope="col"></th>
					  </tr>
				</thead>
				<tbody>
					  <tr v-for="manifestation in manifestations">
							<td>
								<router-link :to="'/manifestations/' + manifestation.id" class="nav-link">{{ manifestation.name}}</router-link>
							</td>
							<td>{{ manifestation.eventDate}}</td>
							<td>{{ manifestation.eventEndDate}}</td>
							<td>{{ manifestation.seats}}</td>
							<td>{{ manifestation.regularTicketPrice}}</td>
							
							<td>
								
								<button type="button"
										class="btn btn-primary btn-sm"
										v-on:click="viewManifestation(manifestation.id)">
										View
								</button>
								
								<button
										v-if="isAdmin && !manifestation.status"
										type="button"
										class="btn btn-success btn-sm"
										v-on:click="openApprovalModal(manifestation)">
										Approve
								</button>
								
								<button 
								 v-if="manifestation.status"
								 type="button"
								 class="btn btn-secondary btn-sm"
								 data-toggle="modal"
							 	 v-on:click="goToManifestationTicketsPage(manifestation)">
								 	Tickets
								 </button>
							</td>
							
							<td>
								<button type="button"
								 class="btn btn-danger btn-sm"
								 data-toggle="modal"
							 	 v-on:click="openDeleteModal(manifestation)">
								 	Delete
								 </button>
							</td>
					  </tr>
				</tbody>
	      </table>
	  <confirmation-modal
		type="success"
		modalName="approvalModal" 
		title="Approve Manifestation" 
		:callback="approveManifestation"
		:callbackData="manifestationToApprove">
			Are you sure you want to approve this manifestation?
		</confirmation-modal>
		
		<confirmation-modal
		type="danger"
		modalName="deleteModal" 
		title="Delete Manifestation" 
		:callback="deleteManifestation"
		:callbackData="manifestationToDelete">
			Are you sure you want to delete this manifestation?
		</confirmation-modal>
  	</div>
    `,

    props: ["manifestations"],

    data: function () {
        return {
            isAdmin: false,
			manifestationToApprove: null,
			manifestationToDelete: null,
        };
    },

    methods: {
        emitSelectedManifestation: function (location) {
            this.$emit("manifestation-selected", location);
        },
		openApprovalModal: function(manifestationId){
			this.manifestationToApprove = manifestationId;
			$('#approvalModal').modal('toggle');
		},
		openDeleteModal: function(manifestationId){
			this.manifestationToDelete = manifestationId;
			$('#deleteModal').modal('toggle');
		},

        deleteManifestation: function (manifestation) {
            axios(
                deleteRestConfig(
                    "/WebProjekat/rest/manifestations/" + manifestation.id
                )
            )
                .then((response) => {
                    toastr.success(`${manifestation.name} is deleted.`, "");
                    this.$emit("deleted-manifestation", response.data);
					$('#deleteModal').modal('toggle');

                })
                .catch((error) =>
                    toastr.error(error.response.data.errorMessage, "")
                );
        },
        approveManifestation: function (manifestation) {
            axios(
                putRestConfig(
                    `../WebProjekat/rest/manifestations/${manifestation.id}/approve`
                )
            )
                .then((response) => {
                    toastr.success(`${manifestation.name} is successfully approved.`, "");
					$('#approvalModal').modal('toggle');
                    manifestation.status = true;
                })
                .catch((error) => toastr.error(error.response.data.errorMessage, ""));
        },
        goToManifestationTicketsPage: function (manifestation) {
            this.$router.push("/manifestation-tickets/" + manifestation.id);
        },
        viewManifestation: function (manifestationId) {
            this.$router.push("/manifestations/" + manifestationId);
        },
    },

    mounted: function () {
        this.isAdmin = localStorage.isLoggedUserRole(["Administrator"]);
    },
});
