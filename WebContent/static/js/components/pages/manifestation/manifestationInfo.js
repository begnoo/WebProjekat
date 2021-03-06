Vue.component("manifestation-info", {
	template: `
    <div class="container">
        <div class="row mt-3 no-gutters">
            <div class="col">
				<img :src="this.manifestation.imagePath" width=400 height=400>
            </div>
			<div class="col align-self-center">
				<button v-if=" isAdminAndManifestationUnapproved()" v-on:click="approveManifestation" type="button" class="btn btn-primary">Approve</button>
				<button v-if=" isSeller()" type="button" class="btn btn-primary" data-toggle="modal" data-target="#editManifestationModal">Edit</button>
				<table>
					<tbody>
						<tr>
							<td colspan=2>
								<a v-show="this.manifestation.seats == 0" style="color: red"><b>SOLD OUT</b></a> <br>
							</td>
						</tr>
						<tr>
							<td colspan=2>
							<span v-if="getEventStatus() == 'EVENT AVAILABLE'">
								<a style="color: green"><b>EVENT AVAILABLE</b></a> <br>
							</span>
							<span v-if="getEventStatus() == 'EVENT ENDED'">
								<a style="color: red"><b>EVENT ENDED</b></a> <br>
							</span>
							<span v-if="getEventStatus() == 'EVENT STARTED'">
								<a style="color: red"><b>EVENT STARTED</b></a> <br>
							</span>
							</td>
						</tr>
						<tr v-if="getEventStatus() == 'EVENT ENDED'">
							<td class="td-label">Rating: </td>
							<td v-if="manifestationRating == 0" class="td-info text-right">Not yet graded</td>
							<td v-else class="td-info text-right"><rating-span :rating="manifestationRating"></rating-span></td>
						</tr>
						<tr>
							<td class="td-label">Event start: </td>
							<td class="td-info text-right">
							<i class="far fa-calendar-alt"></i> {{getDateTimeStrings(this.manifestation.eventDate)[0]}}
							<i class="far fa-clock"></i> {{getDateTimeStrings(this.manifestation.eventDate)[1]}}
							</td>
						</tr>
						<tr>
							<td class="td-label">Event end: </td>
							<td class="td-info text-right">
							<i class="far fa-calendar-alt"></i> {{getDateTimeStrings(this.manifestation.eventEndDate)[0]}}
							<i class="far fa-clock"></i> {{getDateTimeStrings(this.manifestation.eventEndDate)[1]}}
							</td>
						</tr>
						<tr>
							<td class="td-label">Seats left: </td>
							<td class="td-info text-right">{{this.manifestation.seats}} <i class="fas fa-chair"></i></td>
						</tr>
						<tr>
							<td class="td-label">Regular ticket price: </td>
							<td class="td-info text-right">{{this.manifestation.regularTicketPrice}} rsd</td>
						</tr>
						<tr>
							<td class="td-label">Seller: </td>
							<td class="td-info text-right">{{this.manifestation.seller.name}} {{this.manifestation.seller.surname}}</td>
						</tr>
						<tr class="blank-row">
						</tr>
						<tr class="tr-address">
							<td class="td-label">Address: </td>
							<td class="td-info text-right"><i class="far fa-compass"></i> {{this.manifestation.location.address.street}} {{this.manifestation.location.address.houseNumber}}</td>
						</tr>
						<tr>
							<td></td>
							<td class="td-info text-right"><i class="fas fa-city"></i> {{this.manifestation.location.address.place}}, {{this.manifestation.location.address.postalCode}}</td>
						</tr>
						
					</tbody>
				</table>
				
			</div>
        </div>
		<div class="row mt-3">
			<div class="col align-self-center">
				<location-form-map :coordinates="this.getCoordinates()" :zoom="15"></location-form-map>
			</div>
		</div>
		<edit-manifestation-modal v-if="isSeller()" :value="manifestation" 
								  v-on:update-success="manifestation => this.$emit('update-success', manifestation)">
		</edit-manifestation-modal>
    </div>
    `,
	props: ['manifestation'],
	
	data: function(){
		return {
			manifestationRating: null,
		};
	},

	methods: {
		getEventStatus: function() {
			const eventDate = moment(this.manifestation.eventDate, "YYYY-MM-DD hh:mm");
			const eventEndDate = moment(this.manifestation.eventEndDate, "YYYY-MM-DD hh:mm");
			if (eventDate.toDate() > Date.now() && this.manifestation.seats > 0) {
				return "EVENT AVAILABLE";
			}
			if (eventEndDate.toDate() <= Date.now()) {
				return "EVENT ENDED";
			}
			else if (eventDate.toDate() <= Date.now()) {
				return "EVENT STARTED";
			}
		},
		
		getDateTimeStrings: function(dateTimeString){
			return dateTimeString.split(" ");
		},
		getCoordinates: function() {
			return [this.manifestation.location.longitude, this.manifestation.location.latitude];
		},
		redirectToInfo: function(id) {
			this.$router.push("../manifestations/" + id + "/comment");
		}, // removeLater
		isSeller: function(){
			return !isDateStringBeforeNow(this.manifestation.eventDate) && 
				   localStorage.isLoggedUserRole(['Seller']) &&
				   localStorage.getObject("loggedUser").user.id == this.manifestation.sellerId;
		},
		isAdminAndManifestationUnapproved: function(){
			return !isDateStringBeforeNow(this.manifestation.eventDate) && 
				   localStorage.isLoggedUserRole(['Administrator']) &&
				   !this.manifestation.status;
		},
		getManifestationRating: function(){
			axios(getRestConfig(`../WebProjekat/rest/manifestations/${this.manifestation.id}/rating`))
			.then(response => this.manifestationRating = response.data.rating)
			.catch(error => toastr.error(error.response.data.errorMessage, ''));
		},
		approveManifestation: function(){
			axios(putRestConfig(`../WebProjekat/rest/manifestations/${this.manifestation.id}/approve`))
			.then(response => {
				toastr.success(`Manifestation is successfully approved.`, '');
				this.manifestation.status = true;
			})
			.catch(error => toastr.error(error.response.data.errorMessage, ''));
		}

	},
	
	mounted: function(){
		this.getManifestationRating();
	}


});
