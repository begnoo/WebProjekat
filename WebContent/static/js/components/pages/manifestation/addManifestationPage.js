Vue.component("add-manifestation-modal", {
    template: `
    <custom-modal modalName="addManifestationModal" title="Add Manifestation">
        <manifestation-form :value="value" :idPrefix='idPrefix' v-on:inputChange="newValue => value = newValue"></manifestation-form>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" v-on:click="createManifestation">Add Manifestation</button>
			<button type="button" class="btn btn-secondary" data-dismiss="modal" data-target="#addManifestationModal">Cancel</button>
		</div>
    </custom-modal>
    `,
    data: function () {
        return {
			value:{
				name: null,
	            type: null,
	            seats: null,
	            eventDate: null,
	            eventTime: null,
	            eventEndDate: null,
	            eventEndTime: null,
	            regularTicketPrice: null,
	            locationId: null,
				id: "",
			},
			idPrefix: 'add'
        };
    },

    methods: {
        createManifestation: function (event) {
            event.preventDefault();
            
            if(!executeValidation(this.getValidators())) {
				return;
			}
            
            axios(postRestConfig("/WebProjekat/rest/manifestations", {}, {
                    name: this.value.name,
                    type: this.value.type,
                    seats: this.value.seats,
                    eventDate: this.value.eventDate + " " + this.value.eventTime,
                    eventEndDate: this.value.eventEndDate + " " + this.value.eventEndTime,
                    regularTicketPrice: this.value.regularTicketPrice,
                    locationId: this.value.locationId,
                    sellerId: window.localStorage.getObject("loggedUser").user
                        .id,
                }))
                .then((response) => {
                    toastr.success(`You have successfully added a new manifestation.`, '');
					this.value.id = response.data.id;
					this.$emit("add-manifestation-success", response.data);
					this.updateSeller(response.data);
                })
                .catch(function (error) {
					if(error.response.data.errorMessages) {
						toastr.options.preventDuplicates = false;
						for(let errorMessage of error.response.data.errorMessages) {
		                    toastr.error(errorMessage, '');
						}
	                	toastr.options.preventDuplicates = true;
					}
					
					if(error.response.data.errorMessage) {
	                    toastr.error(error.response.data.errorMessage, '');
					}
				});
        },
        
		updateSeller: function(manifestation){
			let loggedUser = localStorage.getObject("loggedUser");
			loggedUser.user.manifestations.push(manifestation);
			localStorage.setObject("loggedUser", loggedUser);
		},
		
		getValidators: function() {
			return {
				'addManifestationName': [validateLength('addManifestationName', 3, 150)],
				'addManifestationSeats': [validateMinNumber('addManifestationSeats', 1)],
				'addManifestationPrice': [validateMinNumber('addManifestationPrice', 0)],
				'addManifestationStartDate': [validateRequired('addManifestationStartDate')],
				'addManifestationStartTime': [validateRequired('addManifestationStartTime')],
				'addManifestationEndDate': [validateRequired('addManifestationEndDate')],
				'addManifestationEndTime': [validateRequired('addManifestationEndTime')],
				'addManifestationType': [validateRequired('addManifestationType')],
				'addManifestationLocation': [validateLocation('addManifestationLocation', this.value.locationId)]
			};
		}
    }
});
