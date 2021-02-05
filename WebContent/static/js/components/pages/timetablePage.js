Vue.component('timetable-page',
{
	template:
	`
	<div>
		<br/>
		<div class="d-flex justify-content-center min-vh-100 align-items-center">
			<div class="row">
			<form>
				<label class="form-label" for="timetableDay">Select tickets for day:</label>
				<input class="form-control" v-model="selectedDate" type="date" id="timetableDay"></date>
			</form>
			</div>
		</div>
		<br/>
		<br/>
		<div class="timetable"></div>
	</div>
	`,

	data: function() {
		return {
			selectedDate: null,
			timetable: null,
			userTickets: [],
			locationsForTickets: {}
		}
	},

	watch: {
		"selectedDate": function() {
			this.changeUserTickets();
		}
	},
	
	methods: {
		initTable: function() {
			this.timetable = new Timetable();
			this.timetable.setScope(0, 23);
		},
		
		changeUserTickets() {
			let loggedUser = localStorage.getObject('loggedUser');
			let userId = loggedUser.user.id;
			
			axios(getRestConfig(`/WebProjekat/rest/users/buyers/${userId}/tickets/calendar`, {'date': this.selectedDate}))
                .then((response) => {
                    this.userTickets = response.data.ticketsOnDate;
					this.locationsForTickets = response.data.locationsOfTickets;
					
					this.changeEvents();
					this.renderTable();
                })
                .catch((error) => {
					console.log(error);
  	              	toastr.error(`Can't load tickets because you are not logged in.`, '');
                });
	
		},
				
		changeEvents: function() {
			this.timetable.locations = [];
			const locations = Object.values(this.locationsForTickets);
			this.timetable.addLocations(locations.map(location => this.getLocationString(location)));
			
			this.timetable.events = [];
			for(let ticket of this.userTickets) {
				const manifestation = ticket.manifestation;
				this.timetable.addEvent(
					manifestation.name,
					this.getLocationString(this.locationsForTickets[manifestation.locationId]),
					this.getEventStartDateTime(manifestation),
					this.getEventEndDateTime(manifestation)
				);
			}
		},
		
		getLocationString: function ({ address }) {
            return `${address.street} ${address.houseNumber}, ${address.place} ${address.postalCode}`;
        },
		
		getEventStartDateTime: function(manifestation) {
			const eventStartDate = manifestation.eventDate.split(' ')[0];
			if(eventStartDate == this.selectedDate) {
				return new Date(manifestation.eventDate);
			}
			
			return new Date(this.selectedDate + " 00:00");
		},
		
		getEventEndDateTime: function(manifestation) {
			const evenEndDate = manifestation.eventEndDate.split(' ')[0];
			if(evenEndDate == this.selectedDate) {
				return new Date(manifestation.eventEndDate);
			}

			return new Date(this.selectedDate + " 23:59");
		},
		
		renderTable: function() {
			const renderer = new Timetable.Renderer(this.timetable);
			renderer.draw('.timetable');
		}
	},
	
	mounted: function() {
		this.initTable();
	}
});